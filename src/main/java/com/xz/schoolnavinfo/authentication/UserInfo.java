package com.xz.schoolnavinfo.authentication;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户信息登陆后的信息，会序列化到Jwt的payload
 */
@Getter
@Setter
public class UserInfo {
    private String id;
    private String username;
    private String nickname;
    private String avatarUrl;
    private String Role;

    private Long expiredTime; // 过期时间

}
