package run.hxtia.workbd.service.miniapp.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.common.util.Streams;
import run.hxtia.workbd.mapper.UserWorkMapper;
import run.hxtia.workbd.pojo.po.UserWork;
import run.hxtia.workbd.pojo.vo.request.save.UserWorkReqVo;
import run.hxtia.workbd.service.miniapp.WxUserWorkService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信用户作业模块 业务层
 */
@Service
public class WxUserWorkServiceImpl
    extends ServiceImpl<UserWorkMapper, UserWork> implements WxUserWorkService {

    /**
     * 根据用户ID获取用户所有作业
     * @param userId：用户ID
     * @return ：Map《作业ID，用户的作业》
     */
    @Override
    public Map<Long, UserWork> mapUserWork(Long userId) {
        if (userId == null || userId <= 0 ) return null;

        // 构建 SQL
        MpLambdaQueryWrapper<UserWork> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(UserWork::getUserId, userId);
        List<UserWork> userWorks = baseMapper.selectList(wrapper);
        Map<Long, UserWork> result = new HashMap<>();
        for (UserWork userWork : userWorks) {
            result.put(userWork.getWorkId(), userWork);
        }
        return result;
    }

    /**
     * 设置作业置顶、作业状态
     * @param pinReqVo ：所需参数
     * @return ：是否成功
     */
    @Override
    public boolean update(UserWorkReqVo pinReqVo) {
        UserWork userWork = get(pinReqVo.getUserId(), pinReqVo.getWorkId());

        if (userWork == null) return false;

        if (pinReqVo.getPinStatus() != null) {
            userWork.setPin(pinReqVo.getPinStatus());
        } else if (pinReqVo.getStatus() != null) {
            userWork.setStatus(pinReqVo.getStatus());
        } else {
            return false;
        }

        return baseMapper.updateById(userWork) > 0;
    }

    /**
     * 根据用户ID、作业ID 获取 用户作业
     * @param userId：用户ID
     * @param workId：作业ID
     * @return ：用户作业
     */
    private UserWork get(Long userId, Long workId) {
        MpLambdaQueryWrapper<UserWork> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(UserWork::getUserId, userId).
            eq(UserWork::getWorkId, workId);
        return baseMapper.selectOne(wrapper);
    }

}
