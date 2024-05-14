package run.hxtia.workbd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import run.hxtia.workbd.pojo.po.StudentNotification;
import run.hxtia.workbd.pojo.vo.notificationwork.response.NotificationVo;


@Repository
public interface StudentNotificationMapper extends BaseMapper<StudentNotification> {

    @Select("SELECT n.* FROM student_notifications sn " +
        "JOIN notifications n ON sn.notification_id = n.id " +
        "WHERE sn.student_id = #{studentId}")
    Page<NotificationVo> selectNotificationsByStudentId(Page<?> page, @Param("studentId") String studentId);
}
