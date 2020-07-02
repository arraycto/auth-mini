package com.litong.security.entity;

import com.litong.bean.SysUserEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author savior
 * @version v1.0
 * @date 2020/6/30 11:04 上午
 * @Description
 */
@Data
public class SelfUserEntity implements UserDetails {

    private SysUserEntity userEntity;

    private Collection<GrantedAuthority> authorities;


    public Set<String> getRoles() {
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    }
    public Long getUserId() {
        return userEntity.getUserId();
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userEntity.getUserStatus();
    }
}
