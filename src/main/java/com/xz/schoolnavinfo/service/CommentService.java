package com.xz.schoolnavinfo.service;

import com.xz.schoolnavinfo.data.entity.Comment;
import com.xz.schoolnavinfo.data.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    List<CommentDTO> getCommentByArticleId(String articleId);

    boolean createComment(Comment comment);

    boolean deleteCommentById(String id);
}
