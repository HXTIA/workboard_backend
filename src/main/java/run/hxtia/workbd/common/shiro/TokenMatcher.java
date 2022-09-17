package run.hxtia.workbd.common.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

@Slf4j
public class TokenMatcher implements CredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        /*
            Q：这里直接放行，不需要验证 shiro密码
            A：因为已经经过了token的认证，用户肯定已经登录成功了
         */
        log.debug("TokenMatcher：authenticationToken - {} authenticationInfo - {}", authenticationToken, authenticationInfo);
        return true;
    }
}
