package com.sbs.exam.board;

import java.util.*;

public class Main {
  static int articleLastId = 0;
  static List<Article> articles = new ArrayList<>();
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    makeTestData();

    if(articles.size() > 0) {
      articleLastId =  articles.get(articles.size() - 1).id;
    }

    System.out.println("== 게시판 v 0.1 ==");
    System.out.println("== 프로그램 시작 ==");

    while (true) {
      System.out.printf("명령) ");
      String cmd = sc.nextLine();

      Rq rq = new Rq(cmd);
      Map<String, String> params = rq.getParams();

      if(rq.getUrlPath().equals("exit")) {
        System.out.println("== 프로그램 종료 ==");
        break;
      }
      else if(rq.getUrlPath().equals("/usr/article/list")) {
        actionUsrArticleList(rq);
      }
      else if(rq.getUrlPath().equals("/usr/article/detail")) {
        actionUsrArticleDetail(rq);
      }
      else if(rq.getUrlPath().equals("/usr/article/write")) {
        actionUsrArticleWrite(rq, sc);
      }
      else if(rq.getUrlPath().equals("/usr/article/modify")) {
        actionUsrArticleModify(rq, sc);
      }
      else if(rq.getUrlPath().equals("/usr/article/delete")) {
        actionUsrArticleDelete(rq, sc);
      }
    }
    sc.close();
  }

  private static void makeTestData() {
    for(int i = 0; i < 100; i++) {
      int id = i + 1;
      articles.add(new Article(id, "제목" + id, "내용" + id));
    }
  }
  private static void actionUsrArticleDelete(Rq rq, Scanner sc) {
    Map<String, String> params = rq.getParams();

    if(params.containsKey("id") == false) {
      System.out.println("id를 입력해주세요.");
      return;
    }

    int id = 0;

    try {
      id = Integer.parseInt(params.get("id"));
    }
    catch ( NumberFormatException e) {
      System.out.println("id를 정수 형태로 입력해주세요.");
      return;
    }

    Article foundArticle = null;

    for(Article article : articles) {
      if(article.id == id) {
        foundArticle = article;
        break;
      }
      else if(article.id != id) {
        System.out.println("해당 게시물은 존재하지 않습니다.");
        return;
      }
    }

    articles.remove(foundArticle);

    System.out.printf("%d번 게시물을 삭제하였습니다.\n", foundArticle.id);
  }

  private static void actionUsrArticleModify(Rq rq, Scanner sc) {
    Map<String, String> params = rq.getParams();

    if(params.containsKey("id") == false) {
      System.out.println("id를 입력해주세요.");
      return;
    }

    int id = 0;

    try {
      id = Integer.parseInt(params.get("id"));
    }
    catch ( NumberFormatException e) {
      System.out.println("id를 정수 형태로 입력해주세요.");
      return;
    }

    if (id > articles.size()) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }

    Article article = articles.get(id - 1);

    System.out.printf("새 제목 : ");
    article.title = sc.nextLine();
    System.out.printf("새 내용 : ");
    article.body = sc.nextLine();

    System.out.printf("%d번 게시물을 수정하였습니다.\n", article.id);
  }

  private static void actionUsrArticleWrite(Rq rq, Scanner sc) {
    System.out.println("== 게시물 등록 ==");
    System.out.printf("제목 : ");
    String title = sc.nextLine();
    System.out.printf("내용 : ");
    String body = sc.nextLine();

    int id = articleLastId + 1;
    articleLastId = id;

    Article article = new Article(id, title, body);

    articles.add(article);
    System.out.println("입력된 게시물 객체 : " + article);

    System.out.printf("%d번 게시물이 등록되었습니다.\n", article.id);
  }

  private static void actionUsrArticleDetail(Rq rq) {
    Map<String, String> params = rq.getParams();

    if(params.containsKey("id") == false) {
      System.out.println("id를 입력해주세요.");
      return;
    }

    int id = 0;

    try {
      id = Integer.parseInt(params.get("id"));
    }
    catch ( NumberFormatException e) {
      System.out.println("id를 정수 형태로 입력해주세요.");
      return;
    }

    Article foundArticle = null;

    for(Article article : articles) {
      if(article.id == id) {
        foundArticle = article;
        break;
      }
      else if(article.id != id) {
        System.out.println("해당 게시물은 존재하지 않습니다.");
        return;
      }
    }

    System.out.println("- 게시물 상세내용 -");
    System.out.printf("번호 : %s\n", foundArticle.id);
    System.out.printf("제목 : %s\n", foundArticle.title);
    System.out.printf("내용 : %s\n", foundArticle.body);
  }

  private static void actionUsrArticleList(Rq rq) {
    System.out.println("- 게시물 리스트 -");
    System.out.println("-----------------");
    System.out.println("번호 / 제목");

    Map<String, String> params = rq.getParams();

    // 검색시작
    List<Article> filteredArticles = articles;

    if(params.containsKey("searchKeyword")) {
      String searchKeyword = params.get("searchKeyword");

      filteredArticles = new ArrayList<>();

      for(Article article : articles) {
        boolean matched = article.title.contains(searchKeyword) || article.body.contains(searchKeyword);

        if(matched) {
          filteredArticles.add(article);
        }
      }
    }
    // 검색 끝

    List<Article> sortedArticles = filteredArticles;

    boolean orderByIdDesc = true;
    if(params.containsKey("orderBy") && params.get("orderBy").equals("idAsc")) {
      orderByIdDesc = false;
    }

    if(orderByIdDesc) {
      sortedArticles = Util.reverseList(sortedArticles);
    }

    for(Article article : sortedArticles) {
      System.out.printf("%d / %s\n", article.id,  article.title);
    }

    System.out.println("-----------------");
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


class Rq {
  String url;
  Map<String, String> params;
  String urlPath;

  Rq(String url) {
    this.url = url;
    params = Util.getUrlParamsFromUrl(this.url);
    urlPath = Util.getUrlPathFromUrl(this.url);
  }

  public Map<String, String> getParams() {
    return params;
  }

  public String getUrlPath() {
    return urlPath;
  }
}

class Util {
  static Map<String, String> getUrlParamsFromUrl(String url) {
    Map<String, String> params = new HashMap<>();
    String[] urlBits = url.split("\\?", 2);

    if(urlBits.length == 1) {
      return params;
    }

    for(String bit : urlBits[1].split("&")) {
      String[] bits = bit.split("=", 2);

      if(bits.length == 1) {
        continue;
      }

      params.put(bits[0], bits[1]);
    }

    return params;
  }

  static String getUrlPathFromUrl(String url) {
    return url.split("\\?", 2)[0];
  }

  public static<T> List<T> reverseList(List<T> list) {
    List<T> reverse = new ArrayList<>(list.size());

    for ( int i = list.size() - 1; i >= 0; i-- ) {
      reverse.add(list.get(i));
    }
    return reverse;
  }
}