package com.litong.security.handler;

import com.litong.common.api.ResponseEntity;
import com.litong.common.api.StatusCode;
import com.litong.common.util.WebUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户未登录处理类
 *
 * @author savior
 * @version v1.0
 * @date 2020/6/29 3:34 下午
 * @Description
 */
@Slf4j
@Component
public class UserAuthEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        log.error("请求["+ WebUtils.getIpAddress(httpServletRequest) +"]未登录");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(ResponseEntity.ofFailure(StatusCode.ACCESS_DENIED,"未登录")));
    }
}
