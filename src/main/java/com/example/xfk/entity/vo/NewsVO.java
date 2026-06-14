package com.example.xfk.entity.vo;

import lombok.Data;

import java.time.LocalDate;

/**
 * 新闻视图对象 VO
 */
@Data
public class NewsVO {
    private Long id;
    private String title;
    private String content;
    private LocalDate publishDate;
}
