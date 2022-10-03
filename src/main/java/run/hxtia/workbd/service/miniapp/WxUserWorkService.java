package run.hxtia.workbd.service.miniapp;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.UserWork;
import run.hxtia.workbd.pojo.vo.request.save.UserWorkReqVo;

import java.util.Map;

/**
 * 微信用户作业模块 业务层
 */
@Transactional(readOnly = true)
public interface WxUserWorkService extends IService<UserWork> {

    /**
     * 根据用户ID获取用户所有作业
     * @param userId：用户ID
     * @return ：Map《作业ID，用户的作业》
     */
    Map<Long, UserWork> mapUserWork(Long userId);

    /**
     * 设置作业置顶、作业状态
     * @param pinReqVo ：所需参数
     * @return ：是否成功
     */
    boolean update(UserWorkReqVo pinReqVo);
}
