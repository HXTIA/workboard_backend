package run.hxtia.workbd.service.NotificationWork.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import run.hxtia.workbd.common.enhance.MpLambdaQueryWrapper;
import run.hxtia.workbd.common.enhance.MpPage;
import run.hxtia.workbd.common.mapstruct.MapStructs;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.common.util.Streams;
import run.hxtia.workbd.mapper.NotificationMapper;
import run.hxtia.workbd.pojo.po.Notification;
import run.hxtia.workbd.pojo.vo.request.page.NotificationPageReqVo;
import run.hxtia.workbd.pojo.vo.request.save.NotificationReqVo;
import run.hxtia.workbd.pojo.vo.response.NotificationVo;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;
import run.hxtia.workbd.pojo.vo.result.PageVo;
import run.hxtia.workbd.service.NotificationWork.NotificationService;
import run.hxtia.workbd.service.NotificationWork.StudentNotificationService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements NotificationService {
    private final StudentNotificationService studentNotificationService;

    /**
     * 分页查询通知
     * @param pageReqVo：分页信息
     * @param type：通知类型·
     * @return 分页后的数据
     */
    @Override
    public PageVo<NotificationVo> list(NotificationPageReqVo pageReqVo, String type) {
        // 构建查询条件
    //        1. 创建一个查询包装器（`QueryWrapper`）来构建查询条件。
        QueryWrapper<Notification> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(Notification::getTitle, pageReqVo.getKeyword())
            .or().like(Notification::getMessage, pageReqVo.getKeyword())
            .eq(Notification::getType, type);

        // 构建分页结果
        Page<Notification> resultPage = baseMapper.selectPage(new MpPage<>(pageReqVo), queryWrapper);
        // 返回分页结果
        return baseMapper.selectPage(new MpPage<>(pageReqVo),queryWrapper)
            .buildVo(MapStructs.INSTANCE::po2vo);
    }

    /**
     * 保存 or 编辑通知
     * @param reqVo：通知信息
     * @return ：是否成功
     */
    @Override
    public boolean saveOrUpdate(NotificationReqVo reqVo) throws Exception {
        Notification po = MapStructs.INSTANCE.reqVo2po(reqVo);
        try {
            // 保存数据
            return saveOrUpdate(po);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除一条or多条通知【逻辑删除】
     * @param ids：需要删除的通知ID
     * @return ：是否成功
     */
    @Override
    public boolean removeByIds(String ids) {
        List<String> notificationIds = Arrays.asList(ids.split(","));
        if (CollectionUtils.isEmpty(notificationIds)) return false;

        // 查出所有通知【并且设置不可见】
        List<Notification> notifications = Streams.list2List(baseMapper.selectBatchIds(notificationIds), (notification -> {
            notification.setStatus(String.valueOf(Constants.Status.WORK_DISABLE));
            return notification;
        }));

        return updateBatchById(notifications);
    }

    /**
     * 根据通知ID获取作业信息
     * @param notificationId ：通知ID
     * @return ：通知数据
     */
    @Override
    public NotificationVo getByNotificationId(Long notificationId) {
        if (notificationId == null || notificationId <= 0) return null;
        MpLambdaQueryWrapper<Notification> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(Notification::getId, notificationId).eq(Notification::getStatus, Constants.Status.WORK_ENABLE);
        return MapStructs.INSTANCE.po2vo(baseMapper.selectOne(wrapper));
    }

    /**
     * 删除一条or多条通知【彻底删除】
     * @param ids：需要删除的通知ID
     * @return ：是否成功
     */
    @Override
    public boolean removeHistory(String ids) {
        if (!StringUtils.hasLength(ids)) return false;

        List<String> notificationIds = Arrays.asList(ids.split(","));
        // 检查通知是否能删除
//        checkNotificationRemove(notificationIds);
        boolean deleteNotification = removeByIds(notificationIds);
        boolean deleteStudentNotification = studentNotificationService.removeByNotificationId(notificationIds);
        // 在用户作业表里删除通知
        if (!deleteNotification || !deleteStudentNotification) {
            return JsonVos.raise(CodeMsg.REMOVE_ERROR);
        }
        return true;
    }

    /**
     * 检查作业是否能删除【只有是历史作业才能彻底删除】
     * @param workIds ：检查的作业ID
     */
    private void checkNotificationRemove(List<String> workIds) {
        if (CollectionUtils.isEmpty(workIds)) return;
        List<Notification> notifications = listByIds(workIds);
        for (Notification notification : notifications) {
            if (Constants.Status.WORK_ENABLE.equals(notification.getStatus())) {
                JsonVos.raise(CodeMsg.WRONG_WORK_NO_REMOVE);
            }
        }
    }
}
