package run.hxtia.workbd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import run.hxtia.workbd.pojo.po.Homework;
import run.hxtia.workbd.pojo.vo.notificationwork.response.HomeworkVo;

@Repository
public interface HomeworkMapper extends BaseMapper<Homework> {

}
