package run.hxtia.workbd.service.notificationwork.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.mapper.StudentNotificationMapper;
import run.hxtia.workbd.pojo.po.StudentNotification;
import run.hxtia.workbd.service.notificationwork.StudentNotificationService;

import java.util.List;


@Service
public class StudentNotificationImpl
    extends ServiceImpl<StudentNotificationMapper,StudentNotification> implements StudentNotificationService{

    /**
     * 根据通知ID列表删除所有相关的学生通知记录
     * @param notificationIds 通知ID列表
     * @return 如果删除的记录数大于0，返回true，否则返回false
     */
    @Override
    @Transactional(readOnly = false)
    public boolean removeByNotificationId(List<String> notificationIds) {
        // 如果提供的通知ID列表为空，立即返回false
        if (CollectionUtils.isEmpty(notificationIds)) return false;

        // 创建一个新的查询包装器
        MpLambdaQueryWrapper<StudentNotification> wrapper = new MpLambdaQueryWrapper<>();

        // 添加一个条件，指定notificationId必须在提供的列表中
        wrapper.in(StudentNotification::getNotificationId, notificationIds);

        // 删除所有符合条件的记录，并返回是否成功
        return baseMapper.delete(wrapper) >= 0;
    }


}
