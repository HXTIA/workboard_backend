package run.hxtia.workbd.common.exception;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import run.hxtia.workbd.common.util.JsonVos;
import run.hxtia.workbd.common.util.Streams;
import run.hxtia.workbd.pojo.vo.result.JsonVo;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Objects;

/**
 * 统一拦截并且处理异常【这里的异常是来自必须是来自Controller|自定义异常】
 */
@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    /**
     * 拦截所有异常
     */
    @SuppressWarnings("ConstantConditions")
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public JsonVo handle(Throwable t) {
        // 先将日志打印了
        log.error("Handle：======== 自定义异常", t);

        // 判断是什么异常。自定义对应的处理方案
        if (t instanceof CommonException) {
            return handle((CommonException) t);
        } else if (t instanceof BindException) {
            return handle((BindException) t);
        } else if (t instanceof ConstraintViolationException) {
            return handle((ConstraintViolationException) t);
        } else if (t instanceof MethodArgumentNotValidException) {
            return handle((MethodArgumentNotValidException) t);
        } else if (t instanceof WxErrorException) {
            return handle((WxErrorException) t);
        }
        // 其他想要处理的异常，继续 else if 拓展异常类即可

        // 递归处理异常
        Throwable cause = t.getCause();
        if (cause != null) return handle(cause);

        // 最终也处理不了的异常【直接返回 400】
        return JsonVos.error();
    }

    /**
     * 处理自定义【CommonException】异常
     */
    private JsonVo handle(CommonException ce) {
        return JsonVos.error(ce.getCode(), ce.getMessage());
    }

    /**
     * 处理后端验证【BindException】异常
     */
    private JsonVo handle(BindException be) {
        return JsonVos.error(getMsg(be.getBindingResult().getAllErrors()));
    }

    /**
     * 处理后端验证【ConstraintViolationException】异常
     */
    private JsonVo handle(ConstraintViolationException cve) {
        List<String> msgs = Streams.map(cve.getConstraintViolations(), ConstraintViolation::getMessage);
        String msg = StringUtils.collectionToDelimitedString(msgs, ", ");
        return JsonVos.error(msg);
    }

    /**
     * 处理后端验证【MethodArgumentNotValidException】异常
     */
    private JsonVo handle(MethodArgumentNotValidException mae) {
        return JsonVos.error(getMsg(mae.getBindingResult().getAllErrors()));
    }

    /**
     * 处理后端验证【MethodArgumentNotValidException】异常
     */
    private JsonVo handle(WxErrorException mae) {
        WxError error = mae.getError();
        return JsonVos.error(error.getErrorCode(), error.getErrorMsg());
    }

    /**
     * 获取错误的消息
     * @param errors ：所有的错误类型
     * @return ：错误消息
     */
    private String getMsg(List<ObjectError> errors) {
        /*
            1、利用Map将 List<ObjectError> -> List<String>
            2、ObjectError::getDefaultMessage：lambda的方法引用
         */
        List<String> defaultMsgs = Streams.map(errors, (err) ->
            Objects.requireNonNull(err.getCodes())[1] + ":" + err.getDefaultMessage());
        return  StringUtils.collectionToDelimitedString(defaultMsgs, ", ");
    }

}
