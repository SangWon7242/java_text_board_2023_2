package com.sbs.exam.board.interceptor;

import com.sbs.exam.board.vo.Rq;

public class NeedLoginInterceptor implements Interceptor {
  @Override
  public boolean run(Rq rq) {
    if(rq.isLogined()) {
      return true;
    }

    switch (rq.getUrlPath()) {
      case "/usr/article/write":
      case "/usr/article/modify":
      case "/usr/article/delete":
      case "/usr/member/logout":
      case "/usr/like/like":
      case "/usr/like/cancelLike":
      case "/usr/like/dislike":
      case "/usr/like/cancelDislike":
        System.out.println("로그인 후 이용해주세요.");
        return false;
    }

    return true;
  }

  public void init() {
  }
}
