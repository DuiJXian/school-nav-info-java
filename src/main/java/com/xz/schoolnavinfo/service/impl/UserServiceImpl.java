package com.xz.schoolnavinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xz.schoolnavinfo.authentication.UserLoginInfo;
import com.xz.schoolnavinfo.authentication.service.AuthJwtService;
import com.xz.schoolnavinfo.common.data.Result;
import com.xz.schoolnavinfo.mapper.UserMapper;
import com.xz.schoolnavinfo.model.entity.User;
import com.xz.schoolnavinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthJwtService jwtService;

    @Override
    public Result register(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        User queryUser = getBaseMapper().selectOne(queryWrapper);
        if (queryUser != null) {
            return Result.fail("账号已存在");
        }
        user.setRole("NORMAL");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);

        return Result.data("注册成功");
    }

    @Override
    public UserLoginInfo getUserInfo() {
        return jwtService.getUserLoginInfo();
    }

    @Override
    public User getUserById(String id) {
        return getById(id);
    }

    @Override
    public Result changePassword(String username, String newPassword, String oldPassword) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(User::getUsername, username);
        User queryUser = getBaseMapper().selectOne(queryWrapper);
        if (queryUser == null){
            return Result.fail("账号不存");
        }
        if (!passwordEncoder.matches(oldPassword, queryUser.getPassword())) {
            return Result.fail("原密码错误");
        }
        queryUser.setPassword(passwordEncoder.encode(newPassword));
        updateById(queryUser);
        return Result.data("修改成功");
    }
}
