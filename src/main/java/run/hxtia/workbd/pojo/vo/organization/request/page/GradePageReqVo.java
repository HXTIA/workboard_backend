package run.hxtia.workbd.pojo.vo.organization.request.page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import run.hxtia.workbd.pojo.vo.common.request.page.KeywordPageReqVo;
import run.hxtia.workbd.pojo.vo.common.request.page.PageReqVo;
/**
 * @author Xiaojin
 * @date 2024/5/6
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GradePageReqVo extends KeywordPageReqVo {

    @ApiModelProperty("学院ID")
    private Integer collegeId;

}

