package com.litong.common.api;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

/**
 * @author savior
 * @version v1.0
 * @date 2020/6/29 8:24 上午
 * @Description 自定义响应实体
 */
@Data
public class ResponseEntity {

    /**
     * 响应码
     */
    private String code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 携带数据
     */
    private Object data;

    /**
     * 私有构造器
     */
    private ResponseEntity(final String code, final String message, final Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功
     * @param content   携带数据
     * @param <T>       携带的数据类型
     * @return          响应实体
     */
    public static <T> ResponseEntity ofSuccess(final T content) {
        return new ResponseEntity(
                StatusCode.OK.getCode(),
                StatusCode.OK.getMessage(),
                JSONArray.toJSON(content)
        );
    }

    /**
     * 成功不携带数据
     * @return 响应实体
     */
    public static ResponseEntity ofSuccess() {
        return ofSuccess(null);
    }

    /**
     * 失败，未知的错误
     * @return 响应实体
     */
    public static ResponseEntity ofFailure() {
        return new ResponseEntity(
                StatusCode.FAIL.getCode(),
                StatusCode.FAIL.getMessage(),
                null
        );
    }

    /**
     * 失败，未知的错误
     * @param errMessage 错误信息
     * @return 响应实体
     */
    public static ResponseEntity ofFailure(String errMessage) {
        return new ResponseEntity(
                StatusCode.FAIL.getCode(),
                errMessage,
                null
        );
    }

    /**
     * 失败，指定错误码枚举
     * @param statusCode 错误枚举
     * @return 响应实体
     */
    public static ResponseEntity ofFailure(StatusCode statusCode) {
        return new ResponseEntity(
                statusCode.getCode(),
                statusCode.getMessage(),
                null
        );
    }

    /**
     * 失败，指定错误码枚举并自定义错误信息
     * @param statusCode 错误枚举
     * @param errMessage 自定义错误信息
     * @return 响应实体
     */
    public static ResponseEntity ofFailure(StatusCode statusCode, String errMessage) {
        return new ResponseEntity(
                statusCode.getCode(),
                errMessage,
                null
        );
    }

}
