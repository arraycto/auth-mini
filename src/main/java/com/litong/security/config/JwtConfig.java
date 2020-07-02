package com.litong.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author savior
 * @version v1.0
 * @date 2020/6/29 4:55 下午
 * @Description JWT配置类
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    /**
     * 密匙KEY
     */
    private String secret;

    /**
     *
     * 必须使用最少88位的Base64对该令牌进行编码
     */
    private String base64Secret;


    /**
     * HeaderKEY
     */
    private String tokenHeader;

    /**
     * 令牌前缀
     */
    private String tokenPrefix;

    /**
     * 过期时间 单位毫秒
     */
    private Integer expiration;

    /**
     * 配置不需要认证的接口

    private List<String> antMatchers;
     */

    /**
     * token 续期检查时间 单位毫秒
     */
    private Integer detect;

    /**
     * 续期时间 单位毫秒
     */
    private Integer renew;
}
