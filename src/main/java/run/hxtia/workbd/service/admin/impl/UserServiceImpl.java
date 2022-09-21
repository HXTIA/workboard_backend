package run.hxtia.workbd.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import run.hxtia.workbd.mapper.UserMapper;
import run.hxtia.workbd.pojo.po.User;
import run.hxtia.workbd.service.admin.UserService;

@Service
public class UserServiceImpl
    extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 用户登录
     * @param reqVo：登录数据
     * @return ：LoginVo
     *//*
    @Override
    public LoginVo login(LoginReqVo reqVo) {

        //TODO: 加强LambdaQueryWrapper
        // 通过邮箱查询user
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, reqVo.getEmail());
        User userPo = baseMapper.selectOne(wrapper);

        // 验证邮箱
        if (userPo == null) {
            return JsonVos.raise(CodeMsg.WRONG_USERNAME);
        }

        // 验证密码
        boolean verify = Md5s.verify(reqVo.getPassword(), Md5s.DEFAULT_SALT, userPo.getPassword());
        if (!verify) return JsonVos.raise(CodeMsg.WRONG_PASSWORD);

        // 用户状态
        if (userPo.getState() == Constants.UserStatus.UNABLE) {
            return JsonVos.raise(CodeMsg.USER_LOCKED);
        }

        // 更新登录时间
        userPo.setLoginTime(new Date());
        baseMapper.updateById(userPo);

        // 生成Token
        String token = UUID.randomUUID().toString().replace("-", "");
        // 将对象其放入缓存中
        Caches.putToken(token, userPo);

        // 将 po -> vo
        LoginVo vo = MapStructs.INSTANCE.po2loginVo(userPo);
        vo.setToken(token);

        return vo;
    }*/
}
