package com.example.xfk.common.interceptor;

import com.example.xfk.common.constant.SystemConstant;
import com.example.xfk.common.exception.BusinessException;
import com.example.xfk.common.result.ResultCode;
import com.example.xfk.common.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录鉴权拦截器
 * 白名单路径（/api/login、/api/register）由 WebMvcConfig 排除，不经过此拦截器
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 放行 CORS 预检请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String authHeader = request.getHeader(SystemConstant.TOKEN_HEADER);

        // 未携带 Token
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith(SystemConstant.TOKEN_PREFIX)) {
            throw new BusinessException(ResultCode.UNAUTHORIZED);
        }

        // 去除 Bearer 前缀后校验
        String token = authHeader.substring(SystemConstant.TOKEN_PREFIX.length());
        if (!JwtUtil.verify(token)) {
            throw new BusinessException(ResultCode.UNAUTHORIZED);
        }

        // 将用户 ID 存入请求属性，方便后续使用
        request.setAttribute("userId", JwtUtil.getUserId(token));
        return true;
    }
}
