package com.example.xfk.controller;

import com.example.xfk.common.result.Result;
import com.example.xfk.entity.dto.NewsDTO;
import com.example.xfk.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 新闻管理接口
 */
@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public Result<Map<String, Object>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(name = "page_size", defaultValue = "10") int pageSize) {
        return Result.success(newsService.listPage(page, pageSize));
    }

    @PostMapping
    public Result<Void> add(@RequestBody NewsDTO dto) {
        newsService.add(dto);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody NewsDTO dto) {
        newsService.update(id, dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        newsService.delete(id);
        return Result.success();
    }
}

