package com.xz.schoolnavinfo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xz.schoolnavinfo.authentication.UserInfo;
import com.xz.schoolnavinfo.common.data.Result;
import com.xz.schoolnavinfo.data.entity.User;


public interface UserService extends IService<User> {
    Result register(User user);
    UserInfo getUserInfo();

    User getUserById(String id);
    Result changePassword(String username,String newPassword,String oldPassword);
}
