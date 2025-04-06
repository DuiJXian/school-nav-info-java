package com.xz.schoolnavinfo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xz.schoolnavinfo.authentication.UserLoginInfo;
import com.xz.schoolnavinfo.common.data.Result;
import com.xz.schoolnavinfo.model.entity.User;


public interface UserService extends IService<User> {
    Result register(User user);
    UserLoginInfo getUserInfo();

    User getUserById(String id);
    Result changePassword(String username,String newPassword,String oldPassword);
}
