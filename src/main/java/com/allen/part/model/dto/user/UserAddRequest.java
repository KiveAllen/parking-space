package com.allen.part.model.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAddRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 手机号，用于登录和接收验证码等
     */
    private String phone;

    /**
     * 用户角色类型，1: 普通用户，2: 管理员
     */
    private Integer roleType;

    private String avatar;

    private String name;

}
