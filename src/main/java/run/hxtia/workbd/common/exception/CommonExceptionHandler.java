package run.hxtia.workbd.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import run.hxtia.workbd.common.utils.JsonVos;
import run.hxtia.workbd.common.utils.Streams;
import run.hxtia.workbd.pojo.vo.result.JsonVo;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    // 拦截所有异常。
    @SuppressWarnings("ConstantConditions")
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public JsonVo handle(Throwable t) {
        // 先将日志打印了
        log.error("handle：", t);

        // 判断是什么异常。自定义对应的处理方案
        if (t instanceof CommonException) {
            return handle((CommonException) t);
        } else if (t instanceof BindException) {
            return handle((BindException) t);
        } else if (t instanceof ConstraintViolationException) {
            return handle((ConstraintViolationException) t);
        } else if (t instanceof MethodArgumentNotValidException) {
            return handle((MethodArgumentNotValidException) t);
        }
        // 其他想要处理的异常，继续 else if 拓展异常类即可

        // 递归处理异常
        Throwable cause = t.getCause();
        if (cause != null) return handle(cause);

        // 最终也处理不了的异常【直接返回 400】
        return JsonVos.error();
    }

    // 处理自定义异常
    private JsonVo handle(CommonException ce) {
        return JsonVos.error(ce.getCode(), ce.getMessage());
    }

    // 处理后端验证【BindException】异常
    private JsonVo handle(BindException be) {
        List<ObjectError> errors = be.getBindingResult().getAllErrors();
        /*
            1、利用Map将 List<ObjectError> -> List<String>
            2、ObjectError::getDefaultMessage：lambda的方法引用
         */
        List<String> defaultMsgs = Streams.map(errors, ObjectError::getDefaultMessage);
        String msg = StringUtils.collectionToDelimitedString(defaultMsgs, ", ");
        return JsonVos.error(msg);
    }

    // 处理后端验证【ConstraintViolationException】异常
    private JsonVo handle(ConstraintViolationException cve) {
        List<String> msgs = Streams.map(cve.getConstraintViolations(), ConstraintViolation::getMessage);
        String msg = StringUtils.collectionToDelimitedString(msgs, ", ");
        return JsonVos.error(msg);
    }

    // 处理后端验证【MethodArgumentNotValidException】异常
    private JsonVo handle(MethodArgumentNotValidException mae) {
        List<String> msgs = Streams.map(mae.getBindingResult().getAllErrors(), ObjectError::getDefaultMessage);
        String msg = StringUtils.collectionToDelimitedString(msgs, ", ");
        return JsonVos.error(msg);
    }

}
