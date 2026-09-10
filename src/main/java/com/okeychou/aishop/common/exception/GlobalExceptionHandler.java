package com.okeychou.aishop.common.exception;

import com.okeychou.aishop.common.result.Result;
import com.okeychou.aishop.common.result.ResultCode;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 业务异常:自己抛的,预期内的错误
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusiness(final BusinessException e) {
        log.warn(e.getMessage());
        return Result.fail(e.getResultCode());
    }
    // 参数校验异常(SB4 新版方式,校验 @RequestParam 上的注解)
    @ExceptionHandler(HandlerMethodValidationException.class)
    public Result<Void> handleMethodValidation(HandlerMethodValidationException e){
        String msg=e.getAllErrors().isEmpty()?
                "参数错误":e.getAllErrors().get(0).getDefaultMessage();
        return Result.fail(ResultCode.PARAM_ERROR.getCode(), msg);

    }
    // 参数校验异常(老版 @Validated 方式,老教程常见)
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Void> handleConstraintViolation(ConstraintViolationException e) {
        String msg=e.getConstraintViolations().iterator().next().getMessage();
        return Result.fail(ResultCode.PARAM_ERROR.getCode(), msg);
    }

    // 资源不存在异常:用户访问了不存在的 URL,返回 404 而不是被兜底成 500
    @ExceptionHandler(NoResourceFoundException.class)
    public Result<Void> handleNoResource(NoResourceFoundException e) {
        String path = e.getResourcePath(); // Spring 把用户访问的 URL 告诉你
        log.warn("资源不存在: {}", path);          // warn级别:客户端问题,不是服务器故障
        return Result.fail(404, "资源不存在: " + path);
    }

    // 缺少必需参数:如 /api/products/category 没带 category
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<Void> handleMissingParam(MissingServletRequestParameterException e) {
        String msg = "缺少必需参数: " + e.getParameterName();
        log.warn(msg);                       // warn:客户端问题,不该刷 error 日志
        return Result.fail(ResultCode.PARAM_ERROR.getCode(), msg);
    }

    // 参数类型不对:如 ?pageNum=abc、?min=xyz
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<Void> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        String msg = "参数格式错误: " + e.getName() + " = " + e.getValue();
        log.warn(msg);
        return Result.fail(ResultCode.PARAM_ERROR.getCode(), msg);
    }

    // 请求方法不支持:如用 POST 打 GET 接口
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<Void> handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        String msg = "请求方法不支持: " + e.getMethod();
        log.warn(msg);
        return Result.fail(405, msg);
    }


    // 兜底:所有漏网异常,统一 500
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("系统异常",e);
        return Result.fail(ResultCode.SYSTEM_ERROR);

    }
}
