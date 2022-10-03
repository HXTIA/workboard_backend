package run.hxtia.workbd.service.miniapp;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.Work;
import run.hxtia.workbd.pojo.vo.request.page.base.WxWorkPageReqVo;
import run.hxtia.workbd.pojo.vo.response.UserWorkVo;
import run.hxtia.workbd.pojo.vo.result.PageVo;

import java.util.List;

@Transactional(readOnly = true)
public interface WxWorkService extends IService<Work> {

    /**
     * 根据用户ID获取所有作业
     * @param userId ：用户ID
     * @param status：作业状态 【1：可用作业 0：历史作业】
     * @return ：用户的作业
     */
    List<UserWorkVo> list(Long userId, Short status);

    /**
     * 分页获取用户的所有作业
     * @param pageReqVo：请求参数
     * @param status：作业状态 【1：可用作业 0：历史作业】
     * @return ：分页作业数据
     */
    PageVo<UserWorkVo> list(WxWorkPageReqVo pageReqVo, Short status);
}
