package com.xz.schoolnavinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xz.schoolnavinfo.authentication.UserInfo;
import com.xz.schoolnavinfo.data.resp.PageResult;
import com.xz.schoolnavinfo.data.resp.Result;
import com.xz.schoolnavinfo.data.type.ArticleType;
import com.xz.schoolnavinfo.data.type.RoleType;
import com.xz.schoolnavinfo.mapper.ArticleMapper;
import com.xz.schoolnavinfo.data.entity.Article;
import com.xz.schoolnavinfo.data.entity.Image;
import com.xz.schoolnavinfo.data.entity.User;
import com.xz.schoolnavinfo.data.dto.ArticleDTO;
import com.xz.schoolnavinfo.service.ArticleService;
import com.xz.schoolnavinfo.service.ImageService;
import com.xz.schoolnavinfo.service.UserService;
import org.apache.commons.lang3.StringUtils;
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
            User user = userService.getUserById(article.getUserId());
            List<Image> images = imageService.getImageByArticleId(article.getId());
            ArticleDTO articleDTO = new ArticleDTO(article, user, images.stream().map(Image::getUrl).collect(Collectors.toList()));
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
            User newUser = new User(user.getId(), user.getUsername(), user.getNickname(), user.getAvatarUrl());
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
    public List<ArticleDTO> getArticleByText(String text) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
            .eq(Article::getType, ArticleType.DISCUSS)
            .and(e -> e
                .like(Article::getTitle, text)
                .or()
                .like(Article::getContent, text))
            .orderByDesc(Article::getCreateTime);

        List<Article> articleList = this.list(queryWrapper);
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        for (Article article : articleList) {
            User user = userService.getUserById(article.getUserId());
            User newUser = new User(user.getId(), user.getUsername(), user.getNickname(), user.getAvatarUrl());
            List<Image> images = imageService.getImageByArticleId(article.getId());
            ArticleDTO articleDTO = new ArticleDTO(article, newUser, images.stream().map(Image::getUrl).collect(Collectors.toList()));
            articleDTOList.add(articleDTO);
        }
        return articleDTOList;
    }

    @Override
    @Transactional
    public Result insertArticle(ArticleDTO articleDTO) {
        UserInfo userInfo = userService.getUserInfo();

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

    @Override
    public Result deleteArticle(String id, ArticleType type) {
        UserInfo userInfo = userService.getUserInfo();
        if (ArticleType.ACTIVITY.name().equals(type.name()) && userInfo.getRole().equals(RoleType.NORMAL.name())) {
            return Result.fail("没有权限");
        }

        Article article = getById(id);
        if (article == null) {
            return Result.fail("id错误");
        }
        if (!StringUtils.equals(article.getUserId(), userInfo.getId()) && StringUtils.equals(type.name(), ArticleType.DISCUSS.name())) {
            return Result.fail("错误删除");
        }
        if (removeById(id)) {
            return Result.success("删除成功");
        }
        return Result.fail("删除失败");
    }
}
