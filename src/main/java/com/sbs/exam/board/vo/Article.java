package com.sbs.exam.board.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Article {
  private int id;
  private String regDate;
  private String updateDate;
  private int boardId;
  private int memberId;
  private String title;
  private String body;
  private String keywordStr;
  private int hitCount;
  private int goodlikePoint;
  private int dislikePoint;
}
