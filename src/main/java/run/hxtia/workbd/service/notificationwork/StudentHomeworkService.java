package run.hxtia.workbd.service.notificationwork;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.StudentHomework;

import java.util.List;

/**
 * 后台管理用户作业模块 业务层
 */
@Transactional(readOnly = true)
public interface StudentHomeworkService extends IService<StudentHomework> {

    /**
     * 根据作业ID删除 用户作业
     * @param workIds：作业ID
     * @return ：是否成功
     */
    boolean removeByWorkId(List<String> workIds);
    /**
     * 根据学生ID查询学生的作业信息列表
     * @param stuId 学生ID
     * @return 学生的作业信息列表
     */
    List<StudentHomework> listByStuId(Long stuId);

    /**
     * 批量添加学生作业关联
     * @param workIds 作业IDs
     * @param stuId 学生ID
     * @return 是否成功
     */
    boolean addStudentHomeworks(List<Long> workIds, Long stuId);
}
