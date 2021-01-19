package com.shopBack.ecommerce.services.Impl;

import com.shopBack.ecommerce.domains.Article;
import com.shopBack.ecommerce.repositories.ArticleRepository;
import com.shopBack.ecommerce.services.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {


    private ArticleRepository articleRepository;
    private static final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);


    public ArticleServiceImpl() {

    }

    @Autowired
    public ArticleServiceImpl(ArticleRepository repository) {

        this.articleRepository = repository;
    }



    @Override
    public List<Article> findAll() {
        List<Article> list = new ArrayList<>();
        articleRepository.findAll().forEach(e -> list.add(e));
        return list;
    }

    @Override
    public Article findById(int id) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        return optionalArticle.get();
    }

    @Override
    public Article save(Article article) {
        log.info("article created with success.");
        return articleRepository.save(article);
    }

    @Override
    public void update(Article article, int id) {
        log.info("article to be updated : id = {}", article.getId());
        Article articlefromDb = this.findById(article.getId());
        if (articlefromDb != null) {
            article.setId(id);
            log.info("article updated with success.");
            articleRepository.save(article);
        } else {
            log.info("article with id = {} cannot found in the database", article.getId());
        }
    }


    @Override
    public void deleteById(int id) {
        Article article = this.findById(id);
        if(article != null) {
            articleRepository.delete(article);
            log.info("Article deleted with success.");
        }else {
            log.info("Error while deleting article.");

        }
    }
}
