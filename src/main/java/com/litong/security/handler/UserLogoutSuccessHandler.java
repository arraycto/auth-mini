package com.litong.security.handler;

import com.litong.common.api.ResponseEntity;
import com.litong.security.provider.TokenProvider;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author savior
 * @version v1.0
 * @date 2020/6/29 4:34 下午
 * @Description 注销成功处理
 */
@Component
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private TokenProvider tokenProvider;


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 从请求头中拿到token(截取后)
        String token = tokenProvider.getTokenFromHeader(request);
        // 清除token
        if (StringUtils.isNotBlank(token)) {
            tokenProvider.deleteToken(token);
        }
        // 响应
        response.setContentType("application/json;charset=utf-8");
        ResponseEntity body = ResponseEntity.ofSuccess();
        response.getWriter().write(JSON.toJSONString(body));
    }
}
