package com.litong.security.filter;

import com.litong.common.api.StatusCode;
import com.litong.common.exception.IllegalTokenException;
import com.litong.common.util.WebUtils;
import com.litong.security.entity.SelfUserEntity;
import com.litong.security.provider.TokenProvider;
import com.litong.security.service.SelfUserDetailsService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author savior
 * @version v1.0
 * @date 2020/6/30 8:32 下午
 * @Description JWT接口请求校验拦截器
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private SelfUserDetailsService selfUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 拿到token
        String token = tokenProvider.getTokenFromHeader(request);

        // 对于token为空的不需要去查Redis，直接放行
        if (StringUtils.isNotBlank(token)) {
            // 从token中拿到信息
            Claims claims = tokenProvider.getTokenClaims(token);
            String username = claims.getSubject();
            // String userId = claims.getId();
            String ip = claims.get("ip", String.class);
            String roleSetStr = claims.get("roles", String.class);
            Set<String> roleSet = new HashSet<>(Arrays.asList(roleSetStr));

            // 比对ip
            String ipAddress = WebUtils.getIpAddress(request);
            if (!StringUtils.equals(ip, ipAddress)) {
                log.info("请求[" + ipAddress + "]和令牌中的ip[" + ip + "]不一致");
                throw new IllegalTokenException(StatusCode.ILLEGAL_REQUEST,"请求和令牌ip不一致");
            }

            // 查redis
            List<String> tokenInfo = tokenProvider.getTokenInfo(token);
            if (tokenInfo != null) {
                // token有效，检查续期并放行
                tokenProvider.checkToken(token);
                SelfUserEntity selfUserEntity = selfUserDetailsService.loadUserByUsername(username);
                selfUserEntity.setAuthorities(roleSet.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(selfUserEntity, tokenInfo, selfUserEntity.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}
