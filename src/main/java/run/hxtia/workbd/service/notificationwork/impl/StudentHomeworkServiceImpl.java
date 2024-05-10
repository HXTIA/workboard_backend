package run.hxtia.workbd.service.notificationwork.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.Streams;
import run.hxtia.workbd.mapper.StudentHomeworkMapper;
import run.hxtia.workbd.pojo.po.StudentHomework;
import run.hxtia.workbd.service.notificationwork.StudentHomeworkService;

import java.util.List;


/**
 * 后台管理用户作业模块 业务层
 */
@Service
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
}
