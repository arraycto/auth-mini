package com.litong.controller.sys;

import com.litong.common.api.AnonymousAccess;
import com.litong.common.api.ResponseEntity;
import com.litong.service.SysAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author savior
 * @version v1.0
 * @date 2020/7/1 3:06 下午
 * @Description
 */
@Api(tags = "系统：登录鉴权")
@RestController
@RequestMapping("/sys/auth")
public class AuthenticationController {

    @Autowired
    private SysAuthService sysAuthService;

    @ApiOperation(value = "验证码")
    @AnonymousAccess
    @GetMapping("/code")
    public ResponseEntity getCode() {
        return sysAuthService.getCode();
    }


}
