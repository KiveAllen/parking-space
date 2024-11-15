package com.allen.part.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 昵称
     */
    private String name;


    /**
     * 手机号，用于登录和接收验证码等
     */
    private String phone;


    /**
     * 用户头像链接
     */
    private String avatar;

    /**
     * 用户角色类型，1: 普通用户，2: 管理员
     */
    private Integer roleType;

    /**
     * 
     */
    private Date registerTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}