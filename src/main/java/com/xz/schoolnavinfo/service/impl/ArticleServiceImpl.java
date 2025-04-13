package com.xz.schoolnavinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xz.schoolnavinfo.authentication.UserInfo;
import com.xz.schoolnavinfo.common.data.PageResult;
import com.xz.schoolnavinfo.common.data.Result;
import com.xz.schoolnavinfo.data.type.ArticleType;
import com.xz.schoolnavinfo.data.type.RoleType;
import com.xz.schoolnavinfo.mapper.ArticleMapper;
import com.xz.schoolnavinfo.data.entity.Article;
import com.xz.schoolnavinfo.data.entity.Image;
import com.xz.schoolnavinfo.data.entity.User;
import com.xz.schoolnavinfo.data.vo.ArticleDTO;
import com.xz.schoolnavinfo.service.ArticleService;
import com.xz.schoolnavinfo.service.ImageService;
import com.xz.schoolnavinfo.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    final ImageService imageService;

    public ArticleServiceImpl(ImageService imageService, UserService userService) {
        this.imageService = imageService;
        this.userService = userService;
    }

    private final UserService userService;

    @Override
    public PageResult<ArticleDTO> getDiscussArticles(int pageNum, int pageSize) {
        return getArticles(pageNum, pageSize, ArticleType.DISCUSS);
    }

    @Override
    public List<ArticleDTO> getBannerArticles() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        List<Article> articles = getBaseMapper().selectList(queryWrapper.eq(Article::isBanner, true));

        List<ArticleDTO> list = new ArrayList<>();
        for (Article article : articles) {
            List<Image> images = imageService.getImageByArticleId(article.getId());
            ArticleDTO articleDTO = new ArticleDTO(article, null, images.stream().map(Image::getUrl).collect(Collectors.toList()));
            list.add(articleDTO);
        }
        return list;
    }

    @Override
    public PageResult<ArticleDTO> getActivityArticles(int pageNum, int pageSize) {
        return getArticles(pageNum, pageSize, ArticleType.ACTIVITY);
    }

    private PageResult<ArticleDTO> getArticles(int pageNum, int pageSize, ArticleType type) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getType, type.name()).orderByDesc(Article::getCreateTime);
        Page<Article> page = new Page<>(pageNum, pageSize);
        Page<Article> articlePage = getBaseMapper().selectPage(page, queryWrapper);

        List<ArticleDTO> list = new ArrayList<>();
        for (Article article : articlePage.getRecords()) {
            User user = userService.getUserById(article.getUserId());
            User newUser = new User();
            newUser.setUsername(user.getUsername());
            newUser.setAvatarUrl(user.getAvatarUrl());
            newUser.setNickname(user.getNickname());
            newUser.setId(user.getId());
            List<Image> images = imageService.getImageByArticleId(article.getId());
            ArticleDTO articleDTO = new ArticleDTO(article, newUser, images.stream().map(Image::getUrl).collect(Collectors.toList()));
            list.add(articleDTO);
        }
        return new PageResult<>(list, pageNum, pageSize, articlePage.getTotal());
    }

    @Override
    public Article getArticleByArticleId(String articleId) {
        return getBaseMapper().selectById(articleId);
    }

    @Override
    @Transactional
    public Result insertArticle(ArticleDTO articleDTO) {
        UserInfo userInfo = userService.getUserInfo();
        if (userInfo.getRole().equals(RoleType.NORMAL.name())) {
            return Result.fail("没有权限");
        }

        Article article = articleDTO.getArticle();
        String articleId = IdWorker.getIdStr();
        article.setId(articleId);
        article.setUserId(userInfo.getId());
        if (!article.getType().equals(ArticleType.ACTIVITY.name())) {
            article.setBanner(false);
        }
        save(article);

        List<String> images = articleDTO.getImageList();
        if (!images.isEmpty()) {
            for (String url : images) {
                Image newImage = new Image();
                newImage.setArticleId(articleId);
                newImage.setUrl(url);
                imageService.insertImage(newImage);
            }
        }
        return Result.success("创建成功");
    }
}
