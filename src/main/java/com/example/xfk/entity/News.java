package com.example.xfk.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalDate;

@Data
@TableName("news")
public class News {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;

    private String content;

    private LocalDate publishDate;

//    @TableField(fill = FieldFill.INSERT)
//    private LocalDateTime createdAt;
}

