package run.hxtia.workbd.service.NotificationWork;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.StudentNotification;

import java.util.List;

@Transactional(readOnly = true)
public interface StudentNotificationService extends IService<StudentNotification>{

    /**
     * 根据通知ID删除 用户通知
     * @param notificationIds：通知ID
     * @return ：是否成功
     */
    boolean removeByNotificationId(List<String> notificationIds);
}



