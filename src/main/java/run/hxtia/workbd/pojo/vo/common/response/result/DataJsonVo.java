package run.hxtia.workbd.pojo.vo.common.response.result;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *  有数据返回，说明肯定成功了。那么返回 数据 + 描述信息
 * @param <T> ：返回的数据
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class DataJsonVo<T> extends JsonVo {

    // 返回的数据
    private T data;

    public DataJsonVo() {}

    // 返回 成功状态码 0 + 描述信息 + 数据
    public DataJsonVo(String msg, T data) {
        super(true, msg);
        this.data = data;
    }

    // 返回 成功状态码 0 + 数据
    public DataJsonVo(T data) {
        this(null, data);
    }

}
