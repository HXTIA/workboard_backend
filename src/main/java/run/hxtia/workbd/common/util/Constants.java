package run.hxtia.workbd.common.util;

/**
 * 放一些常量信息
 * 不要全放在一起，每个模块，建立自己的内部类
 */
public class Constants {

    /**
     * 用户状态常量
     */
    public static class Users {
        public static final int UNABLE = 0;
        public static final int ENABLE = 1;
        public static final String PREFIX = "user";
        public static final String DEFAULT_ROLE = "超级管理员";
    }

    /**
     * 微信小程序常量
     */
    public static class WxMiniApp {
        public static final String TOKEN_PREFIX = "wx:user:";
    }

    /**
     * Web 应用常量
     */
    public static class Web {
        public static final String HEADER_TOKEN = "Token";
        public static final String ERROR_URI = "/handleError";
    }

    /**
     * 时间常量
     */
    public static class Date {
        // 过期时间
        public static final int EXPIRE_DATS = 7;
    }

}
