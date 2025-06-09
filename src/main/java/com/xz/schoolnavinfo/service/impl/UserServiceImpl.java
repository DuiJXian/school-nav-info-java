package com.xz.schoolnavinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xz.schoolnavinfo.authentication.UserInfo;
import com.xz.schoolnavinfo.authentication.service.AuthJwtService;
import com.xz.schoolnavinfo.data.resp.Result;
import com.xz.schoolnavinfo.mapper.UserMapper;
import com.xz.schoolnavinfo.data.entity.User;
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
    public Result register(String username, String password) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User queryUser = getBaseMapper().selectOne(queryWrapper);
        if (queryUser != null) {
            return Result.fail("账号已存在");
        }
        User user = new User();
        user.setRole("NORMAL");
        user.setNickname(String.valueOf(System.currentTimeMillis()));
        user.setUsername(username);
         user.setPassword(passwordEncoder.encode(password));
        save(user);

        return Result.success("注册成功");
    }

    @Override
    public UserInfo getUserInfo() {
        return jwtService.getUserLoginInfo();
    }

    @Override
    public User getUserById(String id) {
        return getById(id);
    }

    @Override
    public Result changePassword(String newPassword, String oldPassword) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        UserInfo userInfo = getUserInfo();
        queryWrapper.eq(User::getUsername, userInfo.getUsername());
        User queryUser = getBaseMapper().selectOne(queryWrapper);
        if (queryUser == null) {
            return Result.fail("账号不存");
        }
        if (!passwordEncoder.matches(oldPassword, queryUser.getPassword())) {
            return Result.fail("原密码错误");
        }
        queryUser.setPassword(passwordEncoder.encode(newPassword));
        updateById(queryUser);
        return Result.success("修改成功");
    }

    @Override
    public Result changeNicknameOrAvatar(String nickname, String avatar) {
        UserInfo userInfo = getUserInfo();
        User user = getById(userInfo.getId());

        if (nickname != null) {
            user.setNickname(nickname);
        }
        if (avatar != null) {
            user.setAvatarUrl(avatar);
        }
        boolean res = saveOrUpdate(user);
        if (!res) {
            return Result.success("修改失败");
        }
        return Result.success("修改成功");
    }
}
