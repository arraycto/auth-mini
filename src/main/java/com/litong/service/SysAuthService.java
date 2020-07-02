package com.litong.service;

import com.litong.common.api.ResponseEntity;

/**
 * @author savior
 * @version v1.0
 * @date 2020/7/1 3:15 下午
 * @Description 认证和授权自定义service
 */
public interface SysAuthService {

    ResponseEntity getCode();
}
