package com.xz.schoolnavinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xz.schoolnavinfo.authentication.UserInfo;
import com.xz.schoolnavinfo.data.entity.User;
import com.xz.schoolnavinfo.data.dto.CommentDTO;
import com.xz.schoolnavinfo.mapper.CommentMapper;
import com.xz.schoolnavinfo.data.entity.Comment;
import com.xz.schoolnavinfo.service.CommentService;
import com.xz.schoolnavinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    @Override
    public List<CommentDTO> getCommentByArticleId(String articleId) {
        List<CommentDTO> commentDTOList = new ArrayList<>();
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        List<Comment> commentLIst = this.list(queryWrapper.eq(Comment::getArticleId, articleId));
        for (Comment comment : commentLIst){
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setComment(comment);

            User user = userService.getUserById(comment.getUserId());
            UserInfo userInfo = new UserInfo();
            userInfo.setNickname(user.getNickname());
            userInfo.setId(user.getId());
            userInfo.setUsername(user.getUsername());
            userInfo.setAvatarUrl(user.getAvatarUrl());

            commentDTO.setUserInfo(userInfo);

            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

    @Override
    public boolean createComment(Comment comment) {
        comment.setId(null);
        comment.setUserId(userService.getUserInfo().getId());
        return save(comment);
    }

    @Override
    public boolean deleteCommentById(String id) {
        return false;
    }
}
