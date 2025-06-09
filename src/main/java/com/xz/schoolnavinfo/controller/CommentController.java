package com.xz.schoolnavinfo.controller;


import com.xz.schoolnavinfo.data.resp.Result;
import com.xz.schoolnavinfo.data.entity.Comment;
import com.xz.schoolnavinfo.service.ArticleService;
import com.xz.schoolnavinfo.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService, ArticleService articleService) {
        this.commentService = commentService;
        this.articleService = articleService;
    }

    private final ArticleService articleService;

    @PostMapping("/getComments")
    private Result getComments(@RequestBody Map<String, String> map) {
        String article = map.get("articleId");
        if (StringUtils.isBlank(article)) {
            return Result.fail("参数错误");
        }
        if (articleService.getArticleByArticleId(article) == null) {
            return Result.fail("文章不存在");
        }
        return Result.data(commentService.getCommentByArticleId(article));
    }

    @PostMapping("/createComment")
    private Result createComment(@RequestBody Comment comment) {
        if (StringUtils.isBlank(comment.getArticleId()) || StringUtils.isBlank(comment.getContent())) {
            return Result.fail("参数错误");
        }

        if (commentService.createComment(comment)) {
            return Result.success();
        }
        return Result.fail("创建失败");
    }
}

