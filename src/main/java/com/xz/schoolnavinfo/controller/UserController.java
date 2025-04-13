package com.xz.schoolnavinfo.controller;

import com.xz.schoolnavinfo.authentication.UserInfo;
import com.xz.schoolnavinfo.common.data.Result;
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
    private Result register(@RequestBody User user) {
        return userService.register(user);
    }


    @PostMapping("/user/changePassword")
    private Result changePassword(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String newPassword = map.get("newPassword");
        String oldPassword = map.get("oldPassword");
        if (username == null || newPassword == null || oldPassword == null) {
            return Result.fail("参数错误");
        }
        return userService.changePassword(username, newPassword, oldPassword);
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

    @GetMapping("/api/user/refreshJWT")
    private Result refreshJWT() {
//        UserInfo userInfo = userService.getUserInfo();
//        User user = userService.getUserById(userInfo.getId());
//        userInfo.setUsername(user.getUsername());
//        userInfo.setNickname(user.getNickname());
//        userInfo.setRole(user.getRole());
//        userInfo.setAvatarUrl(user.getAvatarUrl());
        return Result.data("+");
    }
}
