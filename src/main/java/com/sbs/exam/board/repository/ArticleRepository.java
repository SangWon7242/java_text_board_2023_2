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

  public List<Article> getArticles(String orderBy, int boardId, String searchKeyword, String searchKeywordTypeCode) {

    if (orderBy.equals("idAsc")) {
      return articles;
    }

    List<Article> sortedArticles = articles;

    if (orderBy.equals("idDesc")) {
      sortedArticles = Util.reverseList(articles);
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

    if (boardId == 0 && searchKeyword.length() == 0) {
      return sortedArticles;
    }

    List<Article> filteredArticles = new ArrayList<>();

    if (searchKeyword.length() > 0) {
      for (Article article : sortedArticles) {
        switch (searchKeywordTypeCode) {
          case "body":
            if(!article.getBody().contains(searchKeyword)) {
              continue;
            }
            break;
          case "title":
            if(!article.getTitle().contains(searchKeyword)) {
              continue;
            }
            break;
          case "title,body":
            if(!article.getTitle().contains(searchKeyword) && !article.getBody().contains(searchKeyword)) {
              continue;
            }
            break;
        }

        filteredArticles.add(article);

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
