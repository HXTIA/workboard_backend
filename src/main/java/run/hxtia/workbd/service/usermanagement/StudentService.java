package run.hxtia.workbd.service.usermanagement;


import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.dto.StudentInfoDto;
import run.hxtia.workbd.pojo.po.Student;
import run.hxtia.workbd.pojo.vo.notificationwork.response.StudentVo;
import run.hxtia.workbd.pojo.vo.organization.response.OrganizationVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.StudentAvatarReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.StudentReqVo;

import java.util.List;

@Transactional(readOnly = true)
public interface StudentService extends IService<Student> {

    /**
     * 根据 code验证码换取 session_key + openId
     * @param code：验证码
     * @return ：session_key + openId
     */
    String getToken(String code) throws Exception;

    /**
     * 验证 Token 是否有效
     * @param token token
     * @return 是否有效
     */
    Boolean checkToken(String token) throws Exception;

    /**
     * 认证登录信息
     * @param token：认证参数
     * @return ：用户信息
     */
    @Transactional(readOnly = false)
    StudentInfoDto getStudentByToken(String token);

    /**
     * 完善用户信息
     * @param reqVo：用户信息
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean update(StudentReqVo reqVo, String token);

    /**
     * 用户上传头像
     * @param reqVo：头像数据
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean update(StudentAvatarReqVo reqVo, String token) throws Exception;


}
