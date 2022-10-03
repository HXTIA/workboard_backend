package run.hxtia.workbd.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import run.hxtia.workbd.common.util.Streams;
import run.hxtia.workbd.mapper.RoleMapper;
import run.hxtia.workbd.pojo.po.Role;
import run.hxtia.workbd.service.admin.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl
    extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
