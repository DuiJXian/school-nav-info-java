package com.xz.schoolnavinfo.service;

import com.xz.schoolnavinfo.model.entity.Image;

import java.util.List;

public interface ImageService {
    List<Image> getImages(String articleId);

    void insertImage(Image image);
}
