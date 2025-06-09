package com.xz.schoolnavinfo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xz.schoolnavinfo.data.resp.PageResult;
import com.xz.schoolnavinfo.data.resp.Result;
import com.xz.schoolnavinfo.data.entity.Article;
import com.xz.schoolnavinfo.data.dto.ArticleDTO;
import com.xz.schoolnavinfo.data.type.ArticleType;

import java.util.List;

public interface ArticleService extends IService<Article> {
    public PageResult<ArticleDTO> getDiscussArticles(int pageNum, int pageSize);

    public PageResult<ArticleDTO> getActivityArticles(int pageNum, int pageSize);

    public List<ArticleDTO> getBannerArticles();

    public Article getArticleByArticleId(String articleId);

    public List<ArticleDTO> getArticleByText(String text);

    public Result insertArticle(ArticleDTO articleDTO);

    public Result deleteArticle(String id, ArticleType type);

}
