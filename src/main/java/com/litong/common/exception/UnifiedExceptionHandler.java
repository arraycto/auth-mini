package com.litong.common.exception;

import com.litong.common.api.ResponseEntity;
import com.litong.common.api.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

/**
 * @author savior
 * @version v1.0
 * @date 2020/6/29 9:01 上午
 * @Description 统一异常处理
 */
@Slf4j
@RestControllerAdvice
public class UnifiedExceptionHandler {

    /**
     * 处理数据库连接失败异常
     *
     * @param e 异常对象
     * @return 带有提示信息的失败返回
     */
    @ExceptionHandler(CannotCreateTransactionException.class)
    public ResponseEntity handleCannotCreateTransactionException(CannotCreateTransactionException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.ofFailure("数据库连接失败");
    }

    /**
     * 处理请求参数校验失败异常
     *
     * @param e 抛出的参数校验失败异常对象
     * @return 带有校验失败原因提示信息的失败返回
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errMessage = Optional.ofNullable(e.getBindingResult().getFieldError())
                .map(FieldError::getDefaultMessage)
                .orElse(StatusCode.SIGNATURE_VERIFICATION_FAILED.getMessage());
        log.error(e.getMessage());
        return ResponseEntity.ofFailure(StatusCode.SIGNATURE_VERIFICATION_FAILED, errMessage);
    }

    /**
     * 处理请求体为空或请求参数包含无效参数的异常
     *
     * @param e 异常对象
     * @return 带有请求体无效错误的失败返回
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity.ofFailure(StatusCode.INVALID_REQUEST_PARAM);
    }

    /**
     * 处理拒绝访问异常
     *
     * @param e 异常对象
     * @return 带有请求体无效错误的失败返回
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.ofFailure(StatusCode.ACCESS_DENIED);
    }

    /**
     * 处理业务异常
     *
     * @param e 自定义异常对象
     * @return 业务异常所对应的返回
     */
    @ExceptionHandler(BaseBizException.class)
    public ResponseEntity handleBizExceptions(BaseBizException e) {
        if (e.getStatusCode() != null) {
            StatusCode code = e.getStatusCode();
            log.error(code.getMessage());
            return ResponseEntity.ofFailure(code);
        } else if (StringUtils.isNotBlank(e.getMessage())) {
            log.error(e.getMessage());
            return ResponseEntity.ofFailure(e.getMessage());
        } else {
            log.error(e.getMessage());
            return ResponseEntity.ofFailure();
        }
    }

    /**
     * 处理未知的运行时错误
     *
     * @return 默认的失败返回
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleUnknownRuntimeExceptions(RuntimeException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.ofFailure(e.getMessage());
    }
}
