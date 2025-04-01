package com.example.onaffair.online_chat.filter;

import com.example.onaffair.online_chat.util.JwtUtil;
import com.example.onaffair.online_chat.util.Result;
import com.example.onaffair.online_chat.enums.ResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1. 判断是否需要认证
        if (!requiresAuthentication(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader("Authorization");

        String userAccount = null;
        String jwt = null;
        Integer role = null;

        // 2. 验证 Token 是否存在
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            sendErrorResponse(response, ResultCode.UNAUTHORIZED, "Token is missing");
            return;
        }

        jwt = authorizationHeader.substring(7);
        try {
            userAccount = JwtUtil.extractUserAccount(jwt);
            role = JwtUtil.extractUserRole(jwt); // 解析用户身份
        } catch (Exception e) {
            sendErrorResponse(response, ResultCode.FORBIDDEN, "Token is invalid");
            return;
        }

        // 3. 用户存在但未授权
        if (userAccount != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userAccount);

            // 验证 Token
            if (!JwtUtil.validateToken(jwt, userDetails)) {
                sendErrorResponse(response, ResultCode.FORBIDDEN, "Token is invalid");
                return;
            }

            // 验证 Token 是否过期
            if (JwtUtil.isTokenExpired(jwt)) {
                sendErrorResponse(response, ResultCode.FORBIDDEN, "Token has expired");
                return;
            }

            List<GrantedAuthority> authorities = new ArrayList<>();
            if (role == 1){
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            } else if (role == 0) {
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            }

            // 认证通过，创建身份验证对象
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            authorities
                    );
            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // 在请求中设置用户身份信息
            request.setAttribute("userRole", role);
        }

        filterChain.doFilter(request, response);
    }

    // 判断请求是否需要认证
    private boolean requiresAuthentication(HttpServletRequest request) {
        String path = request.getServletPath();
        return !path.startsWith("/api/test/")
                && !path.contains("/public/")
                && !path.startsWith("/static/avatar/");
    }

    // 发送错误响应
    private void sendErrorResponse(HttpServletResponse response,
                                   ResultCode code,
                                   String msg) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(code.getCode());
        Result<?> res = Result.error(code, msg);
        try {
            String jsonRes = new ObjectMapper().writeValueAsString(res);
            response.getWriter().write(jsonRes);
            response.getWriter().flush();
        } catch (Exception ignored) {
        }
    }
}
