package run.hxtia.workbd.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 加密工具类
 */
@Slf4j
public class Md5s {

    public final static String md5key = "hxtia";

    /**
     * 用来加密
     *
     * @param text 明文
     * @param key  密钥
     * @return 密文
     */
    public static String md5(String text, String key) {
        // 加密后的字符串 加盐操作
        System.out.println(DigestUtils.md5Hex(text + key));
        return DigestUtils.md5Hex(text + key);
    }

    /**
     * 用来验证是否Md5正确
     * @param text 明文
     * @param key  密钥
     * @return 是否通过
     */
    public static boolean verify(String text, String key, String md5) {
        try {
            // 根据传入的密钥进行验证
            String md5Text = md5(text, key);
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
