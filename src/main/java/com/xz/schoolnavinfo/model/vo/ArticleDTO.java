package com.xz.schoolnavinfo.model.vo;

import com.xz.schoolnavinfo.authentication.UserLoginInfo;
import com.xz.schoolnavinfo.model.entity.Article;
import com.xz.schoolnavinfo.model.entity.Image;
import com.xz.schoolnavinfo.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {
    Article article;
    User userInfo;
    List<Image> images;
}

