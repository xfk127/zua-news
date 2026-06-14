package com.example.xfk.entity.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * 新闻请求参数 DTO
 */
@Data
public class NewsDTO {
    private String title;
    private String content;
    private LocalDate publishDate;
}
