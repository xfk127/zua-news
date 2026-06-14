package com.example.xfk.entity.dto;

import lombok.Data;

/**
 * 登录请求参数 DTO
 */
@Data
public class LoginDTO {
    private String username;
    private String password;
}
