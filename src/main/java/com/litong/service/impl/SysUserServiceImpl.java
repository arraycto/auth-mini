package com.litong.service.impl;

import com.litong.bean.SysAuthEntity;
import com.litong.bean.SysRoleEntity;
import com.litong.bean.SysUserEntity;
import com.litong.mapper.SysUserMapper;
import com.litong.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author savior
 * @version v1.0
 * @date 2020/6/30 4:08 下午
 * @Description 系统用户业务实现
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUserEntity> implements SysUserService {

    @Override
    public SysUserEntity selectUserByName(String username) {
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserEntity::getUsername,username);
        return this.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public List<SysRoleEntity> selectSysRoleByUserId(Long userId) {
        return this.baseMapper.selectSysRoleByUserId(userId);
    }

    @Override
    public List<SysAuthEntity> selectSysAuthByUserId(Long userId) {
        return this.baseMapper.selectSysAuthByUserId(userId);
    }
}
