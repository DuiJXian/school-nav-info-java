package com.xz.schoolnavinfo.controller;

import com.xz.schoolnavinfo.authentication.UserLoginInfo;
import com.xz.schoolnavinfo.authentication.service.AuthJwtService;
import com.xz.schoolnavinfo.common.data.PageQueryConf;
import com.xz.schoolnavinfo.common.data.Result;
import com.xz.schoolnavinfo.model.entity.Article;
import com.xz.schoolnavinfo.model.type.ArticleType;
import com.xz.schoolnavinfo.model.vo.ArticleDTO;
import com.xz.schoolnavinfo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    AuthJwtService jwtService;
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }


    @PostMapping("/getArticles")
    public Result getArticle(@RequestBody PageQueryConf conf) {
        return Result.data(
            articleService.getArticles(conf.getPageNum(), conf.getPageSize()),
            "成功");
    }

    @PostMapping("/insertArticles/discuss")
    public Result insertArticlesDiscuss(@RequestBody ArticleDTO articleDTO) {
        if (insertArticle(articleDTO, ArticleType.DISCUSS)) return Result.fail("参数错误");
        return articleService.insertArticle(articleDTO);
    }

    @PostMapping("/insertArticles/notify")
    public Result insertArticlesNotify(@RequestBody ArticleDTO articleDTO) {
        if (insertArticle(articleDTO, ArticleType.ACTIVITY)) return Result.fail("参数错误");
        return articleService.insertArticle(articleDTO);
    }

    private boolean insertArticle(ArticleDTO articleDTO, ArticleType type) {
        Article article = articleDTO.getArticle();
        if (article.getTitle() == null) {
            return true;
        }
        UserLoginInfo userLoginInfo = jwtService.getUserLoginInfo();
        article.setUserId(userLoginInfo.getId());
        article.setType(type.name());
        return false;
    }
}
