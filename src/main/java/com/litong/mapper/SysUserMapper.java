package com.litong.mapper;

import com.litong.bean.SysAuthEntity;
import com.litong.bean.SysRoleEntity;
import com.litong.bean.SysUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author savior
 * @version v1.0
 * @date 2020/6/28 4:38 下午
 * @Description 用户mapper
 */
public interface SysUserMapper extends BaseMapper<SysUserEntity> {

    /**
     * 通过用户ID查询角色集合
     * @Author savior
     * @CreateTime 2020/6/28 4:38 下午
     * @Param  userId 用户ID
     * @Return List<SysRoleEntity> 角色名集合
     */
    List<SysRoleEntity> selectSysRoleByUserId(Long userId);

    /**
     * 通过用户ID查询权限集合
     * @Author Sans
     * @CreateTime 2019/9/18 18:01
     * @Param  userId 用户ID
     * @Return List<SysMenuEntity> 角色名集合
     */
    List<SysAuthEntity> selectSysAuthByUserId(Long userId);

}
