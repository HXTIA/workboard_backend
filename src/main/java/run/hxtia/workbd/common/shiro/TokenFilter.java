package run.hxtia.workbd.common.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.util.StringUtils;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.common.util.Tokens;
import run.hxtia.workbd.pojo.vo.common.response.result.CodeMsg;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * 自定义Shiro过滤器
 */
@Slf4j
public class TokenFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // 来到这，去验证后台的接口，并且返回token，需要去加载权限
        String token = Tokens.checkToken(request, Constants.Web.HEADER_TOKEN, Constants.Web.ADMIN_PREFIX);

        // 去认证且授权
        SecurityUtils.getSubject().login(new Token(token));

        return true;
    }
}
