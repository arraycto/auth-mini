package com.litong.security.filter;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author savior
 * @version v1.0
 * @date 2020/7/1 9:10 上午
 * @Description
 */
@Component
public class UserPermissionEvaluator implements PermissionEvaluator {

    /**
     * hasPermission鉴权方法
     * 这里仅仅判断PreAuthorize注解中的权限表达式
     * 实际中可以根据业务需求设计数据库通过targetUrl和permission做更复杂鉴权
     * 当然targetUrl不一定是URL可以是数据Id还可以是管理员标识等,这里根据需求自行设计
     * @Author savior
     * @CreateTime 2020/7/1 9:25
     * @Param  authentication  用户身份(在使用hasPermission表达式时Authentication参数默认会自动带上)
     * @Param  targetUrl  请求路径
     * @Param  permission 请求路径权限
     * @Return boolean 是否通过
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

        // 从authentication中拿到权限列表，在JwtAuthenticationFilter过滤器中已转换
        List<String> authList = (List) authentication.getCredentials();
        Set<String> permissions = new HashSet<>();
        permissions.addAll(authList);

        // 权限对比
        if (permissions.contains(permission.toString())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
