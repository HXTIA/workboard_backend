package run.hxtia.workbd.service.usermanagement;

import com.baomidou.mybatisplus.extension.service.IService;
import run.hxtia.workbd.pojo.po.StudentAuthorization;
import run.hxtia.workbd.pojo.vo.usermanagement.request.StudentAuthorizationReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.response.StudentAuthorizationSetVo;
import run.hxtia.workbd.pojo.vo.usermanagement.response.StudentAuthorizationVo;

import java.util.List;

/**
 * @author Xiaojin
 * @date 2024/5/17
 */
public interface StudentAuthorizationService  extends IService<StudentAuthorization> {

    /**
     * 保存学生授权信息
     *
     * @param studentAuthorizationReqVo 学生授权信息请求对象
     * @return boolean 是否保存成功
     */
    boolean saveStudentAuthorization(StudentAuthorizationReqVo studentAuthorizationReqVo);

    /**
     * 删除学生授权信息
     *
     * @param studentId 学生ID
     * @return boolean 是否删除成功
     */
    boolean deleteStudentAuthorization(String studentId);

    /**
     * 更新学生授权信息
     *
     * @param studentAuthorizationReqVo 学生授权信息请求对象
     * @return boolean 是否更新成功
     */
    boolean updateStudentAuthorization(StudentAuthorizationReqVo studentAuthorizationReqVo);

    /**
     * 根据学生ID获取学生授权信息
     *
     * @param studentId 学生ID
     * @return StudentAuthorizationVo 学生授权信息返回对象
     */
    StudentAuthorizationVo getStudentAuthorizationById(String studentId);

    /**
     * 根据学生ID获取学生授权信息
     *
     * @param studentId 学生ID
     * @return StudentAuthorizationVo 学生授权信息返回对象（内含切割好的 Set集合）
     */
    StudentAuthorizationSetVo getStudentAuthorizationSetById(String studentId);

    /**
     * 获取所有学生授权信息
     * @return List<StudentAuthorizationVo> 学生授权信息返回对象列表
     */
    List<StudentAuthorizationVo> getAllStudentAuthorizations();

    boolean saveOrUpdate(StudentAuthorization studentAuthorization);
}
