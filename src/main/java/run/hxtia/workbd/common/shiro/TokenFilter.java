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
 * 自定义Shiro过滤器
 */
@Slf4j
public class TokenFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        boolean isWxInterface = request.getRequestURI().startsWith(Constants.WxMiniApp.WX_PREFIX);
        // 判断URI是否是 wx的接口，如果是，验证Token是否有效即可放行，进入下一链条
        if (isWxInterface) {
            checkToken(request, Constants.WxMiniApp.WX_PREFIX);
            return true;
        }

        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // 来到这，去验证后台的接口，并且返回token，需要去加载权限
        String token = checkToken(request, Constants.Web.ADMIN_PREFIX);

        // 去认证且授权
        SecurityUtils.getSubject().login(new Token(token));

        return true;
    }

    /**
     * 检查有无Token、是否有效
     * @param request：请求对象
     * @param prefix：用于获取缓存信息的前缀
     * @return ：返回Token
     */
    private String checkToken(HttpServletRequest request, String prefix) {
        // 不能放在外面，因为过滤器会先装载
        Redises redises = Redises.getRedises();
        // 取出Token

        String token = request.getHeader(Constants.Web.HEADER_TOKEN);
        log.debug("onAccessDenied - {}", token);

        // 如果没有Token
        if (!StringUtils.hasLength(token)) {
            return JsonVos.raise(CodeMsg.NO_TOKEN);
        }

        // 如果Token过期了
        if (redises.getExpire(prefix, token) < 0) {
            return JsonVos.raise(CodeMsg.TOKEN_EXPIRED);
        }

        // 需要刷新一下Token 信息
        redises.expire(prefix, token, Constants.Date.EXPIRE_DATS, TimeUnit.DAYS);

        return token;
    }

}
