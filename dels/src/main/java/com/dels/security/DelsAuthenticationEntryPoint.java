package com.dels.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * 
 * @description 登录错误拦截器
 * @author MaytoMarry
 * @time 2017-07-11
 */
public class DelsAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        if (request.getHeader("Accept").contains("application/json")) {
            response.sendError(401, "Authentication Failed: " + authException.getMessage());
        } else {
            super.commence(request, response, authException);
        }
    }
}
