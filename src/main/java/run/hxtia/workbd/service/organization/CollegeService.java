package run.hxtia.workbd.service.organization;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.College;
import run.hxtia.workbd.pojo.vo.request.save.CollegeReqVo;
import run.hxtia.workbd.pojo.vo.response.CollegeVo;
import run.hxtia.workbd.pojo.vo.result.PageVo;

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
}
