package com.xz.schoolnavinfo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xz.schoolnavinfo.common.data.PageResult;
import com.xz.schoolnavinfo.common.data.Result;
import com.xz.schoolnavinfo.data.entity.Article;
import com.xz.schoolnavinfo.data.dto.ArticleDTO;

import java.util.List;

public interface ArticleService extends IService<Article> {
    public PageResult<ArticleDTO> getDiscussArticles(int pageNum, int pageSize);

    public PageResult<ArticleDTO> getActivityArticles(int pageNum, int pageSize);

    public List<ArticleDTO> getBannerArticles();

    public Article getArticleByArticleId(String articleId);

    public Result insertArticle(ArticleDTO articleDTO);

}
