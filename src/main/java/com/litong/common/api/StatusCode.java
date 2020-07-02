package com.litong.common.api;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author savior
 * @version v1.0
 * @date 2020/6/28 8:22 下午
 * @Description 统一响应码/响应信息
 * <p>
 * 状态代码自定义规则
 * 200 成功
 * 4xx 客户端异常
 * 5xx 服务器异常
 */
@Getter
public enum StatusCode {

    /**
     * 成功
     */
    OK("200", "成功"),

    /**
     * 服务端出现未知错误
     */
    FAIL("500", "服务端错误"),

    /**
     * 资源未找到
     */
    RESOURCE_NOT_FOUND("404", "资源未找到"),

    /**
     * 用户未找到
     */
    USER_NOT_FOUND("414", "用户未找到"),

    /**
     * 用户被禁用
     */
    USER_DISABLED("405", "用户被禁用"),

    /**
     * 用户名或密码错误
     */
    LOGIN_FAILED("401", "用户名或密码错误"),

    /**
     * 验证码错误或过期
     */
    VERIFICATION_CODE_ERROR("411", "验证码错误/过期"),

    /**
     * 拒绝访问（权限不足、token过期）
     */
    ACCESS_DENIED("407", "拒绝访问"),

    /**
     * 请求参数中包含无效参数或请求体为空
     */
    INVALID_REQUEST_PARAM("409", "请求参数中包含无效参数或请求体为空"),

    /**
     * 非法的请求
     */
    ILLEGAL_REQUEST("400","非法的请求"),

    /**
     * 服务端校验失败
     */
    SIGNATURE_VERIFICATION_FAILED("412", "参数校验失败");


    private String code;
    private String message;

    StatusCode(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据状态码获取状态信息
     *
     * @param code
     * @return
     */
    public static String getMessageByCode(String code) {
        for (StatusCode item : values()) {
            if (StringUtils.equals(item.code, code)) {
                return item.message;
            }
        }
        return null;
    }
}
