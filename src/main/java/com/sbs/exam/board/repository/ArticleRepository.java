package com.sbs.exam.board.repository;

import com.sbs.exam.board.util.Util;
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
  public List<Article> getArticles(String searchKeyword, String orderBy) {
    boolean orderByIdDesc = orderBy.equals("idDesc");

    List<Article> sortedArticles = null;

    if(orderByIdDesc) {
      sortedArticles = Util.reverseList(articles);
    }

    if(searchKeyword.length() == 0) {
      return articles;
    }

    List<Article> filteredArticles = new ArrayList<>();

    if(searchKeyword.length() > 0) {
      for(Article article : sortedArticles) {
        boolean matched = article.getTitle().contains(searchKeyword) || article.getBody().contains(searchKeyword);

        if(matched) {
          filteredArticles.add(article);
        }
      }
    }

    return filteredArticles;
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
