package com.litong.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author savior
 * @version v1.0
 * @date 2020/6/30 5:16 下午
 * @Description Rsa加密解密配置类
 *
 */
@Data
@Component
@ConfigurationProperties(prefix = "rsa")
public class RsaConfig {
    /**
     * 私钥
     */
    private String privateKey;
}
