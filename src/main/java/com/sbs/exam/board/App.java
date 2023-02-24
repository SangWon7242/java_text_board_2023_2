package com.sbs.exam.board;

import java.util.Scanner;

public class App {

  public void run() {
    Scanner sc = Container.sc;

    System.out.println("== 게시판 v 0.1 ==");
    System.out.println("== 프로그램 시작 ==");

    while (true) {
      Session session = Container.getSession();
      Member loginedMember = (Member) session.getAttribute("loginedMember");

      String promptName = "명령어";

      if(loginedMember != null) {
        promptName = loginedMember.loginId;
      }

      System.out.printf("%s) ", promptName);
      String cmd = Container.sc.nextLine();


      Rq rq = new Rq(cmd);

      if(rq.getUrlPath().equals("exit")) {
        System.out.println("== 프로그램 종료 ==");
        break;
      }
      else if(rq.getUrlPath().equals("/usr/article/list")) {
        Container.usrArticleController.showList(rq);
      }
      else if(rq.getUrlPath().equals("/usr/article/detail")) {
        Container.usrArticleController.actionDetail(rq);
      }
      else if(rq.getUrlPath().equals("/usr/article/write")) {
        Container.usrArticleController.actionWrite(rq);
      }
      else if(rq.getUrlPath().equals("/usr/article/modify")) {
        Container.usrArticleController.actionModify(rq);
      }
      else if(rq.getUrlPath().equals("/usr/article/delete")) {
        Container.usrArticleController.actionDelete(rq);
      }
      else if(rq.getUrlPath().equals("/usr/member/join")) {
        Container.usrMemberController.actionJoin();
      }
      else if(rq.getUrlPath().equals("/usr/member/login")) {
        Container.usrMemberController.actionLogin(rq);
      }
      else if(rq.getUrlPath().equals("/usr/member/logout")) {
        Container.usrMemberController.actionLogout(rq);
      }
    }
    sc.close();
  }
}
