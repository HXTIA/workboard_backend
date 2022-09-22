package run.hxtia.workbd.common.commoncontroller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import run.hxtia.workbd.common.filter.ErrorFilter;
import run.hxtia.workbd.common.util.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理Filter内部产生的异常
 * 将不是从 controller 抛出的异常，强行扔到这里的ErrorController
 */
@RestController
public class ErrorController {

    @PostMapping(Constants.Web.ERROR_URI)
    public void handle(HttpServletRequest request) throws Exception {
        throw (Exception) request.getAttribute(Constants.Web.ERROR_URI);
    }

}
