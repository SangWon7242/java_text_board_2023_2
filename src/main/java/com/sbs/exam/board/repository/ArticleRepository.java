package com.sbs.exam.board.repository;

import com.sbs.exam.board.vo.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleRepository {
  private List<Article> articles;
  private int lastId;

  public ArticleRepository() {
    lastId = 0;
    articles = new ArrayList<>();
  }
  public List<Article> getArticles() {
    return articles;
  }

  public int write(String title, String body) {
    int id = lastId + 1;
    Article article = new Article(id, title, body);
    articles.add(article);
    lastId = id;

    return id;
  }

  public Article getArticleById(int id) {
    for(Article article : articles) {
      if(article.getId() == id) {
        return article;
      }
    }
    return null;
  }

  public void deleteArticleById(int id) {
    Article article = getArticleById(id);

    if( article != null ) {
      articles.remove(article);
    }
  }
}
