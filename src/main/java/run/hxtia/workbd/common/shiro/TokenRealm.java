package run.hxtia.workbd.common.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 *  自定义Shiro数据源Realm
 */
@Slf4j
public class TokenRealm extends AuthorizingRealm {

    public TokenRealm(TokenMatcher matcher) {
        super(matcher);
    }

    /**
     * 用于认证器所需要的Token
     * @param token : 认证器的那个token
     * @return ：是否符合要求
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        // 这里只支持我自定义的Token令牌
        log.debug("supports--------{}", token);
        return token instanceof Token;
    }

    /**
     * TODO: 若需要，在这给shiro用户授予权限、角色
     * 授权器
     * @param principals ：认证器认证成功传过来的shiro信息【Shiro的用户名和密码】
     * @return 该shiro用户所拥有的权限和角色信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.debug("AuthorizationInfo----{}", principals);
        return null;
    }

    /**
     * 认证器 【SecurityUtils.getSubject().login(new Token(token)) 会触发此认证器】
     * @param authenticationToken：token
     * @return ：认证成功传出去的 信息【Shiro的用户名和密码】
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = ((Token) authenticationToken).getToken();
        log.debug("AuthenticationInfo-----{}", token);
        return new SimpleAuthenticationInfo(token, token, getName());
    }
}
