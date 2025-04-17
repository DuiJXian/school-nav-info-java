package com.xz.schoolnavinfo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xz.schoolnavinfo.authentication.UserInfo;
import com.xz.schoolnavinfo.common.utils.JSON;
import com.xz.schoolnavinfo.data.dto.StuffDTO;
import com.xz.schoolnavinfo.data.entity.Stuff;
import com.xz.schoolnavinfo.data.entity.User;
import com.xz.schoolnavinfo.mapper.ArticleMapper;
import com.xz.schoolnavinfo.mapper.StuffMapper;
import com.xz.schoolnavinfo.service.StuffService;
import com.xz.schoolnavinfo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class StuffServiceImpl extends ServiceImpl<StuffMapper, Stuff> implements StuffService {
    private final UserService userService;

    public StuffServiceImpl(UserService userService) {
        this.userService = userService;
    }


    @Override
    public boolean createStuff(Stuff stuff) {
        UserInfo user = userService.getUserInfo();
        stuff.setStatus(false);
        stuff.setPublisherId(user.getId());
        return save(stuff);
    }

    @Override
    public boolean deleteStuffById(String id) {
        UserInfo user = userService.getUserInfo();
        Stuff stuff = getStuffById(id).getStuff();
        if (stuff == null || !StringUtils.equals(stuff.getPublisherId(), user.getId())) {
            return false;
        }
        return removeById(id);
    }

//    private Stuff getStuffById(String id) {
//        return getById(id);
//    }

    @Override
    public boolean updateStatus(String id) {
        UserInfo user = userService.getUserInfo();
        Stuff stuff = getStuffById(id).getStuff();
        if (stuff == null || !StringUtils.equals(user.getId(), stuff.getPublisherId())) {
            return false;
        }
        stuff.setStatus(true);
        return saveOrUpdate(stuff);
    }

    @Override
    public StuffDTO getStuffById(String id) {
        Stuff stuff = getById(id);
        User user = userService.getUserById(stuff.getPublisherId());
        UserInfo userInfo = JSON.convert(user, UserInfo.class);
        return new StuffDTO(stuff, userInfo);
    }

    @Override
    public List<StuffDTO> getStuffList() {
        List<Stuff> stuffList = list();
        List<StuffDTO> stuffDTOList = new ArrayList<>();
        for (Stuff stuff : stuffList) {
            User user = userService.getUserById(stuff.getPublisherId());
            UserInfo userInfo = JSON.convert(user, UserInfo.class);
            StuffDTO stuffDTO = new StuffDTO(stuff, userInfo);
            stuffDTOList.add(stuffDTO);
        }
        return stuffDTOList;
    }
}
