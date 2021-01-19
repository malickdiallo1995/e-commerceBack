package com.shopBack.ecommerce.services;

import com.shopBack.ecommerce.domains.Article;

import java.util.List;

public interface ArticleService {

    List<Article> findAll();
    Article findById(int id);
    Article save(Article article);
    void update(Article article, int id);
    void deleteById(int id);
}
