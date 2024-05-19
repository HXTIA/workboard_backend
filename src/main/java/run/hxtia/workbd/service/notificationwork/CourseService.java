package run.hxtia.workbd.service.notificationwork;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.Course;
import run.hxtia.workbd.pojo.vo.notificationwork.request.CourseEditReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.CourseReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.page.CoursePageReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.response.CourseVo;
import run.hxtia.workbd.pojo.vo.common.response.result.PageVo;

import java.util.List;

/**
 * @author Xiaojin
 * @date 2024/5/6
 */
@Transactional(readOnly = true)
public interface CourseService extends IService<Course> {

    /**
     * 添加课程
     * @param reqVo ：课程信息
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean save(CourseReqVo reqVo);

    /**
     * 更新课程信息
     * @param reqVo ：课程信息
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean update(CourseEditReqVo reqVo);

    /**
     * 删除课程
     * @param courseId ：课程ID
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean delete(Integer courseId);

    /**
     * 获取课程信息
     * @param courseId:课程id
     * @return 课程信息
     */
    CourseVo getCourseInfoById(Integer courseId);

    /**
     * 获取所有课程信息
     * @return 所有课程信息
     */
    PageVo<CourseVo> getList();

    /**
     * 检查课程是否存在。
     * @param courseId 课程的ID。
     * @return 如果课程存在，则为true，否则为false。
     */
    boolean checkCourseInfo(Integer courseId);

    /**
     * 检查课程是否存在。
     * @param courseName 课程的名称。
     * @param collegeId 学院的ID。
     * @return 如果课程存在，则为true，否则为false。
     */
    boolean checkCourseExists(String courseName, Integer collegeId);

    /**
     * 删除一门or多门课程
     * @param ids：需要删除的课程ID
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean removeHistory(String ids);

    /**
     * 根据学院ID分页获取课程信息
     * @return 该学院下的课程信息的分页列表
     */
    PageVo<CourseVo> getPage(CoursePageReqVo reqVo);

    /**
     * 根据学院ID获取课程信息
     */
    List<CourseVo> getCourseListByCollegeId(Integer collegeId);

    /**
     * 根据教师id获取课程信息
     */
    List<CourseVo> getCourseListByTeacherId(Integer teacherId);

    /**
     * 根据多个课程ID获取课程信息
     * @param courseIds 课程ID列表
     * @return 课程信息列表
     */
    List<CourseVo> getCoursesByIds(List<Integer> courseIds);

}
