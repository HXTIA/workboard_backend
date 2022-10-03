package run.hxtia.workbd.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.mapper.UserWorkMapper;
import run.hxtia.workbd.pojo.po.UserWork;
import run.hxtia.workbd.service.admin.UserWorkService;

import java.util.List;


/**
 * 后台管理用户作业模块 业务层
 */
@Service
public class UserWorkServiceImpl
    extends ServiceImpl<UserWorkMapper, UserWork> implements UserWorkService {

    /**
     * 根据作业ID删除 用户作业
     * @param workIds：作业ID
     * @return ：是否成功
     */
    @Override
    public boolean removeByWorkId(List<String> workIds) {
        if (CollectionUtils.isEmpty(workIds)) return false;
        MpLambdaQueryWrapper<UserWork> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.in(UserWork::getWorkId, workIds);
        return baseMapper.delete(wrapper) > 0;
    }
}
