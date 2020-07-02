package com.litong.controller.test;

import com.litong.common.api.AnonymousAccess;
import com.litong.common.api.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author savior
 * @version v1.0
 * @date 2020/7/1 10:59 上午
 * @Description 测试权限控制API
 */
@Api(tags = "测试：权限控制")
@RequestMapping("/test/auth")
@RestController
public class TestAuthController {

    /**
     * 测试匿名不需要登录的API
     */
    @AnonymousAccess
    @ApiOperation(value = "允许匿名访问的API")
    @GetMapping("/anonymous")
    public ResponseEntity testAnonymous() {
        return ResponseEntity.ofSuccess("匿名API测试成功");
    }

    /**
     * 测试仅角色控制的API
     *
     * 拥有管理员ADMIN角色的用户可以进行用户管理
     */
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "测试ADMIN角色的接口")
    @GetMapping("/user")
    public ResponseEntity testRoleControl() {
        return ResponseEntity.ofSuccess("我的角色是ADMIN，测试成功！");
    }

    /**
     * 测试仅权限控制的API
     * 拥有'sys:user:mng'权限的用户可以进行用户管理
     */
    @PreAuthorize("hasPermission('/test/auth/userMng','sys:user:mng')")
    @ApiOperation(value = "测试拥有[sys:user:mng]权限的接口")
    @GetMapping("/userMng")
    public ResponseEntity testAuthControlMng() {
        return ResponseEntity.ofSuccess("我拥有[sys:user:mng]权限，测试成功！");
    }

    /**
     * 测试仅权限控制的API
     * 拥有'sys:user:self'权限的用户均可进行本人账户管理
     */
    @PreAuthorize("hasPermission('/test/auth/userSelf','sys:user:self')")
    @ApiOperation(value = "测试拥有[sys:user:self]权限的接口")
    @GetMapping("/userSelf")
    public ResponseEntity testAuthControlSelf() {
        return ResponseEntity.ofSuccess("我拥有[sys:user:self]权限，测试成功！");
    }

    /**
     * 测试角色 或 权限控制的API
     * 拥有ADMIN角色或拥有sys:user:mng权限的用户可以访问
     */
    @PreAuthorize("hasRole('ADMIN') or hasPermission('/test/auth/userMngSpecial','sys:user:mng')")
    @ApiOperation(value = "测试ADMIN角色或拥有[sys:user:mng]权限的接口")
    @GetMapping("/userMngSpecial")
    public ResponseEntity testAuthRoleControl() {
        return ResponseEntity.ofSuccess("我的角色是ADMIN，或者拥有[sys:user:mng]权限，测试成功！");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "测试ADMIN角色的用户管理功能")
    @GetMapping("/testResponseFormat")
    public ResponseEntity testCustomResponseBody() {
        throw new NullPointerException();
    }

}
