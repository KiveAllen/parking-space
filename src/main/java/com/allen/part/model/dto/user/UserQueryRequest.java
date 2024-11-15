package com.allen.part.model.dto.user;

import com.allen.part.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户查询请求
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {
    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户角色
     */
    private Integer roleType;

    private static final long serialVersionUID = 1L;
}