package com.sbs.exam.board.container;

import com.sbs.exam.board.controller.UsrLikeController;
import com.sbs.exam.board.interceptor.NeedLoginInterceptor;
import com.sbs.exam.board.interceptor.NeedLogoutInterceptor;
import com.sbs.exam.board.repository.ArticleRepository;
import com.sbs.exam.board.repository.BoardRepository;
import com.sbs.exam.board.repository.LikeRepository;
import com.sbs.exam.board.repository.MemberRepository;
import com.sbs.exam.board.service.ArticleService;
import com.sbs.exam.board.service.BoardService;
import com.sbs.exam.board.service.LikeService;
import com.sbs.exam.board.service.MemberService;
import com.sbs.exam.board.session.Session;
import com.sbs.exam.board.controller.UsrArticleController;
import com.sbs.exam.board.controller.UsrMemberController;
import lombok.Getter;

import java.util.Scanner;

public class Container {
  @Getter
  private static Scanner sc;
  @Getter
  private static Session session;

  @Getter
  private static MemberRepository memberRepository;
  @Getter
  private static BoardRepository boardRepository;
  @Getter
  private static ArticleRepository articleRepository;
  @Getter
  private static LikeRepository likeRepository;

  @Getter
  private static MemberService memberService;
  @Getter
  private static BoardService boardService;
  @Getter
  private static ArticleService articleService;
  @Getter
  private static LikeService likeService;

  @Getter
  private static NeedLoginInterceptor needLoginInterceptor;
  @Getter
  private static NeedLogoutInterceptor needLogoutInterceptor;

  @Getter
  private static UsrArticleController usrArticleController;
  @Getter
  private static UsrMemberController usrMemberController;
  @Getter
  private static UsrLikeController usrLikeController;

  static {
    sc = new Scanner(System.in);
    session = new Session();

    memberRepository = new MemberRepository();
    boardRepository = new BoardRepository();
    articleRepository = new ArticleRepository();
    likeRepository = new LikeRepository();

    articleService = new ArticleService();
    boardService = new BoardService();
    memberService = new MemberService();
    likeService = new LikeService();

    needLoginInterceptor = new NeedLoginInterceptor();
    needLogoutInterceptor = new NeedLogoutInterceptor();

    usrArticleController = new UsrArticleController();
    usrMemberController = new UsrMemberController();
    usrLikeController = new UsrLikeController();
  }

  public static Session getSession() {
    return session;
  }
}
