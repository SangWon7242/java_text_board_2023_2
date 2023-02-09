package com.sbs.exam.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int articleLastId = 0;
    Article lastArticle = null;

    List<Article> articles = new ArrayList<>();

    // 테스트 데이터 3개 등록 시작
    articles.add(new Article(1, "제목1", "내용1"));
    articles.add(new Article(2, "제목2", "내용2"));
    articles.add(new Article(3, "제목3", "내용3"));
    // 테스트 데이터 3개 등록 끝


    System.out.println("== 게시판 v 0.1 ==");
    System.out.println("== 프로그램 시작 ==");

    while (true) {
      System.out.printf("명령) ");
      String cmd = sc.nextLine();

      if(cmd.equals("exit")) {
        System.out.println("== 프로그램 종료 ==");
        break;
      }
      else if(cmd.equals("/usr/article/list")) {
        System.out.println("- 게시물 리스트 -");
        System.out.println("-----------------");
        System.out.println("번호 / 제목");
        System.out.println("-----------------");

        for(Article article : articles) {
          System.out.printf("%d / %s\n", article.id,  article.title);
        }
      }
      else if(cmd.equals("/usr/article/detail")) {

        if (lastArticle == null) {
          System.out.println("게시물이 존재하지 않습니다.");
          continue;
        }

        Article article = lastArticle;

        System.out.println("- 게시물 상세내용 -");
        System.out.printf("번호 : %s\n", article.id);
        System.out.printf("제목 : %s\n", article.title);
        System.out.printf("내용 : %s\n", article.body);
      }
      else if(cmd.equals("/usr/article/write")) {
        System.out.println("== 게시물 등록 ==");
        System.out.printf("제목 : ");
        String title = sc.nextLine();
        System.out.printf("내용 : ");
        String body = sc.nextLine();

        int id = articleLastId + 1;
        articleLastId = id;

        Article article = new Article(id, title, body);
        lastArticle = article;
        System.out.println("입력된 게시물 객체 : " + article);

        System.out.printf("%d번 게시물이 등록되었습니다.\n", article.id);
      }
    }
    sc.close();
  }
}

class Article {
  int id;
  String title;
  String body;

  public Article(int id, String title, String body) {
    this.id = id;
    this.title = title;
    this.body = body;
  }

  @Override
  public String toString() {
    return String.format("{id : %d, title : \"%s\",  body : \"%s\"}", id, title, body);
  }
}