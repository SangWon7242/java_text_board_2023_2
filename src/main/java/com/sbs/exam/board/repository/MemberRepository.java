package com.sbs.exam.board.repository;

import com.sbs.exam.board.util.Util;
import com.sbs.exam.board.vo.Article;
import com.sbs.exam.board.vo.Member;

import java.util.ArrayList;
import java.util.List;

public class MemberRepository {
  private List<Member> members;
  private int lastId;

  public void init() {
    lastId = 0;
    members = new ArrayList<>();
  }
  public int join(String loginId, String loginPw) {
    int id = lastId + 1;
    String regDate = Util.getNowDateStr();
    String updateDate = regDate;
    Member member = new Member(id, regDate, updateDate, loginId, loginPw);
    members.add(member);
    lastId = id;

    return id;
  }

  public Member getMemberByLoginId(String loginId) {
    for(Member member : members) {
      if(member.getLoginId().equals(loginId)) {
        return member;
      }
    }

    return null;
  }

  public Member getMemberById(int id) {
    for(Member member : members) {
      if(member.getId() == id) {
        return member;
      }
    }
    return null;
  }

  public String getWriteNameByBoardId(int memberId) {
    for(Member member : members) {
      if(member.getId() == memberId) {
        return member.getLoginId();
      }
    }
    return "";
  }

  public List<Member> getMembers() {
    return members;
  }
}
