package run.hxtia.workbd.service.notificationwork.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.Streams;
import run.hxtia.workbd.mapper.StudentHomeworkMapper;
import run.hxtia.workbd.mapper.StudentNotificationMapper;
import run.hxtia.workbd.pojo.po.StudentHomework;
import run.hxtia.workbd.pojo.vo.common.response.result.ExtendedPageVo;
import run.hxtia.workbd.pojo.vo.common.response.result.PageVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.page.StudentHomeworkPageReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.response.HomeworkVo;
import run.hxtia.workbd.pojo.vo.notificationwork.response.NotificationVo;
import run.hxtia.workbd.service.notificationwork.StudentHomeworkService;

import java.util.List;


/**
 * 后台管理用户作业模块 业务层
 */
@Service
public class StudentHomeworkServiceImpl
    extends ServiceImpl<StudentHomeworkMapper, StudentHomework> implements StudentHomeworkService {

    @Autowired
    private StudentHomeworkMapper studentHomeworkMapper;

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
    public List<StudentHomework> listByStuId(Long stuId) {
        MpLambdaQueryWrapper<StudentHomework> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(StudentHomework::getStudentId, stuId).ne(StudentHomework::getStatus, Constants.Status.WORK_DONE);
        return baseMapper.selectList(wrapper);
    }

    /**
     * 批量添加学生作业关联
     * @param workIds 作业IDs
     * @param stuId 学生ID
     * @return 是否成功
     */
    public boolean addStudentHomeworks(List<Long> workIds, Long stuId) {
        // 批量插入
        boolean result = saveBatch(Streams.list2List(workIds, (workId) -> {
            StudentHomework po = new StudentHomework();
            po.setStudentId(stuId);
            po.setHomeworkId(workId);
            return po;
        }));

        if (!result) {
            throw new RuntimeException("Failed to save student homework");
        }

        return true;
    }

    @Override
    public PageVo<HomeworkVo> getHomeworksByStudentId(StudentHomeworkPageReqVo reqVo) {
        Page<?> pageParam = new Page<>(reqVo.getPage(), reqVo.getSize());

        // 使用自定义 Mapper 方法进行查询
        Page<HomeworkVo> homeworkPage = studentHomeworkMapper.selectHomeworksByStudentId(pageParam, Long.valueOf(reqVo.getStudentId()));

        // 构建并返回分页结果
        PageVo<HomeworkVo> pageVo = new PageVo<>();
        pageVo.setCount(homeworkPage.getTotal());  // 总记录数
        pageVo.setPages(homeworkPage.getPages());  // 总页数
        pageVo.setData(homeworkPage.getRecords()); // 转换为VO的数据记录
        pageVo.setCurrentPage(homeworkPage.getCurrent());  // 当前页码
        pageVo.setPageSize(homeworkPage.getSize());        // 每页记录数

        return pageVo;
    }
}
