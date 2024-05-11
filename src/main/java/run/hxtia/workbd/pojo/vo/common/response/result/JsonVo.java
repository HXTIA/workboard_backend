package run.hxtia.workbd.pojo.vo.common.response.result;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 返回结果，若没有返回的数据。就返回这个（使用工具类里的方法）
 *
 */

@Data
@Api("返回结果")
public class JsonVo {
    private static final int CODE_OK = CodeMsg.OPERATE_OK.getCode();
    private static final int CODE_ERROR = CodeMsg.BAD_REQUEST.getCode();
    @ApiModelProperty("代码【0代表成功，其他代表失败】")
    private Integer code;
    @ApiModelProperty("消息描述")
    private String msg;


    // 返回成功的状态码 0
    public JsonVo() {
        this(true);
    }

    // 返回成功或者失败的状态码
    public JsonVo(boolean ok) {
        this(ok, null);
    }

    // 状态码 + 描述信息
    public JsonVo(boolean ok, String msg) {
        this(ok ? CODE_OK : CODE_ERROR, msg);
    }

    // 状态码 + 描述信息
    public JsonVo(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // 返回枚举里的 状态码 + 信息
    public JsonVo(CodeMsg codeMsg) {
        this(codeMsg.getCode(), codeMsg.getMsg());
    }



}
