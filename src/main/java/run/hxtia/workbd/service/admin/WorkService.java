package run.hxtia.workbd.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.Work;
import run.hxtia.workbd.pojo.vo.request.page.WorkPageReqVo;
import run.hxtia.workbd.pojo.vo.request.save.WorkReqVo;
import run.hxtia.workbd.pojo.vo.request.save.WorkUploadReqVo;
import run.hxtia.workbd.pojo.vo.response.WorkVo;
import run.hxtia.workbd.pojo.vo.result.PageVo;

/**
 * 作业模块 业务层
 */
@Transactional(readOnly = true)
public interface WorkService extends IService<Work> {

    /**
     * 分页查询作业
     * @param pageReqVo：分页信息
     * @param status：作业状态 【1：可用作业 0：历史作业】
     * @return 分页后的数据
     */
    PageVo<WorkVo> list(WorkPageReqVo pageReqVo, Short status);

    /**
     * 保存 or 编辑作业
     * @param reqVo：作业信息
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean saveOrUpdate(WorkReqVo reqVo) throws Exception;

    /**
     * 多图片编辑，编辑作业的图片
     * @param uploadReqVo ：所需参数
     * @return ：是否成功
     */
    boolean updatePictures(WorkUploadReqVo uploadReqVo) throws Exception;

    /**
     * 删除一条or多条作业【逻辑删除】
     * @param ids：需要删除的作业ID
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean removeByIds(String ids);

    /**
     * 根据作业ID获取作业信息
     * @param workId ：作业ID
     * @return ：作业数据
     */
    WorkVo getByWorkId(Long workId);

    /**
     * 删除一条or多条作业【彻底删除】
     * @param ids：需要删除的作业ID
     * @return ：是否成功
     */
    boolean removeHistory(String ids);
}
