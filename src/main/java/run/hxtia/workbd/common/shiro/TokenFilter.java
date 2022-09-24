package run.hxtia.workbd.common.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.util.StringUtils;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.common.util.Constants;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.pojo.vo.result.CodeMsg;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * 自定义Shiro拦截器
 */
@Slf4j
public class TokenFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        // 不能放在外面，因为过滤器会先装载
        Redises redises = Redises.getRedises();
        // 取出Token
        String key = Constants.Web.HEADER_TOKEN;

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader(key);
        log.debug("onAccessDenied - {}", token);

        // 如果没有Token
        if (!StringUtils.hasLength(token)) return JsonVos.raise(CodeMsg.NO_TOKEN);

        // 如果Token过期了
        if (redises.getExpire(key, token) < 0) {
            return JsonVos.raise(CodeMsg.TOKEN_EXPIRED);
        }

        // 需要刷新一下Token 信息
        redises.expire(key, token, Constants.Date.EXPIRE_DATS, TimeUnit.DAYS);

        //TODO: 这里决定是否需要去鉴权
        SecurityUtils.getSubject().login(new Token(token));

        return true;
    }
}
