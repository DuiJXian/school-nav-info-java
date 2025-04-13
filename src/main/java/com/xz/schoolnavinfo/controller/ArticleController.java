package com.xz.schoolnavinfo.controller;

import com.xz.schoolnavinfo.authentication.UserInfo;
import com.xz.schoolnavinfo.authentication.service.AuthJwtService;
import com.xz.schoolnavinfo.common.data.PageQueryConf;
import com.xz.schoolnavinfo.common.data.Result;
import com.xz.schoolnavinfo.data.entity.Article;
import com.xz.schoolnavinfo.data.type.ArticleType;
import com.xz.schoolnavinfo.data.vo.ArticleDTO;
import com.xz.schoolnavinfo.service.ArticleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    final AuthJwtService jwtService;
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService, AuthJwtService jwtService) {
        this.articleService = articleService;
        this.jwtService = jwtService;
    }


    @PostMapping("/discuss/get")
    public Result getDiscussArticle(@RequestBody PageQueryConf conf) {
        return Result.data(
            articleService.getDiscussArticles(conf.getPageNum(), conf.getPageSize()),
            "成功");
    }

    @PostMapping("/discuss/insert")
    public Result insertArticlesDiscuss(@RequestBody ArticleDTO articleDTO) {
        if (insertArticle(articleDTO, ArticleType.DISCUSS)) return Result.fail("参数错误");
        return articleService.insertArticle(articleDTO);
    }


    @PostMapping("/activity/get")
    public Result getActivityArticle(@RequestBody PageQueryConf conf) {
        return Result.data(
            articleService.getActivityArticles(conf.getPageNum(), conf.getPageSize()),
            "成功");
    }

    @GetMapping("/activity/getBanner")
    public Result getActivityBanner() {
        return Result.data(articleService.getBannerArticles(), "成功");
    }

    @PostMapping("/activity/insert")
    public Result insertActivityArticle(@RequestBody ArticleDTO articleDTO) {
        if (insertArticle(articleDTO, ArticleType.ACTIVITY)) return Result.fail("参数错误");
        return articleService.insertArticle(articleDTO);
    }

    private boolean insertArticle(ArticleDTO articleDTO, ArticleType type) {
        Article article = articleDTO.getArticle();
        if (article.getTitle() == null) {
            return true;
        }
        UserInfo userLoginInfo = jwtService.getUserLoginInfo();
        article.setUserId(userLoginInfo.getId());
        article.setType(type.name());
        return false;
    }
}
