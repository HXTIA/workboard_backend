package run.hxtia.workbd.common.shiro;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * 为了传递Token令牌给认证器
 */
@Data
public class Token implements AuthenticationToken {

    private final String token;

    public Token(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
