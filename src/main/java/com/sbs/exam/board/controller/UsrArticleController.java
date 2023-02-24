package com.sbs.exam.board.controller;

import com.sbs.exam.board.vo.Article;
import com.sbs.exam.board.container.Container;
import com.sbs.exam.board.vo.Rq;
import com.sbs.exam.board.util.Util;

import java.util.ArrayList;
import java.util.List;

public class UsrArticleController {
  int articleLastId;
  List<Article> articles;

  public UsrArticleController() {
    articleLastId = 0;
    articles = new ArrayList<>();

    makeTestData();

    if(articles.size() > 0) {
      articleLastId =  articles.get(articles.size() - 1).getId();
    }
  }

  public void makeTestData() {
    for(int i = 0; i < 100; i++) {
      int id = i + 1;
      articles.add(new Article(id, "제목" + id, "내용" + id));
    }
  }

  public void actionDelete(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if(id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    if (id > articles.size()) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }

    Article foundArticle = getArticleById(id);

    if (foundArticle == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    articles.remove(foundArticle);

    System.out.printf("%d번 게시물을 삭제하였습니다.\n", foundArticle.getId());
  }

  public void actionModify(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if(id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    if (id > articles.size()) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }

    Article article = getArticleById(id);

    if (article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    System.out.printf("새 제목 : ");
    article.setTitle(Container.getSc().nextLine());
    System.out.printf("새 내용 : ");
    article.setBody(Container.getSc().nextLine());

    System.out.printf("%d번 게시물을 수정하였습니다.\n", article.getId());
  }

  public void actionWrite(Rq rq) {
    System.out.println("== 게시물 등록 ==");
    System.out.printf("제목 : ");
    String title = Container.getSc().nextLine();
    System.out.printf("내용 : ");
    String body = Container.getSc().nextLine();

    int id = articleLastId + 1;
    articleLastId = id;

    Article article = new Article(id, title, body);

    articles.add(article);
    System.out.printf("%d번 게시물이 등록되었습니다.\n", article.getId());
  }

  public void actionDetail(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if(id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    Article foundArticle = getArticleById(id);

    if (foundArticle == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    System.out.println("- 게시물 상세내용 -");
    System.out.printf("번호 : %s\n", foundArticle.getId());
    System.out.printf("제목 : %s\n", foundArticle.getTitle());
    System.out.printf("내용 : %s\n", foundArticle.getBody());
  }

  public void showList(Rq rq) {
    System.out.println("- 게시물 리스트 -");
    System.out.println("-----------------");
    System.out.println("번호 / 제목");

    String searchKeyword = rq.getParam("searchKeyword", "");

    // 검색시작
    List<Article> filteredArticles = articles;

    if(searchKeyword.length() > 0) {
      filteredArticles = new ArrayList<>();

      for(Article article : articles) {
        boolean matched = article.getTitle().contains(searchKeyword) || article.getBody().contains(searchKeyword);

        if(matched) {
          filteredArticles.add(article);
        }
      }
    }
    // 검색 끝

    List<Article> sortedArticles = filteredArticles;

    String orderBy = rq.getParam("orderBy", "idDesc");
    boolean orderByIdDesc = orderBy.equals("idDesc");

    if(orderByIdDesc) {
      sortedArticles = Util.reverseList(sortedArticles);
    }

    for(Article article : sortedArticles) {
      System.out.printf("%d / %s\n", article.getId(),  article.getTitle());
    }

    System.out.println("-----------------");
  }

  private Article getArticleById(int id) {
    for(Article article : articles) {
      if(article.getId() == id) {
        return article;
      }
    }

    return null;
  }
}
