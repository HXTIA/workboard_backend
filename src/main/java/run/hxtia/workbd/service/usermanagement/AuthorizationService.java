package run.hxtia.workbd.service.usermanagement;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.Authorization;
import run.hxtia.workbd.pojo.vo.usermanagement.request.CourseAndClassByAuthReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.response.CourseAndClassVo;

/**
 * @author Xiaojin
 * @date 2024/5/14
 */

@Transactional(readOnly = true)
public interface AuthorizationService extends IService<Authorization> {


    // 根据第投权的列表(课程、班级)
    /**
     * 认证登录信息
     * @param token：认证参数
     * @return ：用户信息
     */
    CourseAndClassVo getCourseAndClasslistByAuth(String token);
}
