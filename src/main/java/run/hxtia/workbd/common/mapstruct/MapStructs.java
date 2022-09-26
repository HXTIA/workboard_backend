package run.hxtia.workbd.common.mapstruct;

import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import run.hxtia.workbd.pojo.po.AdminUsers;
import run.hxtia.workbd.pojo.po.User;
import run.hxtia.workbd.pojo.vo.request.WxSubscribeMessageReqVo;
import run.hxtia.workbd.pojo.vo.request.save.AdminUserEditReqVo;
import run.hxtia.workbd.pojo.vo.request.save.AdminUserRegisterReqVo;
import run.hxtia.workbd.pojo.vo.request.save.AdminUserReqVo;
import run.hxtia.workbd.pojo.vo.response.AdminLoginVo;
import run.hxtia.workbd.pojo.vo.response.UserVo;

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

   // reqVo -> po  【用来做数据库保存】

   @Mapping(source = "nickName", target = "nickname")
   User wxReqVo2po(WxMaUserInfo wxReqVo);
   AdminUsers reqVo2po(AdminUserReqVo reqVo);
   AdminUsers reqVo2po(AdminUserRegisterReqVo reqVo);
   AdminUsers reqVo2po(AdminUserEditReqVo reqVo);

   // reqVo -> wxSdk
    WxMaSubscribeMessage reqVo2wxVo(WxSubscribeMessageReqVo reqVo);

}
