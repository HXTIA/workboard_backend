package run.hxtia.workbd.service.notificationwork;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.StudentCourse;
import run.hxtia.workbd.pojo.vo.notificationwork.request.SaveCoursesAndHomeworksReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.StudentCourseEditReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.StudentCourseReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.response.CourseVo;

import java.util.List;

/**
 * @author Xiaojin
 * @date 2024/5/7
 */
@Transactional(readOnly = true)
public interface StudentCourseService extends IService<StudentCourse> {

    /**
     * 添加学生课程
     * @param reqVo ：学生课程信息
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean save(StudentCourseReqVo reqVo);

    // 批量保存学生课程信息接口。传入课程ids、学生id ——> 保存课程
    // TODO 保存课程时需要保存作业
    /**
     * 批量保存学生课程和作业信息
     * @param reqVo 课程ID列表
     * @return 是否成功
     */
    @Transactional(readOnly = false)
    boolean saveCoursesAndHomeworks(SaveCoursesAndHomeworksReqVo reqVo);

    /**
     * 更新学生课程信息
     * @param reqVo ：学生课程信息
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean update(StudentCourseEditReqVo reqVo);

    /**
     * 删除学生课程
     * @param studentId ：学生ID
     * @param courseId ：课程ID
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean delete(Integer studentId, Integer courseId);

    /**
     * 检查学生课程是否存在
     * @param studentId ：学生ID
     * @param courseId ：课程ID
     * @return ：是否存在
     */
    boolean exists(Integer studentId, Integer courseId);


    /**
     * 根据学生ID获取学生课程信息
     * @param studentId ：学生ID
     * @return 学生课程信息列表
     */
    List<CourseVo> getStudentCoursesByStudentId(String studentId);

    /**
     * 根据学生ID获取分页的学生课程信息
     * @return 学生课程信息列表
     */
    IPage<CourseVo> getStudentCoursesByStudentIdWithPagination(String studentId, Page<StudentCourse> page);

    // 批量删除学生课程信息接口。传入一个课程id ——> 删除学生课程表中 course_id = 课程id 的所有记录
    /**
     * 根据课程ID删除所有相关的学生课程记录
     * @param courseId 课程ID
     * @return 是否成功
     */
    @Transactional(readOnly = false)
    boolean deleteByCourseId(Integer courseId);

    /**
     * 根据课程ID删除 学生课程
     * @param courseIds：课程 ID 列表
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean removeByCourseId(List<String> courseIds);

    /**
     * 根据课程 ID 查询选了这个课的学生 IDs
     * @param courseId 学生 ID
     * @return 课程 Id list
     */
    List<String> listStuIdsByCourseId(Integer courseId);
}
