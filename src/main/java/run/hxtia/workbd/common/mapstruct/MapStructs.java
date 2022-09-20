package run.hxtia.workbd.common.mapstruct;

import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import run.hxtia.workbd.pojo.po.Skill;
import run.hxtia.workbd.pojo.po.User;
import run.hxtia.workbd.pojo.vo.request.WxSubscribeMessageReqVo;
import run.hxtia.workbd.pojo.vo.request.save.SkillReqVo;
import run.hxtia.workbd.pojo.vo.request.save.UserReqVo;
import run.hxtia.workbd.pojo.vo.response.SkillVo;
import run.hxtia.workbd.pojo.vo.response.UserVo;
import run.hxtia.workbd.pojo.vo.result.LoginVo;

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
    2、可以解决转换类型不匹配、参数名不匹配的问题。
    （1）source：源对象
    （2）target：目标对象
    （3）qualifiedBy：找转换器中的方法
    */
   @Mapping(
       source = "createdTime",
       target = "createdTime",
       qualifiedBy = MapStructFormatter.Date2Millis.class)
   SkillVo po2vo(Skill po);

    @Mapping(source = "createdTime",
        target = "createdTime",
        qualifiedBy = MapStructFormatter.Date2Millis.class)
    @Mapping(source = "loginTime",
        target = "loginTime",
        qualifiedBy = MapStructFormatter.Date2Millis.class)
    UserVo po2vo(User po);

    LoginVo po2loginVo(User po);

   // reqVo -> po  【用来做数据库保存】
   Skill reqVo2po(SkillReqVo reqVo);
   User reqVo2po(UserReqVo reqVo);

   // reqVo -> wxSdk
    WxMaSubscribeMessage reqVo2wxVo(WxSubscribeMessageReqVo reqVo);

}
