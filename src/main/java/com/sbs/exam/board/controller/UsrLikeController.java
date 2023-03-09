package com.sbs.exam.board.controller;

import com.sbs.exam.board.container.Container;
import com.sbs.exam.board.service.LikeService;
import com.sbs.exam.board.vo.Rq;

import java.util.Map;

public class UsrLikeController {

  private LikeService likeService;

  public UsrLikeController() {
    likeService = Container.getLikeService();
  }
  public void actionLike(Rq rq) {
    String relTypeCode = rq.getParam("relTypeCode", "");
    int relId = rq.getIntParam("relId", 0);

    if(!relTypeCode.equals("article")) {
      System.out.println("relTypeCode 가 올바르지 않습니다.");
      return;
    }

    if(relId == 0) {
      System.out.println("relId를 입력해주세요.");
      return;
    }

    Map<String, Object> rs = likeService.like(relTypeCode, relId, rq.getLoginedMemberId());

    String rsMsg = (String) rs.get("msg");
    System.out.println(rsMsg);
  }

  public void actionCancelLike(Rq rq) {
    String relTypeCode = rq.getParam("relTypeCode", "");
    int relId = rq.getIntParam("relId", 0);

    if(!relTypeCode.equals("article")) {
      System.out.println("relTypeCode 가 올바르지 않습니다.");
      return;
    }

    if(relId == 0) {
      System.out.println("relId를 입력해주세요.");
      return;
    }

    Map<String, Object> rs = likeService.cancelLike(relTypeCode, relId, rq.getLoginedMemberId());

    String rsMsg = (String) rs.get("msg");
    System.out.println(rsMsg);
  }

  public void actionDisLike(Rq rq) {
    String relTypeCode = rq.getParam("relTypeCode", "");
    int relId = rq.getIntParam("relId", 0);

    if(!relTypeCode.equals("article")) {
      System.out.println("relTypeCode 가 올바르지 않습니다.");
      return;
    }

    if(relId == 0) {
      System.out.println("relId를 입력해주세요.");
      return;
    }

    Map<String, Object> rs = likeService.dislike(relTypeCode, relId, rq.getLoginedMemberId());

    String rsMsg = (String) rs.get("msg");
    System.out.println(rsMsg);
  }

  public void actionCancelDislike(Rq rq) {
    String relTypeCode = rq.getParam("relTypeCode", "");
    int relId = rq.getIntParam("relId", 0);

    if(!relTypeCode.equals("article")) {
      System.out.println("relTypeCode 가 올바르지 않습니다.");
      return;
    }

    if(relId == 0) {
      System.out.println("relId를 입력해주세요.");
      return;
    }

    Map<String, Object> rs = likeService.cancelDislike(relTypeCode, relId, rq.getLoginedMemberId());

    String rsMsg = (String) rs.get("msg");
    System.out.println(rsMsg);
  }
}
