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
    }

    /**
     * 权限常量
     */
    public static class Permission {
        // 组织管理
        public static final String SYS_ORGANIZATION_READ = "sysOrganization:read";
        public static final String SYS_ORGANIZATION_UPDATE = "sysOrganization:update";
        // B端用户管理
        public static final String SYS_ADMIN_USER_READ = "sysAdminUser:read";
        public static final String SYS_ADMIN_USER_UPDATE = "sysAdminUser:update";
        public static final String SYS_ADMIN_USER_DELETE = "sysAdminUser:delete";
        public static final String SYS_ADMIN_USER_CREATE = "sysAdminUser:create";
        public static final String SYS_ADMIN_USER_FORGOT = "sysAdminUser:forgot";
        // C端用户管理
        public static final String SYS_USER_READ = "sysUser:read";
        public static final String SYS_USER_UPDATE = "sysUser:update";
        public static final String SYS_USER_DELETE = "sysUser:delete";
        // 角色管理
        public static final String SYS_ROLE_READ = "sysRole:read";
        public static final String SYS_ROLE_UPDATE = "sysRole:update";
        public static final String SYS_ROLE_DELETE = "sysRole:delete";
        public static final String SYS_ROLE_CREATE = "sysRole:create";
        // 行政班级管理
        public static final String CLS_EXECUTIVE_READ = "clsExecutive:read";
        public static final String CLS_EXECUTIVE_UPDATE = "clsExecutive:update";
        public static final String CLS_EXECUTIVE_DELETE = "clsExecutive:delete";
        public static final String CLS_EXECUTIVE_CREATE = "clsExecutive:create";
        // 教学班级管理
        public static final String CLS_EDUCATION_READ = "clsEducation:read";
        public static final String CLS_EDUCATION_UPDATE = "clsEducation:update";
        public static final String CLS_EDUCATION_DELETE = "clsEducation:delete";
        public static final String CLS_EDUCATION_CREATE = "clsEducation:create";
        // 作业管理
        public static final String WORK_MANAGE_READ = "workManage:read";
        public static final String WORK_MANAGE_UPDATE = "workManage:update";
        public static final String WORK_MANAGE_DELETE = "workManage:delete";
        public static final String WORK_MANAGE_CREATE = "workManage:create";

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

}
