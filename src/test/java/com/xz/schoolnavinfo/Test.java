package com.xz.schoolnavinfo;

import com.xz.schoolnavinfo.mapper.UserMapper;
import com.xz.schoolnavinfo.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class Test {
    @Autowired
    private UserMapper mapper;

    @org.junit.jupiter.api.Test
    public void test(){
        List<User> users = mapper.selectList(null);
    }
}
