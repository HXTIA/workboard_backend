package run.hxtia.workbd.service.organization;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.Grade;
import run.hxtia.workbd.pojo.vo.organization.request.GradeEditReqVo;
import run.hxtia.workbd.pojo.vo.organization.request.GradeReqVo;
import run.hxtia.workbd.pojo.vo.organization.response.GradeVo;
import run.hxtia.workbd.pojo.vo.common.response.result.PageVo;

import java.util.List;

/**
 * @author Xiaojin
 * @date 2024/5/5
 */
/**
 * 年级服务接口
 */
@Transactional(readOnly = true)
public interface GradeService extends IService<Grade> {

    /**
     * 添加年级
     * @param reqVo ：年级信息
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean save(GradeReqVo reqVo);

    /**
     * 更新年级信息
     * @param reqVo ：年级信息
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean update(GradeEditReqVo reqVo);

    /**
     * 删除年级
     * @param gradeId ：年级ID
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean delete(Integer gradeId);

    /**
     * 获取年级信息
     * @param gradeId:年级id
     * @return 年级信息
     */
    GradeVo getGradeInfoById(Integer gradeId);

    /**
     * 获取所有年级信息
     * @return 所有年级信息
     */
    PageVo<GradeVo> getList();

    /**
     * 检查年级是否存在。
     * @param gradeId 年级的ID。
     * @return 如果年级存在，则为true，否则为false。
     */
    boolean checkGradeInfo(Integer gradeId);

    // TODO 根据学院ID获取年级信息
    /**
     * 根据学院ID获取年级信息
     * @param collegeId 学院ID
     * @return 所有该学院下的年级信息
     */
    List<GradeVo> getGradeInfoByCollegeId(Integer collegeId);

    // TODO 根据学院ID分页获取年级信息
    /**
     * 根据学院ID分页获取年级信息
     * @param collegeId 学院ID
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 该学院下的年级信息的分页列表
     */
    PageVo<GradeVo> getGradeInfoByCollegeIdWithPagination(Integer collegeId, int pageNum, int pageSize);

    /**
     * 检查年级是否存在
     * @param gradeName 年级名称
     * @param collegeId 学院ID
     * @return 如果年级存在，则为true，否则为false。
     */
    boolean checkGradeExists(String gradeName, Integer collegeId);

}
