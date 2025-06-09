package com.xz.schoolnavinfo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xz.schoolnavinfo.authentication.UserInfo;
import com.xz.schoolnavinfo.data.resp.Result;
import com.xz.schoolnavinfo.data.entity.User;


public interface UserService extends IService<User> {
    Result register(String username, String password);
    UserInfo getUserInfo();

    User getUserById(String id);
    Result changePassword(String newPassword,String oldPassword);

    Result changeNicknameOrAvatar(String nickname,String avatar);
}
