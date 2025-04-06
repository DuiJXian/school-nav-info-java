package com.xz.schoolnavinfo.common.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {
    private String secretKey = "yourSecretKey";  // 设置一个密钥
    private long expirationTime = 1000 * 60 * 60 * 10;  // 设置过期时间为 10 小时

    // 生成 JWT Token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)  // 设置主题为用户名
                .setIssuedAt(new Date())  // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // 设置过期时间
                .signWith(SignatureAlgorithm.HS256, secretKey) // 使用 HS256 签名算法
                .compact();
    }

    // 提取 Token 中的 Claims（有效载荷）
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)  // 设置签名密钥
                .parseClaimsJws(token)  // 解析 JWT Token
                .getBody();  // 返回有效载荷
    }

    // 从 Token 中提取用户名
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();  // 获取 Token 的主题部分（用户名）
    }

    // 获取 Token 的过期时间
    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    // 判断 Token 是否已过期
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());  // 比较当前时间与过期时间
    }

    // 校验 Token 是否有效（用户名匹配且未过期）
    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }
}


