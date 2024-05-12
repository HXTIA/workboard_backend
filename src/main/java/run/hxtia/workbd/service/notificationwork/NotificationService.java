package run.hxtia.workbd.service.notificationwork;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import run.hxtia.workbd.pojo.po.Notification;
import run.hxtia.workbd.pojo.vo.notificationwork.request.page.NotificationPageReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.request.NotificationReqVo;
import run.hxtia.workbd.pojo.vo.notificationwork.response.NotificationVo;
import run.hxtia.workbd.pojo.vo.common.response.result.PageVo;

/**
 * @author Xiaojin
 * @date 2024/5/9
 */
/**
 * 通知模块 业务层
 */
@Transactional(readOnly = true)
public interface NotificationService extends IService<Notification> {

    /**
     * 分页查询通知
     * @param pageReqVo：分页信息
     * @param type：通知类型
     * @return 分页后的数据
     */
    PageVo<NotificationVo> listPage(NotificationPageReqVo pageReqVo, String type);

    /**
     * 保存 or 编辑通知
     * @param reqVo：通知信息
     * @return ：是否成功
     */
    @Transactional(readOnly = false)
    boolean saveOrUpdate(NotificationReqVo reqVo);

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
    @Transactional(readOnly = false)
    boolean removeHistory(String ids);

}
