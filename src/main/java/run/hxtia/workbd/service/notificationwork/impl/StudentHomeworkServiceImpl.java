package run.hxtia.workbd.service.notificationwork.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.thymeleaf.util.ListUtils;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.common.enhance.MpPage;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.common.util.MiniApps;
import run.hxtia.workbd.common.util.Streams;
import run.hxtia.workbd.mapper.StudentHomeworkMapper;
import run.hxtia.workbd.pojo.po.Homework;
import run.hxtia.workbd.pojo.po.Role;
import run.hxtia.workbd.pojo.po.StudentHomework;
import run.hxtia.workbd.pojo.vo.common.response.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.common.response.result.PageVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.StudentHomeworkReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.page.StudentHomeworkPageReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.response.HomeworkVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.page.StudentWorkPageReqVo;
import run.hxtia.workbd.service.notificationwork.HomeworkService;
import run.hxtia.workbd.service.notificationwork.StudentCourseService;
import run.hxtia.workbd.service.notificationwork.StudentHomeworkService;

import java.util.List;


/**
 * 后台管理用户作业模块 业务层
 */
@Service
@RequiredArgsConstructor
public class StudentHomeworkServiceImpl
    extends ServiceImpl<StudentHomeworkMapper, StudentHomework> implements StudentHomeworkService {

    /**
     * 根据作业ID删除 用户作业
     * @param workIds：作业ID
     * @return ：是否成功
     */
    @Override
    public boolean removeByWorkId(List<String> workIds) {
        if (CollectionUtils.isEmpty(workIds)) return false;
        MpLambdaQueryWrapper<StudentHomework> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.in(StudentHomework::getHomeworkId, workIds);
        return baseMapper.delete(wrapper) > 0;
    }

    @Override
    public PageVo<StudentHomework> listByStuId(StudentWorkPageReqVo pageReqVo) {
        MpLambdaQueryWrapper<StudentHomework> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.between(pageReqVo.getCreatedTime(), StudentHomework::getCreatedAt).
            eq(StudentHomework::getStudentId, pageReqVo.getWechatId()).
            eq(StudentHomework::getStatus, pageReqVo.getStatus()).
            eq(StudentHomework::getPin, pageReqVo.getPin());

        // 构建分页结果
        return baseMapper.
            selectPage(new MpPage<>(pageReqVo), wrapper)
            .buildVo();
    }

    /**
     * 批量添加学生作业关联
     * @param workIds 作业IDs
     * @param stuId 学生ID
     * @return 是否成功
     */
    public boolean addStudentHomeworks(List<Long> workIds, String stuId) {
        if (ListUtils.isEmpty(workIds)) {
            return true;
        }
        return saveBatch(Streams.list2List(workIds, (workId) -> {
            StudentHomework po = new StudentHomework();
            po.setStudentId(stuId);
            po.setHomeworkId(workId);
            return po;
        }));
    }

    @Override
    public PageVo<HomeworkVo> getHomeworksByStudentId(StudentHomeworkPageReqVo reqVo) {
        Page<?> pageParam = new Page<>(reqVo.getPage(), reqVo.getSize());

        // 使用自定义 Mapper 方法进行查询
        Page<HomeworkVo> homeworkPage = baseMapper.selectHomeworksByStudentId(pageParam, Long.valueOf(reqVo.getStudentId()));

        // 构建并返回分页结果
        PageVo<HomeworkVo> pageVo = new PageVo<>();
        pageVo.setCount(homeworkPage.getTotal());  // 总记录数
        pageVo.setPages(homeworkPage.getPages());  // 总页数
        pageVo.setData(homeworkPage.getRecords()); // 转换为VO的数据记录
        pageVo.setCurrentPage(homeworkPage.getCurrent());  // 当前页码
        pageVo.setPageSize(homeworkPage.getSize());        // 每页记录数

        return pageVo;
    }

    /**
     * 批量添加学生作业关联
     * @param stuIds 学生 IDs ID
     * @param workId 作业 Id
     * @return 是否成功
     */
    @Override
    public boolean addStudentHomeworks(List<String> stuIds, Long workId) {
        if (ListUtils.isEmpty(stuIds)) {
            return true;
        }

        return saveBatch(Streams.list2List(stuIds, (stuId) -> {
            StudentHomework po = new StudentHomework();
            po.setStudentId(stuId);
            po.setHomeworkId(workId);
            return po;
        }));
    }

    /**
     * 更新学生作业状态
     * @return 是否成功
     */
    @Override
    public boolean update(StudentHomeworkReqVo reqVo) {
        StudentHomework po = MapStructs.INSTANCE.reqVo2po(reqVo);
        String stuId = MiniApps.getOpenId(reqVo.getWxToken());
        if (!StringUtils.hasLength(stuId) || po.getHomeworkId() <= 0) {
            // 必须确保 stuId 和 homeworkId 有值，要不然到时候更新错了
            JsonVos.raise(CodeMsg.SAVE_ERROR);
        }

        MpLambdaQueryWrapper<StudentHomework> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(StudentHomework::getStudentId, stuId).eq(StudentHomework::getHomeworkId, po.getHomeworkId());

        return baseMapper.update(po, wrapper) > 0;
    }
}
