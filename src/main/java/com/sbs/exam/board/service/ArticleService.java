package com.sbs.exam.board.service;

import com.sbs.exam.board.container.Container;
import com.sbs.exam.board.repository.ArticleRepository;
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
      write(1, 1, title, body);
    }
  }

  public int write(int boardId, int loginedMemberId, String title, String body) {
    return articleRepository.write(boardId, loginedMemberId, title, body);
  }

  public List<Article> getArticles(String searchKeyword, String orderBy) {
    return articleRepository.getArticles(searchKeyword, orderBy);
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
}
