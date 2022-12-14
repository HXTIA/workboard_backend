package run.hxtia.workbd.common.mapstruct;

import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import run.hxtia.workbd.pojo.dto.ResourceDto;
import run.hxtia.workbd.pojo.po.*;
import run.hxtia.workbd.pojo.vo.request.WxSubscribeMessageReqVo;
import run.hxtia.workbd.pojo.vo.request.save.*;
import run.hxtia.workbd.pojo.vo.response.AdminLoginVo;
import run.hxtia.workbd.pojo.vo.response.OrganizationVo;
import run.hxtia.workbd.pojo.vo.response.RoleVo;
import run.hxtia.workbd.pojo.vo.response.UserVo;
import run.hxtia.workbd.pojo.vo.response.*;

import java.util.List;

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
    UserVo po2vo(User po);
    AdminLoginVo po2loginVo(AdminUsers po);
    OrganizationVo po2vo(Organization po);
    RoleVo po2vo(Role po);
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
    WorkVo po2vo(Work po);
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
    UserWorkVo po2userWorkVo(Work po);
    AdminUserVo po2adminUserVo(AdminUsers po);

   // reqVo -> po  【用来做数据库保存】

   @Mapping(source = "nickName", target = "nickname")
   User wxReqVo2po(WxMaUserInfo wxReqVo);
   AdminUsers reqVo2po(AdminUserReqVo reqVo);
   @Mapping(source = "email", target = "username")
   AdminUsers reqVo2po(AdminUserRegisterReqVo reqVo);
   AdminUsers reqVo2po(AdminUserEditReqVo reqVo);
   AdminUsers reqVo2po(AdminUserInfoEditReqVo reqVo);
   Organization reqVo2po(OrganizationReqVo reqVo);
   User reqVo2po(UserReqVo reqVo);
   Role reqVo2po(RoleReqVo reqVo);
    @Mapping(
        source = "deadline",
        target = "deadline",
        qualifiedBy = MapStructFormatter.Mills2Date.class
    )
   Work reqVo2po(WorkReqVo reqVo);

   // reqVo -> wxSdk
    WxMaSubscribeMessage reqVo2wxVo(WxSubscribeMessageReqVo reqVo);

    // PO -> DTO
    ResourceDto po2dto(Resource po);

}
