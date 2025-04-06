package com.xz.schoolnavinfo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xz.schoolnavinfo.common.data.PageResult;
import com.xz.schoolnavinfo.common.data.Result;
import com.xz.schoolnavinfo.model.entity.Article;
import com.xz.schoolnavinfo.model.vo.ArticleDTO;

public interface ArticleService extends IService<Article> {
    public PageResult<ArticleDTO> getArticles(int pageNum, int pageSize);

    public Result insertArticle(ArticleDTO articleDTO);
}
