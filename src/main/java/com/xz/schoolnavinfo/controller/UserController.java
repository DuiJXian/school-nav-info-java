package com.xz.schoolnavinfo.controller;

import com.xz.schoolnavinfo.authentication.UserInfo;
import com.xz.schoolnavinfo.data.resp.Result;
import com.xz.schoolnavinfo.data.entity.User;
import com.xz.schoolnavinfo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController()
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/register")
    private Result register(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");
        if (username == null || password == null) {
            return Result.fail("参数错误");
        }
        if (username.isBlank()) {
            return Result.fail("账号不能为空");
        }
        if (password.length() < 6) {
            return Result.fail("密码长度不能小于6个字符");
        }
        return userService.register(username, password);
    }


    @PostMapping("/api/user/changePassword")
    private Result changePassword(@RequestBody Map<String, String> map) {
        String newPassword = map.get("newPassword");
        String oldPassword = map.get("oldPassword");
        if (newPassword == null || oldPassword == null) {
            return Result.fail("参数错误");
        }
        return userService.changePassword(newPassword, oldPassword);
    }

    @PostMapping("/api/user/changeNicknameAndAvatar")
    private Result changeNicknameAndAvatar(@RequestBody Map<String, String> map) {
        String nickname = map.get("nickname");
        String avatar = map.get("avatar");
        if (nickname == null && avatar == null) {
            return Result.fail("参数错误");
        }
        return userService.changeNicknameOrAvatar(nickname, avatar);
    }

    @GetMapping("/api/user/getUserInfo")
    private Result getUserInfo() {
        UserInfo userInfo = userService.getUserInfo();
        User user = userService.getUserById(userInfo.getId());
        userInfo.setUsername(user.getUsername());
        userInfo.setNickname(user.getNickname());
        userInfo.setRole(user.getRole());
        userInfo.setAvatarUrl(user.getAvatarUrl());
        return Result.data(userInfo);
    }

//    @GetMapping("/api/user/refreshJWT")
//    private Result refreshJWT() {
//        UserInfo userInfo = userService.getUserInfo();
//        User user = userService.getUserById(userInfo.getId());
//        userInfo.setUsername(user.getUsername());
//        userInfo.setNickname(user.getNickname());
//        userInfo.setRole(user.getRole());
//        userInfo.setAvatarUrl(user.getAvatarUrl());
//        return Result.data("");
//    }
}
