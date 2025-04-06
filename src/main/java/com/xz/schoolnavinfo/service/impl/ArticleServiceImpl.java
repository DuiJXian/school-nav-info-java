package com.xz.schoolnavinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xz.schoolnavinfo.common.data.PageResult;
import com.xz.schoolnavinfo.common.data.Result;
import com.xz.schoolnavinfo.mapper.ArticleMapper;
import com.xz.schoolnavinfo.mapper.ImageMapper;
import com.xz.schoolnavinfo.model.entity.Article;
import com.xz.schoolnavinfo.model.entity.Image;
import com.xz.schoolnavinfo.model.entity.User;
import com.xz.schoolnavinfo.model.vo.ArticleDTO;
import com.xz.schoolnavinfo.service.ArticleService;
import com.xz.schoolnavinfo.service.ImageService;
import com.xz.schoolnavinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    final ImageService imageService;

    public ArticleServiceImpl(ImageService imageService) {
        this.imageService = imageService;
    }

    @Autowired
    private UserService userService;

    @Override
    public PageResult<ArticleDTO> getArticles(int pageNum, int pageSize) {

        Page<Article> page = new Page<>(pageNum, pageSize);
        Page<Article> articlePage = getBaseMapper().selectPage(page, null);

        List<ArticleDTO> list = new ArrayList<>();
        for (Article article : articlePage.getRecords()) {
            User user = userService.getUserById(article.getUserId());
            User newUser = new User();
            newUser.setUsername(user.getUsername());
            newUser.setPicture(user.getPicture());
            newUser.setNickname(user.getNickname());
            newUser.setId(user.getId());
            ArticleDTO articleDTO = new ArticleDTO(article, newUser, imageService.getImages(article.getId()));
            list.add(articleDTO);
        }
        return new PageResult<>(list, pageNum, pageSize, articlePage.getTotal());
    }

    @Override
    @Transactional
    public Result insertArticle(ArticleDTO articleDTO) {
        save(articleDTO.getArticle());
        List<Image> images = articleDTO.getImages();
        if (images != null) {
            for (Image image : images) {
                image.setArticleId(articleDTO.getArticle().getId());
                imageService.insertImage(image);
            }
        }
        return Result.success("创建成功");
    }
}
