package com.litong.security.service;

import com.litong.bean.SysUserEntity;
import com.litong.security.entity.SelfUserEntity;
import com.litong.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author savior
 * @version v1.0
 * @date 2020/6/30 4:00 下午
 * @Description SpringSecurity用户的业务实现
 */
@Component
public class SelfUserDetailsService implements UserDetailsService {

    @Autowired
    SysUserService userService;

    @Override
    public SelfUserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户
        SysUserEntity sysUserEntity = userService.selectUserByName(username);
        if (Objects.isNull(sysUserEntity)) {
            return null;
        }

        // 构造SelfUserEntity，authorities暂不赋值
        SelfUserEntity selfUserEntity = new SelfUserEntity();
        selfUserEntity.setUserEntity(sysUserEntity);

        // 返回
        return selfUserEntity;
    }
}
