package run.hxtia.workbd.service.notificationwork;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.StudentCourse;
import run.hxtia.workbd.pojo.vo.request.course.StudentCourseEditReqVo;
import run.hxtia.workbd.pojo.vo.request.course.StudentCourseReqVo;
import run.hxtia.workbd.pojo.vo.response.course.CourseVo;

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
     * @param courseIds 课程ID列表
     * @param studentId 学生ID
     * @return 是否成功
     */
    @Transactional(readOnly = false)
    boolean saveCoursesAndHomeworks(List<Integer> courseIds, Integer studentId);

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
    List<CourseVo> getStudentCoursesByStudentId(Integer studentId);

    /**
     * 根据学生ID获取分页的学生课程信息
     * @param studentId ：学生ID
     * @param page ：分页信息
     * @return 学生课程信息列表
     */
    IPage<CourseVo> getStudentCoursesByStudentIdWithPagination(Integer studentId, Page<StudentCourse> page);



}
