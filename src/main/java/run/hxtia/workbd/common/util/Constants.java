package run.hxtia.workbd.common.util;

/**
 * 放一些常量信息
 * 不要全放在一起，每个模块，建立自己的内部类
 */
public class Constants {

    /**
     * 用户状态常量
     */
    public static class UserStatus {
        public static final int UNABLE = 0;
        public static final int ENABLE = 1;
    }

    /**
     * 微信小程序常量
     */
    public static class WxMiniApp {
        public static final String TOKEN_PREFIX = "wx:user:";
    }

}
