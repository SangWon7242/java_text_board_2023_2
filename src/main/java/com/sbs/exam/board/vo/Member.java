package com.sbs.exam.board.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Member {
  private int id;
  private String regDate;
  private String updateDate;
  private String loginId;
  private String loginPw;
}
