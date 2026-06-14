package com.example.xfk.entity.vo;

import lombok.Data;

/**
 * 用户视图对象 VO（对外返回，不含敏感字段如密码）
 */
@Data
public class UserVO {
    private Integer id;
    private String username;
}
