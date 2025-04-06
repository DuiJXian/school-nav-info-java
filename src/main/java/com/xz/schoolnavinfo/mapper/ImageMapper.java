package com.xz.schoolnavinfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xz.schoolnavinfo.model.entity.Image;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
public interface ImageMapper extends BaseMapper<Image> {
}
