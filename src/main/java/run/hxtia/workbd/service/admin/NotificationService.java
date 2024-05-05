package run.hxtia.workbd.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.Notification;
import run.hxtia.workbd.pojo.vo.request.page.NotificationPageReqVo;
import run.hxtia.workbd.pojo.vo.request.save.NotificationReqVo;
import run.hxtia.workbd.pojo.vo.response.NotificationVo;
import run.hxtia.workbd.pojo.vo.result.PageVo;

@Transactional(readOnly = true)
public interface NotificationService extends IService<Notification> {

    /**
     * 分页查询通知
     * @param pageReqVo：分页信息
     * @param status：通知状态 【1：可用通知 0：历史通知】
     * @return 分页后的数据
     */
    PageVo<NotificationVo> list(NotificationPageReqVo pageReqVo, Short status);

    /**
     * 保存 or 编辑通知
     * @param reqVo：通知信息
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean saveOrUpdate(NotificationReqVo reqVo) throws Exception;

    /**
     * 删除一条or多条通知【逻辑删除】
     * @param ids：需要删除的通知ID
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean removeByIds(String ids);

    /**
     * 根据通知ID获取通知信息
     * @param notificationId ：通知ID
     * @return ：通知数据
     */
    NotificationVo getByNotificationId(Long notificationId);

    /**
     * 删除一条or多条通知【彻底删除】
     * @param ids：需要删除的通知ID
     * @return ：是否成功
     */
    boolean removeHistory(String ids);

}
