package com.litong.security.provider;

import com.litong.bean.SysRoleEntity;
import com.litong.common.util.RedisUtils;
import com.litong.common.util.RsaUtils;
import com.litong.security.config.RsaConfig;
import com.litong.security.entity.SelfUserEntity;
import com.litong.security.service.SelfUserDetailsService;
import com.litong.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author savior
 * @version v1.0
 * @date 2020/6/30 3:50 下午
 * @Description 自定义登录验证
 */
@Slf4j
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private SelfUserDetailsService selfUserDetailsService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RsaConfig rsaConfig;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取表单输入中返回的用户名
        String userName = (String) authentication.getPrincipal();
        // 获取表单中输入的密码
        String password = (String) authentication.getCredentials();
        // 获取当前请求
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest();
        // 获取登录表单中的验证码
        String code = request.getParameter("code");
        String uuid = request.getParameter("uuid");

        SelfUserEntity selfUserEntity = selfUserDetailsService.loadUserByUsername(userName);
        // 用户是否存在
        if (Objects.isNull(selfUserEntity)) {
            throw new UsernameNotFoundException("用户不存在");
        }

        // 用户是否禁用
        if (!selfUserEntity.isEnabled()) {
            throw new LockedException("用户被禁用");
        }

        // 校验验证码
        String codeInRedis = (String) redisUtils.get(uuid);
        if (StringUtils.isBlank(codeInRedis)) {
            throw new NonceExpiredException("验证码不存在或已过期");
        }

        // 校验成功后随即将验证码删除
        redisUtils.del(uuid);

        // 不区分大小写验证
        if (StringUtils.isBlank(code) || !code.equalsIgnoreCase(codeInRedis)) {
            throw new NonceExpiredException("验证码错误");
        }

        // 密码解密
        try {
            password = RsaUtils.decryptByPrivateKey(rsaConfig.getPrivateKey(), password);
        } catch (Exception e) {
            throw new BadCredentialsException("密码错误");
        }

        // 角色集合
        Set<GrantedAuthority> authorities = new HashSet<>();
        // 查询用户角色
        List<SysRoleEntity> sysRoleEntityList = sysUserService.selectSysRoleByUserId(selfUserEntity.getUserId());
        for (SysRoleEntity sysRoleEntity : sysRoleEntityList) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + sysRoleEntity.getRoleName()));
        }
        // 设定角色
        selfUserEntity.setAuthorities(authorities);
        // 进行登录
        return new UsernamePasswordAuthenticationToken(selfUserEntity, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
