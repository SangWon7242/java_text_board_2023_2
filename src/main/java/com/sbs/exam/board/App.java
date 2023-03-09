package com.sbs.exam.board;

import com.sbs.exam.board.container.Container;
import com.sbs.exam.board.interceptor.Interceptor;
import com.sbs.exam.board.session.Session;
import com.sbs.exam.board.vo.Member;
import com.sbs.exam.board.vo.Rq;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
  private void forTestLoginByMemberId(int id) {
    Member member = Container.getMemberService().getMemberById(id);
    new Rq().login(member);
  }

  public void run() {
    Scanner sc = Container.getSc();

    System.out.println("== 게시판 v 0.1 ==");
    System.out.println("== 프로그램 시작 ==");

    forTestLoginByMemberId(1);

    while (true) {
      Rq rq = new Rq();
      String promptName = "명령어";

      if (rq.isLogined()) {
        Member loginedMember = rq.getLoginedMember();
        promptName = loginedMember.getLoginId();
      }

      System.out.printf("%s) ", promptName);
      String cmd = Container.getSc().nextLine();

      rq.setCommand(cmd);

      if(runInterceptors(rq) == false) {
        continue;
      }

      if (rq.getUrlPath().equals("exit")) {
        System.out.println("== 프로그램 종료 ==");
        break;
      } else if (rq.getUrlPath().equals("/usr/article/list")) {
        Container.getUsrArticleController().showList(rq);
      } else if (rq.getUrlPath().equals("/usr/article/detail")) {
        Container.getUsrArticleController().actionDetail(rq);
      } else if (rq.getUrlPath().equals("/usr/article/write")) {
        Container.getUsrArticleController().actionWrite(rq);
      } else if (rq.getUrlPath().equals("/usr/article/modify")) {
        Container.getUsrArticleController().actionModify(rq);
      } else if (rq.getUrlPath().equals("/usr/article/delete")) {
        Container.getUsrArticleController().actionDelete(rq);
      } else if (rq.getUrlPath().equals("/usr/member/join")) {
        Container.getUsrMemberController().actionJoin();
      } else if (rq.getUrlPath().equals("/usr/member/login")) {
        Container.getUsrMemberController().actionLogin(rq);
      } else if (rq.getUrlPath().equals("/usr/member/logout")) {
        Container.getUsrMemberController().actionLogout(rq);
      } else if (rq.getUrlPath().equals("/usr/like/like")) {
        Container.getUsrLikeController().actionLike(rq);
      } else if (rq.getUrlPath().equals("/usr/like/cancelLike")) {
        Container.getUsrLikeController().actionCancelLike(rq);
      } else if (rq.getUrlPath().equals("/usr/like/dislike")) {
        Container.getUsrLikeController().actionDisLike(rq);
      } else if (rq.getUrlPath().equals("/usr/like/cancelDislike")) {
        Container.getUsrLikeController().actionCancelDislike(rq);
      }

    }
    sc.close();
  }



  private boolean runInterceptors(Rq rq) {
    List<Interceptor> interceptors = new ArrayList<>();

    interceptors.add(Container.getNeedLoginInterceptor());
    interceptors.add(Container.getNeedLogoutInterceptor());

    for(Interceptor interceptor : interceptors) {
      if(interceptor.run(rq) == false) {
        return false;
      }
    }

    return true;
  }
}
