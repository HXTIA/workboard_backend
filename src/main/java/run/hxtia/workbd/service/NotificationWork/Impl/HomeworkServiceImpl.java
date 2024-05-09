package run.hxtia.workbd.service.NotificationWork.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.common.enhance.MpPage;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.upload.UploadEditParam;
import run.hxtia.workbd.common.upload.Uploads;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.common.util.Streams;
import run.hxtia.workbd.mapper.HomeworkMapper;
import run.hxtia.workbd.pojo.dto.StudentHomeworkDetailDto;
import run.hxtia.workbd.pojo.po.Homework;
import run.hxtia.workbd.pojo.po.StudentHomework;
import run.hxtia.workbd.pojo.vo.request.page.HomeworkPageReqVo;
import run.hxtia.workbd.pojo.vo.request.save.HomeworkReqVo;
import run.hxtia.workbd.pojo.vo.request.save.HomeworkUploadReqVo;
import run.hxtia.workbd.pojo.vo.response.HomeworkVo;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.result.PageVo;
import run.hxtia.workbd.service.NotificationWork.HomeworkService;
import run.hxtia.workbd.service.NotificationWork.StudentHomeworkService;

import java.util.*;
import java.util.function.Function;

/**
 * 作业模块 【管理】业务层
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class HomeworkServiceImpl extends ServiceImpl<HomeworkMapper, Homework> implements HomeworkService {

    private final StudentHomeworkService studentHomeworkService;

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
            // 保存数据
            return saveOrUpdate(po);
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
     * @param stuId 学生ID
     * @return 学生的作业信息列表
     */
    @Override
    public List<StudentHomeworkDetailDto> getWorkInfoListByStuId(Long stuId) {
        // 第一步：获取学生的所有作业记录
        List<StudentHomework> studentHomeworks = studentHomeworkService.listByStuId(stuId);
        if (studentHomeworks.isEmpty()) {
            return new ArrayList<>();  // 如果没有作业记录，直接返回空列表
        }
        // 第二步：获取所有作业ID
        List<Long> homeworkIds = Streams.list2List(studentHomeworks, StudentHomework::getHomeworkId);

        // 第三步：一次性查询所有作业详细信息
        MpLambdaQueryWrapper<Homework> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(Homework::getStatus, Constants.Status.WORK_ENABLE).in(Homework::getId, homeworkIds);
        List<Homework> homeworks = baseMapper.selectList(wrapper);

        // 第四步：将 Homeworks 映射为 Map 以便快速查找
        Map<Long, Homework> homeworkMap = Streams.list2Map(homeworks, Homework::getId, Function.identity());

        // 第五步：组装 DTO
        return Streams.list2List(studentHomeworks, (sh) -> {
            Homework po = homeworkMap.get(sh.getHomeworkId());
            StudentHomeworkDetailDto dto = MapStructs.INSTANCE.po2dto(po);
            dto.setStatus(sh.getStatus());
            dto.setPin(sh.getPin());
            return dto;
        });
    }

    @Override
    public List<Long> getWorkIdsByCourseIds(List<Integer> courseIdList) {
        MpLambdaQueryWrapper<Homework> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.in(Homework::getCourseId, courseIdList).eq(Homework::getStatus, Constants.Status.WORK_ENABLE);
        return Streams.list2List(baseMapper.selectList(wrapper), Homework::getId);
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
