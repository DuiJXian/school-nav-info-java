package com.xz.schoolnavinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xz.schoolnavinfo.mapper.ImageMapper;
import com.xz.schoolnavinfo.data.entity.Image;
import com.xz.schoolnavinfo.service.ImageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, com.xz.schoolnavinfo.data.entity.Image> implements ImageService {

    @Override
    public List<Image> getImageByArticleId(String articleId) {
        LambdaQueryWrapper<Image> query = new LambdaQueryWrapper<>();
        return getBaseMapper().selectList(query.eq(Image::getArticleId, articleId));
    }

    @Override
    public List<Image> getBannerImage() {
        LambdaQueryWrapper<Image> query = new LambdaQueryWrapper<>();
        return getBaseMapper().selectList(query.like(Image::getUrl, "banner"));
    }

    @Override
    public void insertImage(Image image) {
        save(image);
    }
}
