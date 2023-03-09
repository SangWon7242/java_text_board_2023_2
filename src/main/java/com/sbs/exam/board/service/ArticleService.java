package com.sbs.exam.board.service;

import com.sbs.exam.board.container.Container;
import com.sbs.exam.board.repository.ArticleRepository;
import com.sbs.exam.board.util.Util;
import com.sbs.exam.board.vo.Article;

import java.util.List;

public class ArticleService {
  private ArticleRepository articleRepository;

  public ArticleService() {
    articleRepository = Container.getArticleRepository();
  }

  public void makeTestData() {
    for(int i = 0; i < 100; i++) {
      String title = "제목" + (i + 1);
      String body = "내용" + (i + 1);
      writeForTestData(i % 2 + 1, i % 2 + 1, title, body, Util.getRandomInt(1, 100));
    }
  }

  public int writeForTestData(int boardId, int loginedMemberId, String title, String body, int hitCount) {
    return articleRepository.write(boardId, loginedMemberId, title, body, hitCount);
  }

  public List<Article> getArticles(String orderByColumn, String orderBy, int boardId, String searchKeyword, String searchKeywordTypeCode, int page, int pageItemCount) {
    int limitStart = (page - 1) * pageItemCount;
    int limitCount = pageItemCount;
    return articleRepository.getArticles(orderByColumn, orderBy, boardId, searchKeyword, searchKeywordTypeCode, limitStart, limitCount);
  }

  public Article getArticleById(int id) {
    return articleRepository.getArticleById(id);
  }

  public void deleteArticleById(int id) {
    articleRepository.deleteArticleById(id);
  }

  public void modify(int id, String title, String body) {
    articleRepository.modify(id, title, body);
  }

  public int getTotalItemsCount(int boardId, String searchKeywordTypeCode, String searchKeyword) {
    return articleRepository.getTotalItemsCount(boardId, searchKeywordTypeCode, searchKeyword);
  }

  public void increaseHitCount(int id) {
    articleRepository.increaseHitCount(id);
  }

  public void increaseLikePoint(int id) {
    articleRepository.increaseLikePoint(id);
  }

  public void decreaseLikePoint(int id) {
    articleRepository.decreaseLikePoint(id);
  }

  public void increaseDislikePoint(int id) {
    articleRepository.increaseDislikePoint(id);
  }

  public void decreaseDislikePoint(int id) {
    articleRepository.decreaseDislikePoint(id);
  }
}
