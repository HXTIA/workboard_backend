package run.hxtia.workbd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import run.hxtia.workbd.pojo.po.College;

/**
 * @author Xiaojin
 * @date 2024/5/5
 */

@Repository
public interface CollegeMapper extends BaseMapper<College> {

    /**
     * 用户注册时，给其注册默认的组织
     * @param po：空的学院信息
     * @return ：是否成功
     */
    int insertDefaultRegisterCollege(College po);
}
