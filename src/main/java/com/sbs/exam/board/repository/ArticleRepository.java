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

  private boolean searchOptionsMatched(Article article, int boardId, String searchKeywordTypeCode, String searchKeyword) {
    if (boardId != 0) {
      if(article.getBoardId() != boardId) {
        return false;
      }
    }

    if (searchKeyword.length() > 0) {
      switch (searchKeywordTypeCode) {
        case "body":
          if (!article.getBody().contains(searchKeyword)) {
            return false;
          }
          break;
        case "title":
          if (!article.getTitle().contains(searchKeyword)) {
            return false;
          }
          break;
        case "title,body":
          if (!article.getTitle().contains(searchKeyword) && !article.getBody().contains(searchKeyword)) {
            return false;
          }
          break;
      }
    }
    return true;
  }

  private List<Article> getSortedArticles(String orderBy) {
    List<Article> sortedArticles = articles;

    if (orderBy.equals("idAsc")) {
      return articles;
    }

    if (orderBy.equals("idDesc")) {
      sortedArticles = Util.reverseList(articles);
    }

    return sortedArticles;
  }

  public List<Article> getArticles(String orderBy, int boardId, String searchKeyword, String searchKeywordTypeCode,
                                   int limitStart, int limitCount) {

    List<Article> sortedArticles = getSortedArticles(orderBy);

    List<Article> filteredArticles = new ArrayList<>();
    int dataIndex = 0;

    for (Article article : sortedArticles) {
      if(searchOptionsMatched(article, boardId, searchKeywordTypeCode, searchKeyword) == false) {
        continue;
      }

      if (dataIndex >= limitStart) {
        filteredArticles.add(article);
      }

      dataIndex++;

      if (filteredArticles.size() == limitCount) {
        break;
      }
    }

    return filteredArticles;
  }

  public int write(int boardId, int loginedMemberId, String title, String body) {
    int id = lastId + 1;
    String regDate = Util.getNowDateStr();
    String updateDate = regDate;
    Article article = new Article(id, regDate, updateDate, boardId, loginedMemberId, title, body, 0);
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

  public int getTotalItemsCount(int boardId, String searchKeywordTypeCode, String searchKeyword) {
    int totalItemsCount = 0;

    for (Article article : articles) {
      if (searchOptionsMatched(article, boardId, searchKeywordTypeCode, searchKeyword) == false) {
        continue;
      }

      totalItemsCount++;
    }

    return totalItemsCount;
  }

  public void increaseHitCount(int id) {
    getArticleById(id).setHitCount(getArticleById(id).getHitCount() + 1);
  }
}
