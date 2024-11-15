package com.allen.part.model.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserUpdateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 用户角色类型，1: 普通用户，2: 管理员
     */
    private Integer roleType;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String name;

}
