package run.hxtia.workbd.common.util;

import org.springframework.util.StringUtils;
import run.hxtia.workbd.common.redis.Redises;
import run.hxtia.workbd.pojo.vo.common.response.result.CodeMsg;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

public class Tokens {
    // 不能放在外面，因为过滤器会先装载
    private final static Redises redises = Redises.getRedises();

    /**
     * 获取 Token 并校验
     * @param request：请求对象
     * @param tokenFrom：Token 从哪来
     * @param prefix：查询 Redis 前缀
     * @return ： Token
     */
    public static String checkToken(HttpServletRequest request, String tokenFrom, String prefix) {
        // 取出Token
        String token = request.getHeader(tokenFrom);

        // 如果没有Token
        if (!StringUtils.hasLength(token) || !redises.hasKey(prefix+token)) {
            return JsonVos.raise(CodeMsg.NO_TOKEN);
        }

        // 如果 Token 过期了
        if (redises.getExpire(prefix, token) == -2) {
            return JsonVos.raise(CodeMsg.NO_TOKEN);
        }

        // 需要刷新一下Token 信息
        redises.expire(prefix, token, Constants.Date.EXPIRE_DATS, TimeUnit.DAYS);

        return token;
    }

}
