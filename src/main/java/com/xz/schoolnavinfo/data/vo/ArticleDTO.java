package com.xz.schoolnavinfo.data.vo;

import com.xz.schoolnavinfo.data.entity.Article;
import com.xz.schoolnavinfo.data.entity.User;
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
    List<String> imageList;
}

