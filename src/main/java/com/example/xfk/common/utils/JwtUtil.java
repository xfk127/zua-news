package com.example.xfk.common.utils;

import com.example.xfk.common.constant.SystemConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT 工具类
 */
public class JwtUtil {

    /** 签名密钥（至少32位字符） */
    private static final String SECRET = "xfk-news-secret-key-2024-secure!!";

    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    private JwtUtil() {}

    /**
     * 生成 Token
     *
     * @param userId 用户 ID
     * @return token 字符串
     */
    public static String generate(Object userId) {
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + SystemConstant.TOKEN_EXPIRE_MS))
                .signWith(KEY)
                .compact();
    }

    /**
     * 解析 Token，获取 Claims
     *
     * @param token token 字符串（不含 Bearer 前缀）
     * @return Claims，解析失败返回 null
     */
    public static Claims parse(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * 校验 Token 是否有效
     *
     * @param token token 字符串（不含 Bearer 前缀）
     * @return 有效返回 true
     */
    public static boolean verify(String token) {
        return parse(token) != null;
    }

    /**
     * 从 Token 中获取用户 ID
     *
     * @param token token 字符串（不含 Bearer 前缀）
     * @return 用户 ID，解析失败返回 null
     */
    public static String getUserId(String token) {
        Claims claims = parse(token);
        return claims != null ? claims.getSubject() : null;
    }
}
