package com.xz.schoolnavinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xz.schoolnavinfo.mapper.ImageMapper;
import com.xz.schoolnavinfo.model.entity.Image;
import com.xz.schoolnavinfo.service.ImageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, com.xz.schoolnavinfo.model.entity.Image> implements ImageService {

    @Override
    public List<Image> getImages(String articleId) {
        LambdaQueryWrapper<Image> query = new LambdaQueryWrapper<>();
        query.eq(Image::getArticleId, articleId);
        return getBaseMapper().selectList(query);
    }

    @Override
    public void insertImage(Image image) {
        save(image);
    }
}
