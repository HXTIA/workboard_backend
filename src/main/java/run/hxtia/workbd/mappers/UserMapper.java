package run.hxtia.workbd.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import run.hxtia.workbd.pojo.po.User;

@Repository
public interface UserMapper extends BaseMapper<User> {
}
