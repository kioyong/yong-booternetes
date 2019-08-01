package com.yong.security.controller;

import com.yong.security.model.Article;
import com.yong.security.service.impl.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public List<Article> findAll() {
        return articleService.findAll();
    }

    @PostMapping
    public Article create(@RequestBody Article article) {
        return articleService.create(article);
    }

    @GetMapping("/{articleId}/{type}")
    public Article actionArticle(@PathVariable(name = "articleId") String articleId,
                                 @PathVariable(name = "type") String type) {
        return articleService.actionArticle(articleId, type);
    }

}
