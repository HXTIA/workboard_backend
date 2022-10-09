package run.hxtia.workbd.common.util;

import com.pig4cloud.captcha.ArithmeticCaptcha;
import com.pig4cloud.captcha.base.Captcha;
import org.springframework.util.StringUtils;
import run.hxtia.workbd.common.cache.Caches;
import run.hxtia.workbd.pojo.vo.response.CaptchaVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 图形验证码工具类
 */
public class Captchas {

    /**
     * 配置算数验证码【使用 Ehcache】
     * @return ：存储的 key + 图片数据
     */
    public static CaptchaVo out() {
        ArithmeticCaptcha arithmeticCaptcha = buildCaptcha();
        String verifyKey = Strings.getUUID(6);
        Caches.putCode(Constants.VerificationCode.IMAGE_CODE_PREFIX + verifyKey, arithmeticCaptcha.text());
        return new CaptchaVo(verifyKey, arithmeticCaptcha.toBase64());
    }

    /**
     * 配置算数验证码【使用 Session】
     */
    public static void out(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArithmeticCaptcha arithmeticCaptcha = buildCaptcha();
        // 验证码结果存入session
        request.getSession().setAttribute(Constants.VerificationCode.IMAGE_CODE_PREFIX, arithmeticCaptcha.text());

        // 构建响应体
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        arithmeticCaptcha.out(response.getOutputStream());
    }

    /**
     * 构建算数验证码
     * @return ：算数验证码对象
     */
    private static ArithmeticCaptcha buildCaptcha() {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha();
        captcha.setLen(3);  // 几位数运算，默认是两位
        captcha.supportAlgorithmSign(4); // 可设置支持的算法
        captcha.setDifficulty(10); // 设置计算难度，参与计算的每一个整数的最大值
        return captcha;
    }

    /**
     * 验证 verifyCode
     * @param verifyCode：验证码
     * @return ：是否正确
     */
    public static boolean ver(String verifyCode, HttpServletRequest request) {
        if (!StringUtils.hasLength(verifyCode)) return false;
        // 获取预存的 result
        String result = (String) request.getSession().
            getAttribute(Constants.VerificationCode.IMAGE_CODE_PREFIX);
        return result != null && result.equals(verifyCode);
    }

    /**
     * 验证 verifyCode
     * @param verifyKey：返回验证码时生成的 Key
     * @param verifyCode：验证码
     * @return ：是否正确
     */
    public static boolean ver(String verifyKey, String verifyCode) {
        if (!StringUtils.hasLength(verifyKey) || !StringUtils.hasLength(verifyCode)) return false;
        return Caches.checkCode(Constants.VerificationCode.IMAGE_CODE_PREFIX, verifyKey, verifyCode);
    }

}
