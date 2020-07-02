package com.litong.security.handler;

import com.litong.common.api.ResponseEntity;
import com.litong.common.api.StatusCode;
import com.litong.common.util.WebUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理类
 *
 * @author savior
 * @version v1.0
 * @date 2020/6/29 3:37 下午
 * @Description
 */
@Slf4j
@Component
public class UserLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {

        httpServletResponse.setContentType("application/json;charset=utf-8");

        log.info("用户[" + WebUtils.getIpAddress(httpServletRequest) + "]登录失败：" + e.getMessage());

        ResponseEntity body = ResponseEntity.ofFailure(StatusCode.FAIL,"登录失败");

        // 用户不存在
        if (e instanceof UsernameNotFoundException) {
            body = ResponseEntity.ofFailure(StatusCode.USER_NOT_FOUND);
        }

        // 账户禁用
        if (e instanceof LockedException) {
            body = ResponseEntity.ofFailure(StatusCode.USER_DISABLED);
        }

        // 用户名或密码错误
        if (e instanceof BadCredentialsException) {
            body = ResponseEntity.ofFailure(StatusCode.LOGIN_FAILED);
        }

        // 验证码错误或过期
        if (e instanceof NonceExpiredException) {
            body = ResponseEntity.ofFailure(StatusCode.VERIFICATION_CODE_ERROR);
        }

        // 响应
        httpServletResponse.getWriter().write(JSON.toJSONString(body));
    }
}
