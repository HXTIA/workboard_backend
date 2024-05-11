package run.hxtia.workbd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import run.hxtia.workbd.pojo.po.StudentHomework;
import run.hxtia.workbd.pojo.vo.notificationwork.response.HomeworkVo;

/**
 * 用户作业 持久层
 */
@Repository
public interface StudentHomeworkMapper extends BaseMapper<StudentHomework> {
    @Select("SELECT h.* FROM homeworks h " +
        "WHERE h.student_id = #{studentId}")
    Page<HomeworkVo> selectHomeworksByStudentId(Page<?> page, @Param("studentId") Long studentId);
}
