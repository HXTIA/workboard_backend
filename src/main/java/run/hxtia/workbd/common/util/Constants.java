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
        public static final String WX_AT_PREFIX = "wx:accesstoken:";

        public static String WX_TOKEN = "WXToken";
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
        public static final int WX_STUDENT_EXPIRE_DATS = 1;
    }

    /**
     * 状态常量
     */
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
        // 学生作业完成情况
        public static final Short WORK_UNREAD = 0; // 未读
        // 作业是否展示
        public static final Short WORK_UNDONE = 1; // 已读未完成
        public static final Short WORK_DONE = 2; // 已完成

        public static final String PUBLISH_PLAT_WX = "WX"; // 发布平台-微信
        public static final String PUBLISH_PLAT_WEB = "WEB"; // 发布平台- Web

        public static final Short Code_EXIT = 0; // 不存在
        public static final Short Code_UNUSE = 1; // 未使用
        public static final Short Code_USED = 2; // 已使用
        public static final Short Code_REVOKE = 3; // 已吊销
    }

    /**
     * 权限常量
     */
    public static class Permission {

        public static final String ORG_DATA_READ = "orgData:read";
        public static final String USER_DATA_READ = "userData:read";

        public static final String HOMEWORK_READ = "homework:read";
        public static final String HOMEWORK_CREATE = "homework:create";
        public static final String HOMEWORK_UPDATE = "homework:update";
        public static final String HOMEWORK_DELETE = "homework:delete";

        public static final String NOTICE_READ = "notice:read";
        public static final String NOTICE_CREATE = "notice:create";
        public static final String NOTICE_UPDATE = "notice:update";
        public static final String NOTICE_DELETE = "notice:delete";

        public static final String COLLEGE_READ = "college:read";
        public static final String COLLEGE_CREATE = "college:create";
        public static final String COLLEGE_UPDATE = "college:update";
        public static final String COLLEGE_DELETE = "college:delete";

        public static final String GRADE_READ = "grade:read";
        public static final String GRADE_CREATE = "grade:create";
        public static final String GRADE_UPDATE = "grade:update";
        public static final String GRADE_DELETE = "grade:delete";

        public static final String CLASS_READ = "class:read";
        public static final String CLASS_CREATE = "class:create";
        public static final String CLASS_UPDATE = "class:update";
        public static final String CLASS_DELETE = "class:delete";

        public static final String COURSE_READ = "course:read";
        public static final String COURSE_CREATE = "course:create";
        public static final String COURSE_UPDATE = "course:update";
        public static final String COURSE_DELETE = "course:delete";

        public static final String ROLE_READ = "role:read";
        public static final String ROLE_CREATE = "role:create";
        public static final String ROLE_UPDATE = "role:update";
        public static final String ROLE_DELETE = "role:delete";

        public static final String AUTHORIZE_READ = "authorize:read";
        public static final String AUTHORIZE_CREATE = "authorize:create";
        public static final String AUTHORIZE_UPDATE = "authorize:update";
        public static final String AUTHORIZE_DELETE = "authorize:delete";

        public static final String ADMIN_READ = "admin:read";
        public static final String ADMIN_CREATE = "admin:create";
        public static final String ADMIN_UPDATE = "admin:update";
        public static final String ADMIN_DELETE = "admin:delete";
        public static final String ADMIN_FORGOT = "admin:forget";

        public static final String STUDENT_READ = "student:read";
        public static final String STUDENT_CREATE = "student:create";
        public static final String STUDENT_UPDATE = "student:update";
        public static final String STUDENT_DELETE = "student:delete";

    }

    /**
     * 邮件
     */
    public static class VerificationCode {
        // 邮件验证码前缀
        public static final String EMAIL_CODE_PREFIX = "Email:";
        // 图形验证码前缀
        public static final String IMAGE_CODE_PREFIX = "Captcha:";
    }

    /**
     * HTTP
     */
    public static class HTTPClient {
        public static int HTTP_SUCCESS = 200;
        public static final String CONTENT_TYPE = "content-type";
        public static final String APPLICATION_JSON = "application/json";
        public static final String CHARSET_UFS8 = "UTF-8";
    }

    public static class WxApp {
        public static String PREFIX = "https://api.weixin.qq.com/";
        public static String GET_TOKEN = "sns/jscode2session";
        public static String CHECK_TOKEN = "wxa/checksession";
        public static String WX_ACCESS_TOKEN = "cgi-bin/token";

    }

    public static class Auth {
        public static final String AUTH_CODE = "/auth/code/";
        public static final String AUTH_Code_USER = "auth:code:user:";
    }

}
