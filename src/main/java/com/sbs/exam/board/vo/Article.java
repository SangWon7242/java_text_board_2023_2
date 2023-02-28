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
  private int boardId;
  private String title;
  private String body;
}
