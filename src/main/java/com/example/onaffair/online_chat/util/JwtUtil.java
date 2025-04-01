package com.example.onaffair.online_chat.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final String SECRET = "onaffaironaffaironaffaironaffaironaffaironaffaironaffaironaffaironaffaironaffaironaffair";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    // 从 JWT 中提取用户账号
    public static String extractUserAccount(String token) {
        return extractClaim(token, claims -> claims.get("account",String.class));
    }

    // 从 JWT 中提取用户身份
    public static Integer extractUserRole(String token) {
        return extractClaim(token, claims -> claims.get("role", Integer.class));
    }

    // 提取过期时间
    public static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // 提取特定声明
    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // 提取所有声明
    private static Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // 判断 Token 是否过期
    public static Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 生成 Token，包含用户身份
    public static String generateToken(String userAccount, Integer role) {
        return Jwts.builder()
                .claim("account", userAccount)
                .claim("role", role) // 添加用户身份信息
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10小时
                .signWith(SECRET_KEY, Jwts.SIG.HS256)
                .compact();
    }

    // 验证 Token
    public static Boolean validateToken(String token, UserDetails userDetails) {
        final String userAccount = extractUserAccount(token);
        return (userAccount.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
