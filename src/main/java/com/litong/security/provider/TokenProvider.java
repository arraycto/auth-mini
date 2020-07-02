package com.litong.security.provider;

import com.litong.common.util.RedisUtils;
import com.litong.common.util.WebUtils;
import com.litong.security.config.JwtConfig;
import com.litong.security.entity.SelfUserEntity;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author savior
 * @version v1.0
 * @date 2020/6/30 9:17 上午
 * @Description
 */
@Component
@RequiredArgsConstructor
public class TokenProvider implements InitializingBean {

    /**
     * jwt配置类（构造器注入）
     */
    private final JwtConfig jwtConfig;

    /**
     * redis工具类（构造器注入）
     */
    private final RedisUtils redisUtils;

    /**
     * 密钥 afterPropertiesSet 生成
     */
    private Key key;

    @Override
    public void afterPropertiesSet() {
        byte[] decode = Decoders.BASE64.decode(jwtConfig.getBase64Secret());
        this.key = Keys.hmacShaKeyFor(decode);
    }

    /**
     * 生成token
     *
     * @param selfUserEntity
     * @param request
     * @return
     */
    public String createToken(SelfUserEntity selfUserEntity, HttpServletRequest request) {

        String ip = WebUtils.getIpAddress(request);
        String roles = selfUserEntity.getRoles()
                .stream().map(String::trim)
                .collect(Collectors.joining(","));

        String token = Jwts.builder()
                .setId(selfUserEntity.getUserId() + "")
                .setSubject(selfUserEntity.getUsername())
                .claim("ip", ip)
                .claim("roles", roles)
                // 使用redis因此不设置失效时间
                // .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        return token;
    }

    /**
     * 解析token
     * 获取自定义Claims
     */
    public Claims getTokenClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 将token存入redis
     * key: token(不带前缀，约定只有在前后端互传时才带前缀)
     * value: 该用户所拥有的权限列表authList字符串形式
     * time: 过期时间
     */
    public void saveToken(String token, String authListStr) {
        redisUtils.set(token, authListStr, jwtConfig.getExpiration() / 1000);
    }

    /**
     * 检查token是否需要续期
     */
    public void checkToken(String token) {
        // 判断是否续期token,计算token的过期时间
        long time = redisUtils.getExpire(token) * 1000;
        Date expireDate = DateUtil.offset(new Date(), DateField.MILLISECOND, (int) time);
        // 判断当前时间与过期时间的时间差
        long differ = expireDate.getTime() - System.currentTimeMillis();
        // 如果在续期检查的范围内，则续期
        if (differ <= jwtConfig.getDetect()) {
            long renew = time + jwtConfig.getRenew();
            redisUtils.expire(token, renew / 1000);
        }
    }

    /**
     * 清除token
     */
    public void deleteToken(String token) {
        redisUtils.del(token);
    }

    /**
     * 从redis中获取token信息（权限列表）
     *
     * @param token
     * @return
     */
    public List<String> getTokenInfo(String token) {

        List<String> tokenInfo = null;

        // 如果传入token非空且redis中有该token的信息
        if (StringUtils.isNotBlank(token) && redisUtils.hasKey(token)) {
            tokenInfo = Arrays.asList(redisUtils.get(token).toString().split(","));
        }

        return tokenInfo;
    }

    /**
     * 从请求头中获取token字符串（截取前缀）
     *
     * @param request
     * @return
     */
    public String getTokenFromHeader(HttpServletRequest request) {
        final String requestHeader = request.getHeader(jwtConfig.getTokenHeader());
        if (requestHeader != null && requestHeader.startsWith(jwtConfig.getTokenPrefix())) {
            return requestHeader.substring(7);
        }
        return null;
    }

}
