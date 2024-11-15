package com.allen.part.constant;

/**
 * Redis 常量
 *
 * @author Allen
 */
public interface RedisConstant {

    /**
     * 用户登录或注册的验证码
     */
    String PHONE_CODE_KEY = "phone:code:";


    /**
     * 拼接手机号
     */
    static String getPhoneCodeKey(String phone) {
        return PHONE_CODE_KEY + phone;
    }

}

