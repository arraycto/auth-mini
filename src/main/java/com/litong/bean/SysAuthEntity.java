package com.litong.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author savior
 * @version v1.0
 * @date 2020/6/28 4:10 下午
 * @Description 权限实体
 */
@Data
@TableName("sys_auth")
public class SysAuthEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限ID
     */
    @TableId
    private Long authId;

    /**
     * 权限名称
     */
    private String authName;

    /**
     * 权限标识
     */
    private String authPermission;

    /**
     * 创建时间
     */
    private LocalDateTime authCreate;

    /**
     * 修改时间
     */
    private LocalDateTime authModified;
}
