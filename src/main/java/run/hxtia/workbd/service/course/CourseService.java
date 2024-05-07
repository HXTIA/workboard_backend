package run.hxtia.workbd.service.course;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.Course;
import run.hxtia.workbd.pojo.vo.request.course.CourseEditReqVo;
import run.hxtia.workbd.pojo.vo.request.course.CourseReqVo;
import run.hxtia.workbd.pojo.vo.response.course.CourseVo;
import run.hxtia.workbd.pojo.vo.result.PageVo;

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
     * 根据学院ID分页获取课程信息
     * @param collegeId 学院ID
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 该学院下的课程信息的分页列表
     */
    PageVo<CourseVo> getCourseInfoByCollegeIdWithPagination(Integer collegeId, int pageNum, int pageSize);

}
