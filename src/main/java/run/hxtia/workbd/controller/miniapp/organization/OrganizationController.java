package run.hxtia.workbd.controller.miniapp.organization;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.pojo.vo.common.response.result.DataJsonVo;
import run.hxtia.workbd.pojo.vo.common.response.result.PageJsonVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.page.CourseIdWorkPageReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.page.CoursePageReqVo;
import run.hxtia.workbd.pojo.vo.organization.request.page.ClassPageReqVo;
import run.hxtia.workbd.pojo.vo.organization.request.page.CollegePageReqVo;
import run.hxtia.workbd.pojo.vo.organization.request.page.GradePageReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.response.HomeworkVo;
import run.hxtia.workbd.pojo.vo.notificationwork.response.CourseVo;
import run.hxtia.workbd.pojo.vo.organization.response.ClassVo;
import run.hxtia.workbd.pojo.vo.organization.response.CollegeVo;
import run.hxtia.workbd.pojo.vo.organization.response.GradeVo;
import run.hxtia.workbd.pojo.vo.organization.response.OrganizationVo;
import run.hxtia.workbd.pojo.vo.common.response.result.ExtendedPageVo;
import run.hxtia.workbd.pojo.vo.common.response.result.PageVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.StudentIdReqVo;
import run.hxtia.workbd.service.notificationwork.CourseService;
import run.hxtia.workbd.service.notificationwork.HomeworkService;
import run.hxtia.workbd.service.organization.ClassService;
import run.hxtia.workbd.service.organization.CollegeService;
import run.hxtia.workbd.service.organization.GradeService;
import run.hxtia.workbd.service.usermanagement.StudentService;

import javax.validation.Valid;

/**
 * @author Xiaojin
 * @date 2024/5/9
 */

@RestController
@RequestMapping("/wx/organization/complexAll")
@Api(tags = "ComplexAllController")
@Tag(name = "ComplexAllController", description = "【C端】组织管理模块")
@RequiredArgsConstructor
public class OrganizationController {

    // 学院
    private final CollegeService collegeService;
    // 年级
    private final GradeService gradeService;
    // 班级
    private final ClassService classService;
    // 课程
    private final CourseService courseService;
    // 作业
    private final HomeworkService workService;
    // 学生
    private final StudentService studentService;

    // 选择学院
    /**
     * 分页获取学院信息
     * @param reqVo ：分页请求参数
     * @return 分页的学院信息
     */
    @PostMapping("/collegePage")
    @ApiOperation("分页获取学院信息")
    public PageJsonVo<CollegeVo> getPageList(@Valid @RequestBody CollegePageReqVo reqVo) {
        return JsonVos.ok(collegeService.getPageList(reqVo));
    }

    // 选择年级
    // 根据学院ID分页获取年级信息
    /**
     * 根据学院ID分页获取年级信息
     * @param reqVo ：学院ID
     * @return 分页的年级信息
     */
    @PostMapping("/gradePage")
    @ApiOperation("根据学院ID分页获取年级信息")
    public PageJsonVo<GradeVo> getGradeInfoByCollegeIdWithPagination(@Valid @RequestBody GradePageReqVo reqVo) {
        return JsonVos.ok(gradeService.getGradeInfoByCollegeIdWithPagination(reqVo));
    }

    // 选择班级
    // 根据年级ID分页获取班级信息
    /**
     * 根据年级ID分页获取班级信息
     * @param reqVo ：年级ID
     * @return 分页的班级信息
     */
    @PostMapping("/classPage")
    @ApiOperation("根据年级ID分页获取班级信息")
    public PageJsonVo<ClassVo> getClassInfoByGradeIdWithPagination(@Valid @RequestBody ClassPageReqVo reqVo) {
        return JsonVos.ok(classService.listPage(reqVo));
    }

    // 选择课程
    // 根据班级ID获取课程信息
    /**
     * 根据学院ID分页获取课程信息
     * @param reqVo ：学院ID
     * @return 分页的课程信息
     */
    @PostMapping("/coursePage")
    @ApiOperation("根据学院ID分页获取课程信息")
    public PageJsonVo<CourseVo> getCourseInfoByCollegeIdWithPagination(@Valid @RequestBody CoursePageReqVo reqVo) {
        return JsonVos.ok(courseService.getPage(reqVo));
    }

    // 查看作业列表
    /**
     * 根据课程Ids获取作业信息列表（分页）
     * @param reqVo 包含课程Ids列表和分页信息的请求对象
     * @return 分页的作业信息列表
     */
    @PostMapping("/workPage")
    @ApiOperation("根据课程 ids 分页获取作业列表")
    public PageJsonVo<HomeworkVo> getWorkInfoByCourseIdsWithPagination(@Valid @RequestBody CourseIdWorkPageReqVo reqVo) {
        return JsonVos.ok(workService.getWorkInfoByCourseIds(reqVo));
    }

    // 整合所有组织
    // TODO 通过学生的id，获取学院、年级、班级信息

//    @PostMapping("/studentOrganizationDetails")
//    @ApiOperation("根据学生ID获取学院、年级、班级信息")
//    public DataJsonVo<OrganizationVo> getStudentOrganizationDetails(@RequestBody @Valid StudentIdReqVo reqVo) {
//            OrganizationVo organizationVo = studentService.getOrganizationDetailsByStudentId(reqVo.getStudentId());
//            return JsonVos.ok(organizationVo);
//    }
}
