package com.litong.security.handler;

import com.litong.bean.SysAuthEntity;
import com.litong.common.api.ResponseEntity;
import com.litong.security.config.JwtConfig;
import com.litong.security.entity.SelfUserEntity;
import com.litong.security.provider.TokenProvider;
import com.litong.service.SysUserService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * @author savior
 * @version v1.0
 * @date 2020/6/29 4:30 下午
 * @Description 登录成功处理类
 */
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 生成token
        SelfUserEntity selfUserEntity =  (SelfUserEntity) authentication.getPrincipal();
        String token = tokenProvider.createToken(selfUserEntity, request);

        // 查询权限
        String authListStr = sysUserService.selectSysAuthByUserId(selfUserEntity.getUserId())
                .stream().map(SysAuthEntity::getAuthPermission)
                .map(String::trim).collect(Collectors.joining(","));

        // 存入redis
        tokenProvider.saveToken(token,authListStr);
        // 响应
        response.setContentType("application/json;charset=utf-8");
        ResponseEntity body = ResponseEntity.ofSuccess(jwtConfig.getTokenPrefix() + " " +token);
        response.getWriter().write(JSON.toJSONString(body));
    }
}
