package com.litong.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author savior
 * @version v1.0
 * @date 2020/6/28 3:51 PM
 * @Description 用户实体
 */
@Data
@TableName("sys_user")
public class SysUserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 启用状态
     * true：启用，false：禁用
     */
    private Boolean userStatus;

    /**
     * 创建时间
     */
    private LocalDateTime userCreate;

    /**
     * 修改时间
     */
    private LocalDateTime userModified;
}
