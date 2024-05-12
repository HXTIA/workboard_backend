package run.hxtia.workbd.common.mapstruct;

import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import run.hxtia.workbd.pojo.dto.ResourceDto;
import run.hxtia.workbd.pojo.dto.StudentHomeworkDetailDto;
import run.hxtia.workbd.pojo.po.*;
import run.hxtia.workbd.pojo.vo.common.response.*;
import run.hxtia.workbd.pojo.vo.common.response.result.PageJsonVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.*;
import run.hxtia.workbd.pojo.vo.notificationwork.response.*;
import run.hxtia.workbd.pojo.vo.organization.request.*;
import run.hxtia.workbd.pojo.vo.common.request.WxSubscribeMessageReqVo;
import run.hxtia.workbd.pojo.vo.organization.response.ClassVo;
import run.hxtia.workbd.pojo.vo.organization.response.CollegeVo;
import run.hxtia.workbd.pojo.vo.organization.response.GradeVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.StudentReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.*;
import run.hxtia.workbd.pojo.vo.usermanagement.response.AdminUserVo;
import run.hxtia.workbd.pojo.vo.usermanagement.response.RoleVo;

/**
 * 1、简单Java对象的转换【不用自己写很多 set、get】
 * 2、https://github.com/mapstruct/mapstruct
 */
@Mapper(uses = {
       MapStructFormatter.class
})
public interface MapStructs {

   // 生成实例对象。可以调用下面的方法
   MapStructs INSTANCE = Mappers.getMapper(MapStructs.class);

   /*
    1、Po -> vo     【用来将从数据库查到的数据过滤成 vo返回给前端】
    2、可以解决转换类型不匹配、参数名不匹配的问题。 @Mapping 参数如下
    （1）source：源对象
    （2）target：目标对象
    （3）qualifiedBy：找转换器中的方法
    */
    StudentVo po2vo(Student po);
    AdminLoginVo po2loginVo(AdminUsers po);
    RoleVo po2vo(Role po);

    AdminUserVo po2adminUserVo(AdminUsers po);
    CollegeVo po2vo(College po);
    GradeVo po2vo(Grade po);
    ClassVo po2vo(Classes po);
    CourseVo po2vo(Course po);
    StudentCourseVo po2vo(StudentCourse po);

    @Mapping(
        source = "createdAt",
        target = "createdAt",
        qualifiedBy = MapStructFormatter.Date2Millis.class
    )
    @Mapping(
        source = "deadline",
        target = "deadline",
        qualifiedBy = MapStructFormatter.Date2Millis.class
    )
    @Mapping(
        source = "updatedAt",
        target = "updatedAt",
        qualifiedBy = MapStructFormatter.Date2Millis.class
    )
    HomeworkVo po2vo(Homework po);

    // 通知
    NotificationVo po2vo(Notification po);
    NotificationVo po2vo(StudentNotification studentNotification);

   // reqVo -> po  【用来做数据库保存】

   AdminUsers reqVo2po(AdminUserReqVo reqVo);
   @Mapping(source = "email", target = "username")
   AdminUsers reqVo2po(AdminUserRegisterReqVo reqVo);
   AdminUsers reqVo2po(AdminUserEditReqVo reqVo);
   AdminUsers reqVo2po(AdminUserInfoEditReqVo reqVo);
   Student reqVo2po(StudentReqVo reqVo);
   Role reqVo2po(RoleReqVo reqVo);

    @Mapping(source = "name", target = "name")
   College reqVo2po(CollegeReqVo reqVo);
    @Mapping(source = "name", target = "name")
    College reqVo2po(CollegeEditReqVo reqVo);
    Grade reqVo2po(GradeReqVo reqVo);
    Grade reqVo2po(GradeEditReqVo reqVo);
    Classes reqVo2po(ClassReqVo reqVo);
    Classes reqVo2po(ClassEditReqVo reqVo);
    Course reqVo2po(CourseReqVo reqVo);
    Course reqVo2po(CourseEditReqVo reqVo);
    StudentCourse reqVo2po(StudentCourseReqVo reqVo);
    @Mapping(source = "id", target = "id")
    StudentCourse reqVo2po(StudentCourseEditReqVo reqVo);

    @Mapping(
        source = "deadline",
        target = "deadline",
        qualifiedBy = MapStructFormatter.Mills2Date.class
    )
    Homework reqVo2po(HomeworkReqVo reqVo);

   // reqVo -> wxSdk
    WxMaSubscribeMessage reqVo2wxVo(WxSubscribeMessageReqVo reqVo);

    // PO -> DTO
    ResourceDto po2dto(Resource po);
    @Mapping(
        source = "deadline",
        target = "deadline",
        qualifiedBy = MapStructFormatter.Date2Millis.class
    )
    StudentHomeworkDetailDto po2dto(Homework po);



    Notification reqVo2po(NotificationReqVo reqVo);


}
