package run.hxtia.workbd.service.notificationwork.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.ListUtils;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.common.enhance.MpPage;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.common.upload.UploadEditParam;
import run.hxtia.workbd.common.upload.Uploads;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.common.util.MiniApps;
import run.hxtia.workbd.common.util.Streams;
import run.hxtia.workbd.mapper.HomeworkMapper;
import run.hxtia.workbd.pojo.dto.StudentHomeworkDetailDto;
import run.hxtia.workbd.pojo.dto.StudentInfoDto;
import run.hxtia.workbd.pojo.po.Homework;
import run.hxtia.workbd.pojo.po.StudentHomework;
import run.hxtia.workbd.pojo.vo.notificationwork.request.page.CourseIdWorkPageReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.page.HomeworkPageReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.HomeworkReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.HomeworkUploadReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.response.HomeworkVo;
import run.hxtia.workbd.pojo.vo.common.response.result.ExtendedPageVo;
import run.hxtia.workbd.pojo.vo.common.response.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.common.response.result.PageVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.page.StudentWorkPageReqVo;
import run.hxtia.workbd.service.notificationwork.HomeworkService;
import run.hxtia.workbd.service.notificationwork.StudentCourseService;
import run.hxtia.workbd.service.notificationwork.StudentHomeworkService;
import run.hxtia.workbd.service.usermanagement.StudentService;

import java.util.*;
import java.util.function.Function;

/**
 * 作业模块 【管理】业务层
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class HomeworkServiceImpl extends ServiceImpl<HomeworkMapper, Homework> implements HomeworkService {

    private final Redises redises;
    private final StudentHomeworkService studentHomeworkService;
    private StudentCourseService studentCourseService;

    @Autowired
    public void setStudentCourseService(@Lazy StudentCourseService studentCourseService) {
        this.studentCourseService = studentCourseService;
    }


    /**
     * 分页查询作业
     * @param pageReqVo：分页信息
     * @param status：作业状态 【1：可用作业 0：历史作业】
     * @return 分页后的数据
     */
    @Override
    public PageVo<HomeworkVo> list(HomeworkPageReqVo pageReqVo, Short status) {

        // 构建分页sql
        MpLambdaQueryWrapper<Homework> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.like(pageReqVo.getKeyword(), Homework::getTitle, Homework::getDescription, Homework::getPublishPlatform).
            between(pageReqVo.getCreatedTime(), Homework::getCreatedAt).
            between(pageReqVo.getDeadline(), Homework::getUpdatedAt).
            eq(Homework::getCourseId, pageReqVo.getCourseId()).
            eq(Homework::getPublisherId, pageReqVo.getPublisherId()).
            eq(Homework::getStatus, status);

        return baseMapper.
            selectPage(new MpPage<>(pageReqVo), wrapper).
            buildVo(MapStructs.INSTANCE::po2vo);
    }

    /**
     * 从微信：保存 or 编辑作业
     * @param reqVo：作业信息
     * @return ：是否成功
     */
    @Override
    public boolean saveOrUpdateFromWx(HomeworkReqVo reqVo) throws Exception {
        // 从 Token 中解析出 Token WX ID
        String wechatId = MiniApps.getOpenId(reqVo.getWxToken());

        // 然后去学生授权表中查看有无这个课程的权限。
        // TODO：然后去学生授权表中查看有无这个课程的权限。
        // 去真正的保存作业
        return saveOrUpdate(reqVo);
    }

    /**
     * 保存 or 编辑作业
     * @param reqVo：作业信息
     * @return ：是否成功
     */
    @Override
    public boolean saveOrUpdate(HomeworkReqVo reqVo) throws Exception {
        Homework po = MapStructs.INSTANCE.reqVo2po(reqVo);

        // 上传图片
        String filePath = "";
        if (reqVo.getId() == null) {
            // 保证是新建时，才来上传图片
            List<MultipartFile> pictureFiles = reqVo.getPictureFiles();
            if (!CollectionUtils.isEmpty(pictureFiles))
                filePath = Uploads.uploadImages(pictureFiles);

            // 如果上传成功，保存图片路径到数据库
            if (StringUtils.hasLength(filePath))
                po.setPictureLinks(filePath);
        }

        try {
            // 保存作业数据
            boolean res = saveOrUpdate(po);
            if (!res) {
                return false;
            }

            // 保存成功了，同步将这条作业插入学生的作业表中
            // TODO：异步去插入，“补偿” 机制

            // 根据课程 ID 查询学生 IDs
            List<String> stuIds = studentCourseService.listStuIdsByCourseId(po.getCourseId());
            boolean pushStuWorkOk = studentHomeworkService.addStudentHomeworks(stuIds, po.getId());
            if (!pushStuWorkOk) {
                // 对于插入失败的场景，不能回滚作业，那么需要自己开定时任务去 “补偿”
                log.error("给学生推送作业失败");
            }

            return true;
        } catch (Exception e) {
            // 出现异常将刚上传的图片给删掉
            log.error(e.getMessage());
            e.printStackTrace();
            Uploads.deleteFiles(filePath);
            return false;
        }
    }

    /**
     * 多图片编辑，编辑作业的图片
     * @param uploadReqVo ：所需参数
     * @return ：是否成功
     */
    @Override
    public boolean updatePictures(HomeworkUploadReqVo uploadReqVo) throws Exception {
        Homework po = baseMapper.selectById(uploadReqVo.getId());

        // 构建编辑图片的对象
        UploadEditParam editParam = new UploadEditParam(
            uploadReqVo.getPicturesFiles(),
            uploadReqVo.getMatchIndex(), po.getPictureLinks());

        // 编辑图片
        return Uploads.uploadMoreWithPo(po, editParam, baseMapper, Homework::setPictureLinks);
    }

    /**
     * 删除一条or多条作业【逻辑删除】
     * @param ids：需要删除的作业ID
     * @return ：是否成功
     */
    @Override
    public boolean removeByIds(String ids) {
        List<String> workIds = Arrays.asList(ids.split(","));
        if (CollectionUtils.isEmpty(workIds)) return false;

        // 查出所有作业【并且设置不可见】
        List<Homework> works = Streams.list2List(baseMapper.selectBatchIds(workIds), (work -> {
            work.setStatus(Constants.Status.WORK_DISABLE);
            return work;
        }));

        return updateBatchById(works);
    }

    /**
     * 根据作业ID获取作业信息
     * @param workId ：作业ID
     * @return ：作业数据
     */
    @Override
    public HomeworkVo getByWorkId(Long workId) {
        if (workId == null || workId <= 0) return null;
        MpLambdaQueryWrapper<Homework> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(Homework::getId, workId).eq(Homework::getStatus, Constants.Status.WORK_ENABLE);
        return MapStructs.INSTANCE.po2vo(baseMapper.selectOne(wrapper));
    }

    /**
     * 删除一条or多条作业【彻底删除】
     * @param ids：需要删除的作业ID
     * @return ：是否成功
     */
    @Override
    public boolean removeHistory(String ids) {
        if (!StringUtils.hasLength(ids)) return false;

        List<String> workIds = Arrays.asList(ids.split(","));
        // 检查作业是否能删除
        checkWorkRemove(workIds);
        boolean deleteWork = removeByIds(workIds);
        boolean deleteUserWork = studentHomeworkService.removeByWorkId(workIds);
        // 在用户作业表里删除作业
        if (!deleteWork || !deleteUserWork) {
            return JsonVos.raise(CodeMsg.REMOVE_ERROR);
        }
        return true;
    }

    /**
     * 根据学生ID查询学生的作业信息列表
     * @param reqVo：分页对象
     * @return 学生的作业信息列表
     */
    @Override
    public PageVo<StudentHomeworkDetailDto> getWorkInfoListByStuId(StudentWorkPageReqVo reqVo) {
        // 第一步：获取学生的所有作业记录
        PageVo<StudentHomework> pages = studentHomeworkService.listByStuId(reqVo);
        List<StudentHomework> studentHomeworks = pages.getData();

        PageVo<StudentHomeworkDetailDto> resPages = new PageVo<>();
        resPages.setPages(pages.getPages());
        resPages.setCurrentPage(pages.getCurrentPage());
        resPages.setPageSize(pages.getPageSize());
        resPages.setCount(pages.getCount());

        if (studentHomeworks.isEmpty()) {
            return resPages;  // 如果没有作业记录，直接返回空列表
        }

        // 第二步：获取所有作业ID
        List<Long> homeworkIds = Streams.list2List(studentHomeworks, StudentHomework::getHomeworkId);

        // 第三步：一次性查询所有作业详细信息
        MpLambdaQueryWrapper<Homework> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.like(reqVo.getKeyword(), Homework::getTitle, Homework::getDescription).
            between(reqVo.getDeadline(), Homework::getDeadline).
            eq(Homework::getStatus, Constants.Status.WORK_ENABLE).in(Homework::getId, homeworkIds);

        List<Homework> homeworks = baseMapper.selectList(wrapper);

        // 第四步：将 Homeworks 映射为 Map 以便快速查找
        Map<Long, Homework> homeworkMap = Streams.list2Map(homeworks, Homework::getId, Function.identity());

        // 第五步：组装 DTO
        resPages.setData(Streams.list2List(studentHomeworks, (sh) -> {
            Homework po = homeworkMap.get(sh.getHomeworkId());
            StudentHomeworkDetailDto dto = MapStructs.INSTANCE.po2dto(po);
            if (po == null) {
                return dto;
            }
            dto.setStatus(sh.getStatus());
            dto.setPin(sh.getPin());
            return dto;
        }));

        return resPages;
    }

    /**
     * 根据学生ID查询学生的作业信息列表
     * @param reqVo：分页对象
     * @return 学生的作业信息列表
     */
    @Override
    public PageVo<StudentHomeworkDetailDto> getWorkInfoListByStuToken(StudentWorkPageReqVo reqVo) {
        reqVo.setWechatId(MiniApps.getOpenId(reqVo.getWxToken()));
        // 第一步：获取学生的所有作业记录
        return getWorkInfoListByStuId(reqVo);
    }

    @Override
    public StudentHomeworkDetailDto getWorkInfo(Long workId, String token) {
        // 作业信息
        Homework po = baseMapper.selectById(workId);
        StudentHomeworkDetailDto dto = MapStructs.INSTANCE.po2dto(po);
        String stuId = MiniApps.getOpenId(token);

        // 学生作业
        MpLambdaQueryWrapper<StudentHomework> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(StudentHomework::getStudentId, stuId)
            .eq(StudentHomework::getHomeworkId, workId);

        StudentHomework studentHomework = studentHomeworkService.getOne(wrapper);
        if (studentHomework == null) {
            return dto;
        }

        dto.setStatus(studentHomework.getStatus());
        dto.setPin(studentHomework.getPin());

        return dto;
    }


    @Override
    public List<Long> getWorkIdsByCourseIds(List<Integer> courseIdList) {
        MpLambdaQueryWrapper<Homework> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.in(Homework::getCourseId, courseIdList).eq(Homework::getStatus, Constants.Status.WORK_ENABLE);
        return Streams.list2List(baseMapper.selectList(wrapper), Homework::getId);
    }

    /**
     * 根据课程ID列表分页查询作业信息。
     * @param reqVo 请求参数对象，包含课程ID列表及分页信息
     * @return 分页的作业视图对象列表
     */
    @Override
    public PageVo<HomeworkVo> getWorkInfoByCourseIds(CourseIdWorkPageReqVo reqVo) {
        // 初始化分页参数
        Page<Homework> pageParam = new Page<>(reqVo.getPage(), reqVo.getSize());

        // 构建查询条件
        MpLambdaQueryWrapper<Homework> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.in(Homework::getCourseId, reqVo.getCourseIds())
            .eq(Homework::getStatus, Constants.Status.WORK_ENABLE);

        // 执行分页查询
        Page<Homework> resultPage = baseMapper.selectPage(pageParam, wrapper);

        // 转换查询结果为VO对象
        List<HomeworkVo> homeworkVos = Streams.list2List(resultPage.getRecords(), MapStructs.INSTANCE::po2vo);

        // 构建扩展分页结果对象
        PageVo<HomeworkVo> pageVo = new PageVo<>();
        pageVo.setCount(resultPage.getTotal());
        pageVo.setPages(resultPage.getPages());
        pageVo.setData(homeworkVos);
        pageVo.setCurrentPage(resultPage.getCurrent());
        pageVo.setPageSize(resultPage.getSize());

        // 返回分页结果
        return pageVo;
    }

    /**
     * 检查作业是否能删除【只有是历史作业才能彻底删除】
     * @param workIds ：检查的作业ID
     */
    private void checkWorkRemove(List<String> workIds) {
        if (CollectionUtils.isEmpty(workIds)) return;
        List<Homework> works = listByIds(workIds);
        for (Homework work : works) {
            if (Constants.Status.WORK_ENABLE.equals(work.getStatus())) {
                JsonVos.raise(CodeMsg.WRONG_WORK_NO_REMOVE);
            }
        }
    }
}
