package run.hxtia.workbd.service.organization;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.College;
import run.hxtia.workbd.pojo.vo.organization.request.CollegeEditReqVo;
import run.hxtia.workbd.pojo.vo.organization.request.CollegeReqVo;
import run.hxtia.workbd.pojo.vo.common.request.page.PageReqVo;
import run.hxtia.workbd.pojo.vo.organization.request.page.CollegePageReqVo;
import run.hxtia.workbd.pojo.vo.organization.response.CollegeVo;
import run.hxtia.workbd.pojo.vo.common.response.result.PageVo;

/**
 * @author Xiaojin
 * @date 2024/5/5
 */
@Transactional(readOnly = true)
public interface CollegeService extends IService<College> {

    /**
     * 添加学院
     * @param reqVo ：学院信息
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean save(CollegeReqVo reqVo);

    // 更新学院信息
    @Transactional(readOnly = false)
    boolean update(CollegeEditReqVo reqVo);


    /**
     * 删除学院
     * @param collegeId ：学院ID
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean delete(Integer collegeId);

    /**
     * 获取学院信息
     * @param collegeId:学院id
     * @return 学院信息
     */
    CollegeVo getCollegeInfoById(Integer collegeId);

    /**
     * 获取所有学院信息
     * @return 所有学院信息
     */
    PageVo<CollegeVo> getList();

    /**
     * 分页获取学院信息
     * @param reqVo ：分页请求参数
     * @return 分页的学院信息
     */
    PageVo<CollegeVo> getPageList(CollegePageReqVo reqVo);

    /**
     * 检查学院是否存在。
     * @param collegeId 学院的ID。
     * @return 如果学院存在，则为true，否则为false。
     */
    boolean checkClgInfo(Integer collegeId);

    /**
     * 注册默认学院。
     * @param collegeInfo 要注册的学院的信息。
     * @return 如果注册成功，则为true，否则为false。
     */
    @Transactional(readOnly = false)
    boolean saveDefaultRegisterClg(College collegeInfo);
}
