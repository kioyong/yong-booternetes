package com.yong.security.service.impl;

import com.yong.security.model.Article;
import com.yong.security.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ArticleService {

    private final ArticleRepository repository;

    public List<Article> findAll(){
        return repository.findAll();
    }

    public Article create(Article article){
        article.setCreatedDate(new Date());
        return repository.save(article);
    }

    public Article actionArticle(String articleId,String type){
        Optional<Article> optional = repository.findById(articleId);
        if(optional.isPresent()){
            Article article = optional.get();
            switch (type) {
                case "collection":
                    article.setCollection(article.getCollection() + 1);
                    break;
                case "unCollect":
                    article.setCollection(article.getCollection() - 1);
                    break;
                case "like":
                    article.setLike(article.getLike() + 1);
                    break;
                default:
                    throw new IllegalArgumentException("unSupport action type!");
            }
            return repository.save(article);
        }else{
            throw new IllegalArgumentException("Article not found update fail!");
        }

    }

}
