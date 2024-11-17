package com.allen.part.controller;


import com.allen.part.common.BaseResponse;
import com.allen.part.common.DeleteRequest;
import com.allen.part.common.ErrorCode;
import com.allen.part.common.ResultUtils;
import com.allen.part.constant.UserConstant;
import com.allen.part.exception.ThrowUtils;
import com.allen.part.model.dto.user.UserAddRequest;
import com.allen.part.model.dto.user.UserQueryRequest;
import com.allen.part.model.dto.user.UserRegisterAndLoginRequest;
import com.allen.part.model.dto.user.UserUpdateRequest;
import com.allen.part.model.entity.User;
import com.allen.part.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户接口
 * 提供了用户相关的所有操作，包括但不限于用户的注册、登录、注销以及用户的增删改查等。
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;


    /**
     * 获取当前登录用户
     *
     * @param request HTTP请求对象
     * @return 当前登录用户的信息
     */
    @GetMapping("/get/login")
    public BaseResponse<User> getLoginUser(HttpServletRequest request) {
        return ResultUtils.success(userService.getLoginUser(request));
    }

    /**
     * 用户注册并登录
     */
    @PostMapping("/registerAndLogin")
    public BaseResponse<User> userRegisterAndLogin(@RequestBody UserRegisterAndLoginRequest userRegisterAndLoginRequest, HttpServletRequest request) {
        User user = userService.userRegisterAndLogin(
                userRegisterAndLoginRequest.getPhone(),
                userRegisterAndLoginRequest.getCode(),
                request,
                userRegisterAndLoginRequest.isAdmin()
        );
        return ResultUtils.success(user);
    }


    /**
     * 发送短信验证码
     */
    @GetMapping("/sendCode")
    public BaseResponse<Boolean> sendCode(@RequestParam String phone) {
        return ResultUtils.success(userService.sendCode(phone));
    }

    /**
     * 用户注销
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        return ResultUtils.success(userService.userLogout(request));
    }

    /**
     * 创建用户
     */
    @PostMapping("/add")
    public BaseResponse<Boolean> addUser(@RequestBody UserAddRequest userAddRequest) {
        // 校验参数是否为空
        ThrowUtils.throwIf(userAddRequest == null, ErrorCode.PARAMS_ERROR);

        // 转为User
        User user = new User();
        BeanUtils.copyProperties(userAddRequest, user);
        if (user.getAvatar() == null) {
            user.setAvatar(UserConstant.DEFAULT_USER_AVATAR);
        }
        if (user.getName() == null) {
            user.setName(UserConstant.DEFAULT_USER_NAME + RandomUtils.nextInt());
        }

        // 调用方法
        return ResultUtils.success(userService.save(user));
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest) {
        ThrowUtils.throwIf(deleteRequest == null || deleteRequest.getId() <= 0, ErrorCode.PARAMS_ERROR);
        return ResultUtils.success(userService.removeById(deleteRequest.getId()));
    }

    /**
     * 更新用户
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateUser(@RequestBody User user) {
        return ResultUtils.success(userService.updateById(user));
    }

    /**
     * 分页获取用户列表
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<User>> listUserByPage(@RequestBody UserQueryRequest userQueryRequest) {
        ThrowUtils.throwIf(userQueryRequest == null, ErrorCode.PARAMS_ERROR);
        Page<User> userPage = userService.page(new Page<>(userQueryRequest.getPageNum(), userQueryRequest.getPageSize()));
        Page<User> resultPage = userService.page(userPage, new LambdaQueryWrapper<User>()
                .eq(userQueryRequest.getRoleType() != null, User::getRoleType, userQueryRequest.getRoleType())
                .like(!userQueryRequest.getUserName().isEmpty(), User::getName, userQueryRequest.getUserName())
        );
        return ResultUtils.success(resultPage);
    }
}
