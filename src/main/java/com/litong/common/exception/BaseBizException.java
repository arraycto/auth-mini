package com.litong.common.exception;

import com.litong.common.api.StatusCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author savior
 * @version v1.0
 * @date 2020/6/29 8:55 上午
 * @Description 业务异常基类
 */
@Getter
public abstract class BaseBizException extends RuntimeException {

    protected StatusCode statusCode = null;

    public BaseBizException(String message) {
        super(message);
    }

    public BaseBizException(StatusCode statusCode) {
        super(statusCode.getMessage());
        this.statusCode = statusCode;
    }

    public BaseBizException(StatusCode statusCode,String message) {
        super(StringUtils.isNotBlank(message) ? message : statusCode.getMessage());
        this.statusCode = statusCode;
    }


    /**
     * 业务异常不记录stackTrace
     * @return
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
