package com.xz.schoolnavinfo.controller;

import com.xz.schoolnavinfo.authentication.UserLoginInfo;
import com.xz.schoolnavinfo.common.data.Result;
import com.xz.schoolnavinfo.common.utils.JSON;
import com.xz.schoolnavinfo.model.entity.User;
import com.xz.schoolnavinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController()
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    private Result register(@RequestBody User user) {
        return userService.register(user);
    }

    @GetMapping("/getUserInfo")
    private Result getUserInfo() {
        UserLoginInfo userInfo = userService.getUserInfo();
        return Result.data(userInfo);
    }

    @PostMapping("/changePassword")
    private Result changePassword(@RequestBody String json) {
        Map<String, Object> stringObjectMap = JSON.parseToMap(json);
        String username = (String) stringObjectMap.get("username");
        String newPassword = (String) stringObjectMap.get("newPassword");
        String oldPassword = (String) stringObjectMap.get("oldPassword");
        if (username == null || newPassword == null || oldPassword == null) {
            return Result.fail("参数错误");
        }
        return userService.changePassword(username, newPassword, oldPassword);
    }
}
