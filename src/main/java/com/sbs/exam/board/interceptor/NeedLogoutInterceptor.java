package com.sbs.exam.board.interceptor;

import com.sbs.exam.board.vo.Rq;

public class NeedLogoutInterceptor implements Interceptor {
  @Override
  public boolean run(Rq rq) {
    if(rq.isLogined() == false) {
      return true;
    }

    switch (rq.getUrlPath()) {
      case "/usr/member/login":
      case "/usr/member/join":
      case "/usr/member/findLoginId":
      case "/usr/member/findLoginPw":
        System.out.println("이미 로그인 되었습니다.");
        return false;
    }

    return true;
  }
}
