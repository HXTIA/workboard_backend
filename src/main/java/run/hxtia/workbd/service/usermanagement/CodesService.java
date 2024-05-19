package run.hxtia.workbd.service.usermanagement;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.dto.CodesInfoDto;
import run.hxtia.workbd.pojo.po.Codes;
import run.hxtia.workbd.pojo.vo.organization.request.CollegeReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.CodeSavaBatchReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.request.CodeSaveReqVo;
import run.hxtia.workbd.pojo.vo.usermanagement.response.CodeAndCourseAndClassInfoVo;
import run.hxtia.workbd.pojo.vo.usermanagement.response.CodesVo;

import java.util.List;

/**
 * @author Xiaojin
 * @date 2024/5/16
 */

public interface CodesService extends IService<Codes> {

    /**
     * 批量增加授权码
     * @param reqVo ：授权码信息
     * @return ：是否成功
     */
    boolean saveBatch(CodeSavaBatchReqVo reqVo);

    /**
     *  根据token获取授权码列表
     *  @param token ：用户token
     *  @return ：授权码列表
     */
    List<CodeAndCourseAndClassInfoVo> getCodelistByUserId(String token);

    /**
     * 保存授权码信息
     * @param codeSaveReqVo ：授权码信息
     * @return ：是否成功
     */
    boolean saveCodesInfo(CodeSaveReqVo codeSaveReqVo);

    /**
     *  删除授权码
     *  @param code ：授权码
     *  @return ：是否成功
     */
    boolean deleteCode(String code);

    /**
     * 更新授权码状态
     * @param code ：授权码
     * @param status ：新的状态
     * @return ：是否成功
     */
    boolean updateCodeStatus(String code, Integer status);

    /**
     * 检查授权码状态
     * @param code ：授权码
     * @return ：授权码状态
     */
    Short checkCodeStatus(String code);

}
