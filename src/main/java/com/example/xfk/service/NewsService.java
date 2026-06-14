package com.example.xfk.service;

import com.example.xfk.entity.dto.NewsDTO;

import java.util.Map;

/**
 * 新闻业务接口
 */
public interface NewsService {

    /**
     * 分页查询新闻列表
     *
     * @param page     当前页码
     * @param pageSize 每页条数
     * @return 包含 data 列表和 total 总数的 Map
     */
    Map<String, Object> listPage(int page, int pageSize);

    /**
     * 新增新闻
     *
     * @param dto 新闻参数
     */
    void add(NewsDTO dto);

    /**
     * 更新新闻
     *
     * @param id  新闻 ID
     * @param dto 新闻参数
     */
    void update(Long id, NewsDTO dto);

    /**
     * 删除新闻
     *
     * @param id 新闻 ID
     */
    void delete(Long id);
}
