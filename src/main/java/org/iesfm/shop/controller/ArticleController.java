package org.iesfm.shop.controller;

import org.iesfm.shop.Article;
import org.iesfm.shop.dao.ArticleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    private ArticleDAO articleDAO;


    @RequestMapping(method = RequestMethod.GET, path = "/articles")
    public List<Article> getArticles() {
        return articleDAO.list();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/articles/{id}")
    public Article getArticleId(@PathVariable int id) {
        return articleDAO.get(id);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/articles")
    public void postArticle(@RequestBody Article article) {
        Article articleFound = getArticleId(article.getId());
        if (articleFound == null) {
            articleDAO.insert(article);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT
            );
        }

    }

    @RequestMapping(method = RequestMethod.PUT, path = "/articles")
    public void putArticles(@RequestBody Article article) {
        Article articleFound = getArticleId(article.getId());
        if (articleFound != null) {
            articleDAO.update(article);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/articles?")
    public List<Article> getTagArticles(@RequestParam(name = "tag", required = false) String tag) {
        if (tag != null) {
            return articleDAO.list(tag);
        } else {
            return articleDAO.list();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/articles/")
    public void deleteArticle(@PathVariable int id) {
        Article articleFound = getArticleId(id);
        if (articleFound == null) {
            articleDAO.delete(id);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
    }
}
