package com.litong.service;

import com.litong.bean.SysAuthEntity;
import com.litong.bean.SysRoleEntity;
import com.litong.bean.SysUserEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author savior
 * @version v1.0
 * @date 2020/6/30 4:05 下午
 * @Description 系统用户业务接口
 */
public interface SysUserService extends IService<SysUserEntity> {

    /**
     * 根据用户名查询实体
     * @Author savior
     * @CreateTime 2020/6/30 4:05 下午
     * @Param  username 用户名
     * @Return SysUserEntity 用户实体
     */
    SysUserEntity selectUserByName(String username);

    /**
     * 根据用户ID查询角色集合
     * @Author savior
     * @CreateTime 2020/6/30 4:05 下午
     * @Param  userId 用户ID
     * @Return List<SysRoleEntity> 角色名集合
     */
    List<SysRoleEntity> selectSysRoleByUserId(Long userId);

    /**
     * 根据用户ID查询权限集合
     * @Author savior
     * @CreateTime 2020/6/30 4:05 下午
     * @Param  userId 用户ID
     * @Return List<SysRoleEntity> 角色名集合
     */
    List<SysAuthEntity> selectSysAuthByUserId(Long userId);

}
