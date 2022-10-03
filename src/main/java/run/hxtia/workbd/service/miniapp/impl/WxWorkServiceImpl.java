package run.hxtia.workbd.service.miniapp.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.common.enhance.MpPage;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.util.Streams;
import run.hxtia.workbd.mapper.WorkMapper;
import run.hxtia.workbd.pojo.po.UserWork;
import run.hxtia.workbd.pojo.po.Work;
import run.hxtia.workbd.pojo.vo.request.page.base.WxWorkPageReqVo;
import run.hxtia.workbd.pojo.vo.response.UserWorkVo;
import run.hxtia.workbd.pojo.vo.result.PageVo;
import run.hxtia.workbd.service.miniapp.WxUserWorkService;
import run.hxtia.workbd.service.miniapp.WxWorkService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * C端作业模块 业务层
 */
@Service
@RequiredArgsConstructor
public class WxWorkServiceImpl
    extends ServiceImpl<WorkMapper, Work> implements WxWorkService {

    private final WxUserWorkService userWorkService;

    /**
     * 根据用户ID获取所有作业
     * @param userId ：用户ID
     * @param status：作业状态 【1：可用作业 0：历史作业】
     * @return ：用户的作业
     */
    @Override
    public List<UserWorkVo> list(Long userId, Short status) {

        // 获取用户的所有作业
        Map<Long, UserWork> userWorkMap = userWorkService.mapUserWork(userId);

        // 获取用户的所有作业 ID
        Set<Long> workIds = userWorkMap.keySet();
        if (CollectionUtils.isEmpty(workIds)) return null;

        // 获取该用户所有的作业
        List<Work> works = baseMapper.selectBatchIds(workIds);

        // 过滤出需要的作业【历史 or 现存】
        List<Work> filter = Streams.filter(works,
            (work -> status.equals(work.getEnable())));

        // 根据DDL排序
        filter.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));

        // 构建返回结果
        return Streams.map(filter, work2userWorkVo(userWorkMap));
    }

    /**
     * 分页获取用户的所有作业
     * @param pageReqVo：请求参数
     * @param status：作业状态 【1：可用作业 0：历史作业】
     * @return ：分页作业数据
     */
    @Override
    public PageVo<UserWorkVo> list(WxWorkPageReqVo pageReqVo, Short status) {

        // 获取用户的所有作业
        Map<Long, UserWork> userWorkMap = userWorkService.mapUserWork(pageReqVo.getUserId());
        // 获取用户的所有作业 ID
        Set<Long> workIds = userWorkMap.keySet();
        if (CollectionUtils.isEmpty(workIds)) return null;

        MpLambdaQueryWrapper<Work> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.like(pageReqVo.getKeyword(), Work::getTitle, Work::getDetail).
            eq(Work::getEnable, status).in(Work::getId, workIds).
            orderByDesc(Work::getCreatedAt);

        return baseMapper.selectPage(new MpPage<>(pageReqVo), wrapper).
            buildVo(work2userWorkVo(userWorkMap));
    }

    /**
     * 将 Work -> UserWorkVo
     * @param userWorkMap：Map《作业ID，用户的作业》
     * @return ：UserWorkVo
     */
    private  Function<Work, UserWorkVo> work2userWorkVo (Map<Long, UserWork> userWorkMap) {
        return work -> {
            UserWorkVo userWorkVo = MapStructs.INSTANCE.po2userWorkVo(work);
            UserWork userWork = userWorkMap.get(work.getId());
            userWorkVo.setPin(userWork.getPin());
            userWorkVo.setStatus(userWork.getStatus());
            return userWorkVo;
        };
    }
}
