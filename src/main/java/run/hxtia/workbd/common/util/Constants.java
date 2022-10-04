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
        public static final String USER_ID = "user:id:";
        // 超管默认角色
        public static final String DEFAULT_ROLE = "超级管理员";
    }

    /**
     * 微信小程序常量
     */
    public static class WxMiniApp {
        public static final String WX_USER = "wx:user:";
        // 微信接口前缀
        public static final String WX_PREFIX = "/wx/";
    }

    /**
     * Web 应用常量
     */
    public static class Web {
        public static final String HEADER_TOKEN = "Token";
        public static final String ERROR_URI = "/handleError";
        public static final String ADMIN_PREFIX = "/admin/";
    }

    /**
     * 时间常量
     */
    public static class Date {
        // 过期时间
        public static final int EXPIRE_DATS = 7;
    }

    public static class Status {
        // 用户禁用状态
        public static final int UNABLE = 0;
        public static final int ENABLE = 1;
        // 目录类型
        public static final Short DIR_TYPE = 1;
        // 作业是否展示
        public static final Short WORK_ENABLE = 1;
        public static final Short WORK_DISABLE = 0;
        // 对标索引状态
        public static final short MATCH_INDEX_NO_EDIT = 0;
        public static final short MATCH_INDEX_EDIT = 1;
    }

}
