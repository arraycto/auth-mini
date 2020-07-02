package com.litong.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author savior
 * @version v1.0
 * @date 2020/6/28 4:18 下午
 * @Description 角色权限映射
 */
@Data
@TableName("sys_role_auth")
public class SysRoleAuthEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private Long roleAuthId;

    /**
     * 用户ID
     */
    private Long roleId;

    /**
     * 角色ID
     */
    private Long authId;

    /**
     * 创建时间
     */
    private LocalDateTime roleAuthCreate;

    /**
     * 修改时间
     */
    private LocalDateTime roleAuthModified;
}
