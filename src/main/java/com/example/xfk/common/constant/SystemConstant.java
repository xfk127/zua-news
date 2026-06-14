package com.example.xfk.common.constant;

/**
 * 系统常量
 */
public class SystemConstant {

    private SystemConstant() {}

    /** 请求头中携带 Token 的字段名 */
    public static final String TOKEN_HEADER = "Authorization";

    /** Token 前缀 */
    public static final String TOKEN_PREFIX = "Bearer ";

    /** JWT 过期时间：7天（毫秒） */
    public static final long TOKEN_EXPIRE_MS = 7 * 24 * 60 * 60 * 1000L;

    /** 登录放行路径 */
    public static final String[] WHITE_LIST = {
            "/api/login",
            "/api/register"
    };
}
