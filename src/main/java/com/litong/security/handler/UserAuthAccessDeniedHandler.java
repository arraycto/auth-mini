package com.litong.security.handler;

import com.litong.common.api.ResponseEntity;
import com.litong.common.api.StatusCode;
import com.litong.common.util.WebUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author savior
 * @version v1.0
 * @date 2020/6/29 3:23 下午
 * @Description 无权限处理
 */
@Slf4j
@Component
public class UserAuthAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 无权限处理
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException {
        log.error("请求["+ WebUtils.getIpAddress(httpServletRequest) +"]无权访问资源："+ httpServletRequest.getRequestURI());
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(ResponseEntity.ofFailure(StatusCode.ACCESS_DENIED,"权限不足")));
    }
}
