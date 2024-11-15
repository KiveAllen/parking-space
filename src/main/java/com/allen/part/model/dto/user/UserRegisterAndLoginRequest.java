package com.allen.part.model.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterAndLoginRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String phone;
    private String code;
    private boolean admin;

}
