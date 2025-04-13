package com.xz.schoolnavinfo.service;

import com.xz.schoolnavinfo.data.entity.Image;

import java.util.List;

public interface ImageService {
    List<Image> getImageByArticleId(String articleId);

    List<Image> getBannerImage();

    void insertImage(Image image);
}
