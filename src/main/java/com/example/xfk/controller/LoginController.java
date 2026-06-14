package com.example.xfk.controller;

import com.example.xfk.common.result.Result;
import com.example.xfk.entity.dto.LoginDTO;
import com.example.xfk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 登录注册接口
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginDTO dto) {
        return Result.success(userService.login(dto));
    }

    @PostMapping("/register")
    public Result<Void> register(@RequestBody LoginDTO dto) {
        userService.register(dto);
        return Result.success();
    }
}
