package com.example.xfk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.xfk.common.exception.BusinessException;
import com.example.xfk.common.result.ResultCode;
import com.example.xfk.common.utils.JwtUtil;
import com.example.xfk.entity.User;
import com.example.xfk.entity.dto.LoginDTO;
import com.example.xfk.mapper.UserMapper;
import com.example.xfk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 用户业务实现类
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public String login(LoginDTO dto) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, dto.getUsername());
        User user = userMapper.selectOne(wrapper);
        if (user == null || !user.getPassword().equals(dto.getPassword())) {
            throw new BusinessException(ResultCode.LOGIN_ERROR);
        }
        return JwtUtil.generate(user.getId());
    }

    @Override
    public void register(LoginDTO dto) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, dto.getUsername());
        if (userMapper.selectOne(wrapper) != null) {
            throw new BusinessException(ResultCode.USER_ALREADY_EXISTS);
        }
        User newUser = new User();
        newUser.setUsername(dto.getUsername());
        newUser.setPassword(dto.getPassword());
        userMapper.insert(newUser);
    }
}
