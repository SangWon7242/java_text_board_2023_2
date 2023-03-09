package com.sbs.exam.board.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Like {
  private int id;
  private String regDate;
  private String updateDate;
  private String relTypeCode;
  private int relId;
  private int memberId;
  private int point;
}
