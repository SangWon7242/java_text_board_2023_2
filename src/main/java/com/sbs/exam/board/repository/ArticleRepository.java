package com.sbs.exam.board.repository;

import com.sbs.exam.board.util.Util;
import com.sbs.exam.board.vo.Article;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleRepository {
  private List<Article> articles;
  private int lastId;

  public void init() {
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

  private List<Article> getSortedArticles(String orderByColumn, String orderBy) {
    if (orderByColumn.equals("id") && orderBy.equals("idAsc")) {
      return articles;
    }

    List<Article> sortedArticles = articles;

    if (orderByColumn.equals("id") && orderBy.equals("idDesc")) {
      sortedArticles = Util.reverseList(articles);
    }
    else if (orderByColumn.equals("hitCount") && orderBy.equals("idAsc")) {
      return sortedArticles.stream().sorted(Comparator.comparing(Article::getHitCount)).collect(Collectors.toList());
    }
    else if (orderByColumn.equals("hitCount") && orderBy.equals("idDesc")) {
      return sortedArticles.stream().sorted(Comparator.comparing(Article::getHitCount).reversed()).collect(Collectors.toList());
    }
    else if (orderByColumn.equals("goodlikePoint") && orderBy.equals("idAsc")) {
      return sortedArticles.stream().sorted(Comparator.comparing(Article::getGoodlikePoint)).collect(Collectors.toList());
    }
    else if (orderByColumn.equals("goodlikePoint") && orderBy.equals("idDesc")) {
      return sortedArticles.stream().sorted(Comparator.comparing(Article::getGoodlikePoint).reversed()).collect(Collectors.toList());
    }
    else if (orderByColumn.equals("dislikePoint") && orderBy.equals("idAsc")) {
      return sortedArticles.stream().sorted(Comparator.comparing(Article::getDislikePoint)).collect(Collectors.toList());
    }
    else if (orderByColumn.equals("dislikePoint") && orderBy.equals("idDesc")) {
      return sortedArticles.stream().sorted(Comparator.comparing(Article::getDislikePoint).reversed()).collect(Collectors.toList());
    }

    return sortedArticles;
  }

  public List<Article> getArticles(String orderByColumn, String orderBy, int boardId, String searchKeyword, String searchKeywordTypeCode,
                                   int limitStart, int limitCount) {

    List<Article> sortedArticles = getSortedArticles(orderByColumn, orderBy);

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

  public int write(int boardId, int loginedMemberId, String title, String body, String keywordsStr, int hitCount) {
    int id = lastId + 1;
    String regDate = Util.getNowDateStr();
    String updateDate = regDate;
    Article article = new Article(id, regDate, updateDate, boardId, loginedMemberId, title, body, keywordsStr, hitCount, 0, 0);
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

  public void modify(int id, String title, String body, String keywordsStr) {
    Article article = getArticleById(id);

    article.setTitle(title);
    article.setBody(body);
    article.setKeywordStr(keywordsStr);
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
    Article article = getArticleById(id);
    article.setHitCount(article.getHitCount() + 1);
  }

  public void increaseLikePoint(int id) {
    Article article = getArticleById(id);
    article.setGoodlikePoint(article.getGoodlikePoint() + 1);
  }

  public void decreaseLikePoint(int id) {
    Article article = getArticleById(id);
    article.setGoodlikePoint(article.getGoodlikePoint() - 1);
  }

  public void increaseDislikePoint(int id) {
    Article article = getArticleById(id);
    article.setDislikePoint(article.getDislikePoint() + 1);
  }

  public void decreaseDislikePoint(int id) {
    Article article = getArticleById(id);
    article.setDislikePoint(article.getDislikePoint() - 1);
  }

  public void updateKeywordsStrAsync(int id, String keywordsStr) {
    Article article = getArticleById(id);

    article.setKeywordStr(keywordsStr);
  }
}
