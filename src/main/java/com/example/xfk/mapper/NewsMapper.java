package com.example.xfk.mapper;

import com.example.xfk.entity.News;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NewsMapper {
    List<News> selectPage(@Param("offset") int offset, @Param("limit") int limit);
    int countNews();
    int updateNews(News news);
    int deleteNews(@Param("id") Long id);

    @Insert("INSERT INTO news(title, content, publish_date) VALUES(#{title}, #{content}, #{publishDate})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertNews(News news);
}
