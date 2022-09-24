package run.hxtia.workbd.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import java.security.InvalidParameterException;

/**
 * 加密工具类
 */
@Slf4j
public class Md5s {

    public final static int DEFAULT_SALT_LEN = 6;

    /**
     * 用来加密
     *
     * @param text 明文
     * @param salt  密钥
     * @return 密文
     */
    public static String md5(String text, String salt) {
        // 加密后的字符串 加盐操作
        System.out.println(DigestUtils.md5Hex(text + salt));
        return DigestUtils.md5Hex(text + salt);
    }

    /**
     * 用来验证是否Md5正确
     * @param text 明文
     * @param salt  密钥
     * @return 是否通过
     */
    public static boolean verify(String text, String salt, String md5) {
        try {
            // 根据传入的密钥进行验证
            String md5Text = md5(text, salt);
            if (md5Text.equalsIgnoreCase(md5)) { // 相同
                System.out.println("MD5验证通过");
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error("Md5s - verify 失败！ message - {}", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
