package com.allen.part.service.impl;

import com.allen.part.common.ErrorCode;
import com.allen.part.constant.RedisConstant;
import com.allen.part.constant.UserConstant;
import com.allen.part.exception.ThrowUtils;
import com.allen.part.mapper.UserMapper;
import com.allen.part.model.entity.User;
import com.allen.part.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

import static com.allen.part.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 获取当前登录用户
     */
    @Override
    public User getLoginUser(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        ThrowUtils.throwIf(currentUser == null, ErrorCode.NOT_LOGIN_ERROR);
        return currentUser;
    }

    /**
     * 发送验证码
     */
    @Override
    public boolean sendCode(String phone) {
        // 随机生成验证码四位数验证码
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
        // 存储验证码 5分钟
        RBucket<Object> bucket = redissonClient.getBucket(RedisConstant.getPhoneCodeKey(phone));
        bucket.set(code, 60 * 5, TimeUnit.SECONDS);

        //  阿里云短信服务

        return true;
    }

    /**
     * 用户注册并登录
     */
    @Override
    public User userRegisterAndLogin(String phone, String code, HttpServletRequest request, boolean needAdmin) {
        RBucket<Object> bucket = redissonClient.getBucket(RedisConstant.getPhoneCodeKey(phone));
        String codeInRedis = (String) bucket.get();

        ThrowUtils.throwIf(codeInRedis == null, ErrorCode.OPERATION_ERROR, "验证码不存在或已过期");
        ThrowUtils.throwIf(!codeInRedis.equals(code), ErrorCode.OPERATION_ERROR, "验证码错误");

        // 校验用户是否已注册
        User oldUser = this.getOne(new LambdaQueryWrapper<User>()
                        .eq(User::getPhone, phone)
                , false);

        if (oldUser == null) {
            if (needAdmin) {
                ThrowUtils.throwException(ErrorCode.OPERATION_ERROR, "管理员未注册");
            }
            // 插入数据
            User user = new User();
            user.setPhone(phone);
            // 默认数据
            user.setAvatar(UserConstant.DEFAULT_USER_AVATAR);
            user.setName(UserConstant.DEFAULT_USER_NAME + RandomUtils.nextInt());
            user.setRoleType(UserConstant.USER_ROLE);
            this.save(user);

            request.getSession().setAttribute(USER_LOGIN_STATE, user);
            return user;
        } else {
            // 已注册
            ThrowUtils.throwIf(needAdmin && !isAdmin(oldUser), ErrorCode.OPERATION_ERROR, "用户无管理员权限");
            request.getSession().setAttribute(USER_LOGIN_STATE, oldUser);
            return oldUser;
        }
    }

    /**
     * 用户注销
     */
    @Override
    public boolean userLogout(HttpServletRequest request) {
        ThrowUtils.throwIf(request.getSession().getAttribute(USER_LOGIN_STATE) == null,
                ErrorCode.OPERATION_ERROR, "未登录");
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }

    private boolean isAdmin(User user) {
        return user != null && UserConstant.ADMIN_ROLE == user.getRoleType();
    }
}




