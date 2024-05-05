package run.hxtia.workbd.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.mapper.NotificationMapper;
import run.hxtia.workbd.mapper.StudentNotificationMapper;
import run.hxtia.workbd.pojo.po.Student;
import run.hxtia.workbd.pojo.po.StudentNotification;
import run.hxtia.workbd.service.admin.StudentNotificationService;

import java.util.List;


@Service
public class StudentNotificationImpl
    extends ServiceImpl<StudentNotificationMapper,StudentNotification> implements StudentNotificationService{

    /**
     * 根据通知ID删除 通知作业
     * @param notificationIds：通知ID
     * @return ：是否成功
     */
    @Override
    public boolean removeByNotificationId(List<String> notificationIds) {
        if (CollectionUtils.isEmpty(notificationIds)) return false;
        MpLambdaQueryWrapper<StudentNotification> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.in(StudentNotification::getNotificationId, notificationIds);
        return baseMapper.delete(wrapper) > 0;
    }


}
