package com.xz.schoolnavinfo.data.vo;

import com.xz.schoolnavinfo.authentication.UserInfo;
import com.xz.schoolnavinfo.data.entity.Comment;
import lombok.Data;


@Data
public class CommentDTO {
    Comment comment;
    UserInfo userInfo;
}
