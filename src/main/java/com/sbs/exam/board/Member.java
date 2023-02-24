package com.sbs.exam.board;

public class Member {
  public int id;
  public String loginId;
  public String loginPw;

  public Member(int id, String loginId, String loginPw) {
    this.id = id;
    this.loginId = loginId;
    this.loginPw = loginPw;
  }

  @Override
  public String toString() {
    return "Member{" +
        "id=" + id +
        ", loginId='" + loginId + '\'' +
        ", loginPw='" + loginPw + '\'' +
        '}';
  }
}
