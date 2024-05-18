package run.hxtia.workbd.pojo.vo.common.response.result;


/**
 * 常用状态码和描述信息（可追加）
 */
public enum CodeMsg {

    OPERATE_OK(0, "操作成功"),
    SAVE_OK(0, "保存成功"),
    REMOVE_OK(0, "删除成功"),
    SUB_MSG_OK(0, "成功订阅消息"),
    REGISTER_OK(0, "注册成功"),
    UPDATE_PWD_OK(0, "密码修改成功，请重新登录"),

    BAD_REQUEST(400, "请求出错"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),

    OPERATE_ERROR(40001, "操作失败"),
    SAVE_ERROR(40002, "保存失败"),
    REMOVE_ERROR(40003, "删除失败"),
    UPLOAD_IMG_ERROR(40004, "图片上传失败"),
    USER_INFO_ERROR(40005, "用户信息校验失败"),
    SUB_MSG_ERROR(40006, "消息订阅失败"),
    AUTHORIZED_ERROR(40007, "微信授权失败"),
    REGISTER_ERROR(40008, "注册失败"),
    ADD_ROLE_ERROR(40009, "添加角色失败"),
    CODE_SEND_ERROR(50010, "验证码发送失败，请稍后再试哟~"),
    UPDATE_PWD_ERROR(50011, "密码修改失败"),

    WRONG_USERNAME_NOT_EXIST(50001, "用户不存在"),
    WRONG_PASSWORD(50002, "密码错误"),
    USER_LOCKED(50003, "用户被锁定，无法正常登录"),
    WRONG_CAPTCHA(50004, "验证码错误"),
    EXIST_USERS(50005, "用户已存在"),
    PERFECT_ORG_INFO(50006, "请完善组织信息"),
    WRONG_OLD_PASSWORD(50007, "旧密码错误"),
    WRONG_NEW_PASSWORD_REPEAT(50008, "新密码与旧密码重复"),
    WRONG_WORK_NO_REMOVE(50009, "该作业不能被删除"),
    WRONG_CODE_EXIST(50010, "验证码已发送，请稍后再试哟~"),
    NO_USER_AT_PRESENT(50011,"暂无用户,请添加"),

    GET_TOKEN_ERR(60000, "Token 获取失败"),
    NO_TOKEN(60001, "没有Token，请登录"),
    TOKEN_EXPIRED(60002, "Token过期，请重新登录"),
    NO_APP_ID(60003, "未找到对应的appId"),
    NO_ORG_INFO(60004, "未找到组织信息！！！"),
    NO_PERMISSION(60005, "没有相关的操作权限"),
    CHECK_OK(60005,"校验通过"),
    CHECK_ERROR(60006,"校验失败"),
    CHECK_TOKEN_ERR(60007, "Token Check 失效"),
    GET_ACCESS_TOKEN_ERR(60008, "获取 ACCESS Token 失败"),


    AUTH_CODE_USED(60009, "授权码 code 已被使用"),
    AUTH_CODE_NOT_EXIT(600010, "授权码 code 不存在");



    /**
     *     状态码
     */
    private final int code;
    /**
     * 描述信息
     */
    private final String msg;

    CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}
