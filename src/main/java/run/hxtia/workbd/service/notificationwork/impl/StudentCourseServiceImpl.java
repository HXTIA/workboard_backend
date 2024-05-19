package run.hxtia.workbd.service.notificationwork.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.common.util.Streams;
import run.hxtia.workbd.mapper.StudentCourseMapper;
import run.hxtia.workbd.pojo.po.Course;
import run.hxtia.workbd.pojo.po.StudentCourse;
import run.hxtia.workbd.pojo.vo.common.response.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.common.response.result.PageVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.SaveCoursesAndHomeworksReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.StudentCourseEditReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.StudentCourseReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.response.CourseVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.page.StudentCoursePageReqVo;
import run.hxtia.workbd.service.notificationwork.HomeworkService;
import run.hxtia.workbd.service.notificationwork.StudentHomeworkService;
import run.hxtia.workbd.service.notificationwork.StudentCourseService;
import run.hxtia.workbd.service.usermanagement.StudentService;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Xiaojin
 * @date 2024/5/7
 */
@Service
@RequiredArgsConstructor
public class StudentCourseServiceImpl extends ServiceImpl<StudentCourseMapper, StudentCourse> implements StudentCourseService {


    private final StudentHomeworkService studentHomeworkService;
    private HomeworkService homeworkService;

    @Autowired
    public void setHomeworkService(@Lazy HomeworkService homeworkService) {
        this.homeworkService = homeworkService;
    }

    /**
     * 添加学生课程
     * @param reqVo ：学生课程信息
     * @return ：是否成功
     */
    @Override
    @Transactional(readOnly = false)
    public boolean save(StudentCourseReqVo reqVo) {
        // 将请求对象转换为实体对象
        StudentCourse studentCourse = MapStructs.INSTANCE.reqVo2po(reqVo);
        // 检查是否存在相同的学生课程信息
        boolean exists = lambdaQuery().eq(StudentCourse::getStudentId, studentCourse.getStudentId())
            .eq(StudentCourse::getCourseId, studentCourse.getCourseId())
            .one() != null;
        if (exists) {
            // 如果存在相同的学生课程信息，返回false，表示保存操作失败
            return false;
        }
        // 使用Mapper的insert方法保存到数据库
        return save(studentCourse);
    }

    /**
     * 更新学生课程信息
     * @param reqVo ：学生课程信息
     * @return ：是否成功
     */
    @Override
    @Transactional(readOnly = false)
    public boolean update(StudentCourseEditReqVo reqVo) {
        // 将请求对象转换为实体对象
        StudentCourse studentCourse = MapStructs.INSTANCE.reqVo2po(reqVo);
        // 检查除当前学生课程外，是否存在相同的学生课程信息
        boolean exists = lambdaQuery().eq(StudentCourse::getStudentId, studentCourse.getStudentId())
            .eq(StudentCourse::getCourseId, studentCourse.getCourseId())
            .one() != null;
        if (exists) {
            // 如果存在相同的学生课程信息，返回false，表示更新操作失败
            return false;
        }
        // 使用Mapper的update方法更新数据库
        return updateById(studentCourse);
    }

    /**
     * 删除学生课程
     * @param studentId ：学生ID
     * @param courseId ：课程ID
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    public boolean delete(Integer studentId, Integer courseId) {
        // 使用lambdaUpdate方法从数据库中删除特定的学生课程记录
        boolean removed = lambdaUpdate().eq(StudentCourse::getStudentId, studentId).eq(StudentCourse::getCourseId, courseId).remove();
        return removed;
    }

    /**
     * 检查学生课程是否存在
     * @param studentId 学生ID
     * @param courseId 课程ID
     * @return 如果学生课程存在，返回true，否则返回false
     */
    @Override
    public boolean exists(Integer studentId, Integer courseId) {
        // 使用lambdaQuery方法查询数据库
        return lambdaQuery().eq(StudentCourse::getStudentId, studentId).eq(StudentCourse::getCourseId, courseId).one() != null;
    }

    /**
     * 根据学生ID获取学生课程信息
     * @param studentId ：学生ID
     * @return 学生课程信息列表
     */
    @Override
    public List<CourseVo> getStudentCoursesByStudentId(String studentId) {
        // 使用StudentCourseMapper的selectCoursesByStudentId方法获取课程信息
        List<Course> courses = baseMapper.selectCoursesByStudentId(studentId);
        // 使用MapStructs.INSTANCE.po2vo方法将课程信息转换为视图对象
        return Streams.list2List(courses, MapStructs.INSTANCE::po2vo);
    }

    /**
     * 根据学生ID获取分页的学生课程信息
     * @return 学生课程信息
     */
    @Override
    public IPage<CourseVo> getStudentCoursesByStudentIdWithPagination(String studentId, Page<StudentCourse> page) {
        // 使用StudentCourseMapper的selectCoursesByStudentId方法获取课程信息
        IPage<Course> courses = baseMapper.selectCoursesByStudentIdWithPagination(page, studentId);
        // 使用MapStructs.INSTANCE.po2vo方法将课程信息转换为视图对象
        IPage<CourseVo> courseVos = courses.convert(MapStructs.INSTANCE::po2vo);
        return courseVos;
    }

    @Override
    public boolean deleteByCourseId(Integer courseId) {
        // 使用 lambdaUpdate 删除 courseId 等于提供的课程 ID 的所有记录
        return lambdaUpdate().eq(StudentCourse::getCourseId, courseId).remove();
    }

    @Override
    public boolean removeByCourseId(List<String> courseIds) {
        // 如果提供的通知ID列表为空，立即返回false
        if (CollectionUtils.isEmpty(courseIds)) return false;

        // 创建一个新的查询包装器
        MpLambdaQueryWrapper<StudentCourse> wrapper = new MpLambdaQueryWrapper<>();

        // 添加一个条件，指定notificationId必须在提供的列表中
        wrapper.in(StudentCourse::getCourseId, courseIds);

        // 删除所有符合条件的记录，并返回是否成功
        return baseMapper.delete(wrapper) >= 0;
    }

    /**
     * 批量保存学生课程和作业信息
     * @return 是否成功
     */
    @Override
    public boolean saveCoursesAndHomeworks(SaveCoursesAndHomeworksReqVo reqVo) {
        if (reqVo == null) {
            return false;
        }

        List<StudentCourse> studentCourses = Streams.list2List(reqVo.getCourseIds(), (courseId -> {
            StudentCourse studentCourse = new StudentCourse();
            studentCourse.setStudentId(reqVo.getStudentId());
            studentCourse.setCourseId(courseId);
            return studentCourse;
        }));

        // 保存学生课程信息
        boolean saveStudentCourse = saveBatch(studentCourses);

        // 如果保存学生课程信息失败，抛出异常
        if (!saveStudentCourse) {
            return false;
        }

        // 获取作业ID
        List<Long> workIdsByCourseIds = homeworkService.getWorkIdsByCourseIds(reqVo.getCourseIds());

        // 保存学生作业信息
       if (!studentHomeworkService.addStudentHomeworks(workIdsByCourseIds, reqVo.getStudentId())) {
           JsonVos.raise(CodeMsg.SAVE_ERROR);
       }

       return true;
    }

    /**
     * 根据课程 ID 查询选了这个课的学生 IDs
     * @param courseId 学生 ID
     * @return 课程 Id list
     */
    @Override
    public List<String> listStuIdsByCourseId(Integer courseId) {
        MpLambdaQueryWrapper<StudentCourse> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.select(StudentCourse::getStudentId).eq(StudentCourse::getCourseId, courseId);

        return Streams.list2List(baseMapper.selectObjs(wrapper), Object::toString);
    }
}
