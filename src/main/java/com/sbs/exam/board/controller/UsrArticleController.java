package com.sbs.exam.board.controller;

import com.sbs.exam.board.service.ArticleService;
import com.sbs.exam.board.service.BoardService;
import com.sbs.exam.board.vo.Article;
import com.sbs.exam.board.container.Container;
import com.sbs.exam.board.vo.Board;
import com.sbs.exam.board.vo.Rq;
import com.sbs.exam.board.util.Util;

import java.util.ArrayList;
import java.util.List;

public class UsrArticleController {
  private ArticleService articleService;
  private BoardService boardService;
  public UsrArticleController() {
    articleService = Container.getArticleService();
    boardService = Container.getBoardService();

    articleService.makeTestData();
  }

  public void actionDelete(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if(id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    Article foundArticle = articleService.getArticleById(id);

    if (foundArticle == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    articleService.deleteArticleById(id);

    System.out.printf("%d번 게시물을 삭제하였습니다.\n", foundArticle.getId());
  }

  public void actionModify(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if(id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    Article article = articleService.getArticleById(id);

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
    int boardId = rq.getIntParam("boardId", 0);

    if(boardId == 0) {
      System.out.println("boardId를 입력해주세요.");
      return;
    }

    Board board = boardService.getBoardById(boardId);

    if(board == null) {
      System.out.println("존재하지 않는 게시판 번호입니다.");
      return;
    }

    System.out.printf("== %s 게시판 글작성 ==\n", board.getName());

    System.out.printf("제목 : ");
    String title = Container.getSc().nextLine();
    System.out.printf("내용 : ");
    String body = Container.getSc().nextLine();

    int loginedMemberId = rq.getLoginedMemberId();

    int id = articleService.write(1, loginedMemberId, title, body);

    System.out.printf("%d번 게시물이 등록되었습니다.\n", id);
  }

  public void actionDetail(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if(id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    Article foundArticle = articleService.getArticleById(id);

    if (foundArticle == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    System.out.println("- 게시물 상세내용 -");
    System.out.printf("번호 : %s\n", foundArticle.getId());
    System.out.printf("제목 : %s\n", foundArticle.getTitle());
    System.out.printf("내용 : %s\n", foundArticle.getBody());
    System.out.printf("작성자 : %d번 회원\n", foundArticle.getMemberId());
  }

  public void showList(Rq rq) {
    String searchKeyword = rq.getParam("searchKeyword", "");
    String orderBy = rq.getParam("orderBy", "idDesc");

    System.out.println("- 게시물 리스트 -");
    System.out.println("-----------------");
    System.out.println("번호 / 제목");

    List<Article> articles = articleService.getArticles(searchKeyword, orderBy);

    for( Article article : articles) {
      System.out.printf("%d / %s\n", article.getId(), article.getTitle());
    }

    System.out.println("-----------------");
  }

}
