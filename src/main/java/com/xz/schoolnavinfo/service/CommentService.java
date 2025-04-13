package com.xz.schoolnavinfo.service;

import com.xz.schoolnavinfo.authentication.UserInfo;
import com.xz.schoolnavinfo.data.entity.Comment;
import com.xz.schoolnavinfo.data.vo.CommentDTO;

import java.util.List;

public interface CommentService {
    List<CommentDTO> getCommentByArticleId(String articleId);

    boolean createComment(Comment comment);

    boolean deleteCommentById(String id);
}
