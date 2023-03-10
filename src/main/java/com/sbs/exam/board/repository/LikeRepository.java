package com.sbs.exam.board.repository;

import com.sbs.exam.board.util.Util;
import com.sbs.exam.board.vo.Article;
import com.sbs.exam.board.vo.Like;

import java.util.ArrayList;
import java.util.List;

public class LikeRepository {
  private List<Like> likes;
  private int lastId;

  public void init() {
    lastId = 0;
    likes = new ArrayList<>();
  }
  public Like getItemByLikePointByRelTypeCodeAndRelIdAndMemberId(String relTypeCode, int relId, int memberId) {
    for(Like like : likes) {
      if(!like.getRelTypeCode().equals(relTypeCode)) {
        continue;
      }

      if(like.getRelId() != relId) {
        continue;
      }

      if(like.getMemberId() != memberId) {
        continue;
      }

      return like;
    }

    return null;
  }

  public int getSummaryLikePointByRelTypeCodeAndRelIdAndMemberId(String relTypeCode, int relId, int memberId) {
    Like like = getItemByLikePointByRelTypeCodeAndRelIdAndMemberId(relTypeCode, relId, memberId);

    if(like != null) {
      return like.getPoint();
    }

    return 0;
  }

  public int like(String relTypeCode, int relId, int memberId) {
    int id = lastId + 1;
    String regDate = Util.getNowDateStr();
    String updateDate = regDate;

    Like like = new Like(id, regDate, updateDate, relTypeCode, relId, memberId, 1);
    likes.add(like);
    lastId = id;

    return id;
  }

  public int dislike(String relTypeCode, int relId, int memberId) {
    int id = lastId + 1;
    String regDate = Util.getNowDateStr();
    String updateDate = regDate;

    Like like = new Like(id, regDate, updateDate, relTypeCode, relId, memberId, -1);
    likes.add(like);
    lastId = id;

    return id;
  }

  public void removeItem(String relTypeCode, int relId, int memberId) {
    Like like = getItemByLikePointByRelTypeCodeAndRelIdAndMemberId(relTypeCode, relId, memberId);

    if(like != null) {
      likes.remove(like);
    }
  }
}
