package run.hxtia.workbd.pojo.vo.request.page;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import run.hxtia.workbd.pojo.vo.request.page.base.KeywordPageReqVo;

@Data
@ApiModel("用户信息分页请求Vo")
@EqualsAndHashCode(callSuper = true)
public class AdminUserPageReqVo extends KeywordPageReqVo {

}
