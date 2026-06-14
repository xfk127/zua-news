package com.example.xfk.service;

import com.example.xfk.entity.dto.LoginDTO;

/**
 * 用户业务接口
 */
public interface UserService {

    /**
     * 登录校验
     *
     * @param dto 登录参数
     * @return 登录成功返回 JWT Token
     */
    String login(LoginDTO dto);

    /**
     * 注册新用户
     *
     * @param dto 注册参数
     */
    void register(LoginDTO dto);
}
