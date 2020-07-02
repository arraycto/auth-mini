package com.litong.service.impl;

import com.litong.common.api.ResponseEntity;
import com.litong.common.util.RedisUtils;
import com.litong.service.SysAuthService;
import cn.hutool.core.util.IdUtil;
import com.wf.captcha.ArithmeticCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author savior
 * @version v1.0
 * @date 2020/7/1 3:20 下午
 * @Description
 */
@Slf4j
@Service
public class SysAuthServaiceImpl implements SysAuthService {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 验证码过期时间
     */
    @Value("${loginCode.expiration}")
    private Long codeExpiration;

    /**
     * 生成验证码
     * @return
     */
    @Override
    public ResponseEntity getCode() {
        // 算术类型 https://gitee.com/whvse/EasyCaptcha
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36);
        // 几位数运算，默认是两位
        captcha.setLen(2);
        // 获取运算的结果
        String result = captcha.text();
        String uuid = IdUtil.simpleUUID();
        log.info("生成的验证码结果：" + result);
        // 放入缓存
        redisUtils.set(uuid,result,codeExpiration);
        // 验证码信息
        Map<String,Object> imgResult = new HashMap<String,Object>(2){{
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        log.info("放入缓存的验证码结果：" + redisUtils.get(uuid));
        return ResponseEntity.ofSuccess(imgResult);
    }
}
