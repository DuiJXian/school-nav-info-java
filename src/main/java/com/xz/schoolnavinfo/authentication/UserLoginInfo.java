package com.xz.schoolnavinfo.authentication;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户信息登陆后的信息，会序列化到Jwt的payload
 */
@Getter
@Setter
public class UserLoginInfo {

    private String sessionId; // 会话id，全局唯一
    private String username;
    private String id;
    private String nickname; // 昵称
    private String role;
    private String picture;

    private Long expiredTime; // 过期时间

}
