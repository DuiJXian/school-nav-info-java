package com.xz.schoolnavinfo.data.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;


@Data
@TableName("user")
public class User {
    @TableId(type = IdType.ASSIGN_ID)
    private String id; // 主键

    private String username; // 用户名

    private String password; // 密码（建议加密存储）

    private String name; // 姓名

    private String nickname; // 昵称

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime; // 创建时间

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime; // 修改时间

    private String studentId; // 学号

    private String role; // 角色

    private String avatarUrl;
}
