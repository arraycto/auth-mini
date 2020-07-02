package com.litong.common.exception;

import com.litong.common.api.StatusCode;

/**
 * @author savior
 * @version v1.0
 * @date 2020/7/2 9:36 上午
 * @Description 无效或非法的令牌
 */
public class IllegalTokenException extends BaseBizException {

    public IllegalTokenException(StatusCode statusCode, String message) {
        super(statusCode,message);
    }
}
