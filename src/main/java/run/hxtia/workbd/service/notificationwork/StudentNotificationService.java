package run.hxtia.workbd.service.notificationwork;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.StudentNotification;
import run.hxtia.workbd.pojo.vo.common.response.result.ExtendedPageVo;
import run.hxtia.workbd.pojo.vo.common.response.result.PageVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.page.StudentNotificationPageReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.response.NotificationVo;

import java.util.List;

@Transactional(readOnly = true)
public interface StudentNotificationService extends IService<StudentNotification>{

    /**
     * 根据通知ID删除 用户通知
     * @param notificationIds：通知ID
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean removeByNotificationId(List<String> notificationIds);

    /**
     * 根据学生ID分页获取学生通知
     * @param reqVo 分页和学生ID信息
     * @return 分页后的学生通知列表
     */
    PageVo<NotificationVo> getNotificationListByStuId(StudentNotificationPageReqVo reqVo);
}



