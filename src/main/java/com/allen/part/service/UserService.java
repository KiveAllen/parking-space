package com.allen.part.service;

import com.allen.part.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author KiveAllen
* @description 针对表【user】的数据库操作Service
* @createDate 2024-11-15 13:48:26
*/
public interface UserService extends IService<User> {
    /**
     * 发送验证码
     */
    boolean sendCode(String phone);

    /**
     * 用户注册并登录
     *
     */
    User userRegisterAndLogin(String phone, String code, HttpServletRequest request, boolean needAdmin);

    /**
     * 用户注销
     *
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 获取当前登录用户
     */
    public User getLoginUser(HttpServletRequest request);

}
