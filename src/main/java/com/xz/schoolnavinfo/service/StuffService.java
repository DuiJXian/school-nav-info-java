package com.xz.schoolnavinfo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xz.schoolnavinfo.data.dto.StuffDTO;
import com.xz.schoolnavinfo.data.entity.Stuff;

import java.util.List;

public interface StuffService extends IService<Stuff> {
    boolean createStuff(Stuff stuff);

    boolean deleteStuffById(String id);

    boolean updateStatus(String id);

    StuffDTO getStuffById(String id);

    List<StuffDTO> getStuffList();
}
