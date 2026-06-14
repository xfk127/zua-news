package com.example.xfk.service.impl;

import com.example.xfk.common.exception.BusinessException;
import com.example.xfk.common.result.ResultCode;
import com.example.xfk.entity.News;
import com.example.xfk.entity.dto.NewsDTO;
import com.example.xfk.mapper.NewsMapper;
import com.example.xfk.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 新闻业务实现类
 */
@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsMapper newsMapper;

    @Override
    public Map<String, Object> listPage(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<News> data = newsMapper.selectPage(offset, pageSize);
        int total = newsMapper.countNews();
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        result.put("total", total);
        return result;
    }

    @Override
    public void add(NewsDTO dto) {
        News news = new News();
        news.setTitle(dto.getTitle());
        news.setContent(dto.getContent());
        news.setPublishDate(dto.getPublishDate());
        if (newsMapper.insertNews(news) <= 0) {
            throw new BusinessException(ResultCode.NEWS_OPERATE_FAIL);
        }
    }

    @Override
    public void update(Long id, NewsDTO dto) {
        News news = new News();
        news.setId(id);
        news.setTitle(dto.getTitle());
        news.setContent(dto.getContent());
        news.setPublishDate(dto.getPublishDate());
        if (newsMapper.updateNews(news) <= 0) {
            throw new BusinessException(ResultCode.NEWS_NOT_FOUND);
        }
    }

    @Override
    public void delete(Long id) {
        if (newsMapper.deleteNews(id) <= 0) {
            throw new BusinessException(ResultCode.NEWS_NOT_FOUND);
        }
    }
}
