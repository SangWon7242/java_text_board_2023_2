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

//  private boolean searchOptionsMatched(Article article, int boarId) {
//    if(boarId != 0) {
//      if(article.getBoardId() != boarId) {
//        return false;
//      }
//    }
//
//    return true;
//  }


  public List<Article> getArticles(String searchKeyword, String orderBy, int boardId) {

    if (orderBy.equals("idAsc")) {
      return articles;
    }

    List<Article> sortedArticles = articles;

    if (orderBy.equals("idDesc")) {
      sortedArticles = Util.reverseList(articles);
    }

    if(boardId == 0) {
      return sortedArticles;
    }

    List<Article> boardArticles = new ArrayList<>();

    if(boardId > 0) {
      for(Article article : sortedArticles) {
        if(article.getBoardId() == boardId) {
          boardArticles.add(article);
        }
      }
      return boardArticles;
    }

    if (searchKeyword.length() == 0) {
      return sortedArticles;
    }

    List<Article> filteredArticles = new ArrayList<>();

    if (searchKeyword.length() > 0) {
      for (Article article : sortedArticles) {
        boolean matched = article.getTitle().contains(searchKeyword) || article.getBody().contains(searchKeyword);

        if (matched) {
          filteredArticles.add(article);
        }
      }
    }

    return filteredArticles;
  }

  public int write(int boardId, int loginedMemberId, String title, String body) {
    int id = lastId + 1;
    String regDate = Util.getNowDateStr();
    String updateDate = regDate;
    Article article = new Article(id, regDate, updateDate, boardId, loginedMemberId, title, body);
    articles.add(article);
    lastId = id;

    return id;
  }

  public Article getArticleById(int id) {
    for (Article article : articles) {
      if (article.getId() == id) {
        return article;
      }
    }
    return null;
  }

  public void deleteArticleById(int id) {
    Article article = getArticleById(id);

    if (article != null) {
      articles.remove(article);
    }
  }

  public void modify(int id, String title, String body) {
    Article article = getArticleById(id);

    article.setTitle(title);
    article.setBody(body);
    article.setUpdateDate(Util.getNowDateStr());
  }
}
