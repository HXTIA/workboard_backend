package run.hxtia.workbd.service.organization;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.Classes;
import run.hxtia.workbd.pojo.vo.request.organization.ClassEditReqVo;
import run.hxtia.workbd.pojo.vo.request.organization.ClassReqVo;
import run.hxtia.workbd.pojo.vo.response.organization.ClassVo;
import run.hxtia.workbd.pojo.vo.result.PageVo;

import java.util.List;

/**
 * @author Xiaojin
 * @date 2024/5/5
 */
@Transactional(readOnly = true)
public interface ClassService extends IService<Classes> {

    /**
     * 添加班级
     * @param reqVo ：班级信息
     * @param gradeId ：年级ID
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean save(ClassReqVo reqVo, Integer gradeId);

    /**
     * 更新班级信息
     * @param reqVo ：班级信息
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean update(ClassEditReqVo reqVo);

    /**
     * 删除班级
     * @param classId ：班级ID
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean delete(Integer classId);

    /**
     * 获取班级信息
     * @param classId:班级id
     * @return 班级信息
     */
    ClassVo getClassInfoById(Integer classId);

    /**
     * 获取所有班级信息
     * @return 所有班级信息
     */
    PageVo<ClassVo> getList();

    /**
     * 检查班级是否存在。
     * @param classId 班级的ID。
     * @return 如果班级存在，则为true，否则为false。
     */
    boolean checkClassInfo(Integer classId);

    /**
     * 根据年级ID分页获取班级信息
     * @param gradeId 年级ID
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 该年级下的班级信息的分页列表
     */
    PageVo<ClassVo> getClassInfoByGradeIdWithPagination(Integer gradeId, int pageNum, int pageSize);

}
