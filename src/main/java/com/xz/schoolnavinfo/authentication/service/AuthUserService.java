package com.xz.schoolnavinfo.authentication.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xz.schoolnavinfo.model.entity.User;
import com.xz.schoolnavinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthUserService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User getUserByPassword(String username, String password) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User queryUser = userService.getBaseMapper().selectOne(queryWrapper);
        if (queryUser == null) {
            return null;
        }
        boolean isMatch = passwordEncoder.matches(password, queryUser.getPassword());
        return isMatch ? queryUser : null;
    }

    public void createUser(String username, String password, String nickname) {
        User entity = new User();
        entity.setRole("NORMAL");
        entity.setNickname(nickname);
        entity.setPassword(passwordEncoder.encode(password));
        entity.setUsername(username);
        userService.save(entity);
    }

}
