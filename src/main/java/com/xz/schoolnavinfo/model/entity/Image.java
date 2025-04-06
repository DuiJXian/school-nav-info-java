package com.xz.schoolnavinfo.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Image {
    @TableId(type = IdType.ASSIGN_ID) // 假设 ID 是手动分配的，如 UUID
    private String id;

    private String url;

    private String articleId;
}