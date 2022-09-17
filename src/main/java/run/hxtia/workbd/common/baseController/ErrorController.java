package run.hxtia.workbd.common.baseController;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import run.hxtia.workbd.common.filter.ErrorFilter;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api("处理Filter内部产生的异常")
public class ErrorController {

    @GetMapping(ErrorFilter.ERROR_URI)
    public void handle(HttpServletRequest request) throws Exception {
        throw (Exception) request.getAttribute(ErrorFilter.ERROR_URI);
    }

}
