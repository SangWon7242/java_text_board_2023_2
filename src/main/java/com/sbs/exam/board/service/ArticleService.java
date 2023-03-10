package com.sbs.exam.board.service;

import com.sbs.exam.board.container.Container;
import com.sbs.exam.board.repository.ArticleRepository;
import com.sbs.exam.board.util.Util;
import com.sbs.exam.board.vo.Article;
import com.sbs.exam.board.vo.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleService {
  private ArticleRepository articleRepository;
  private MemberService memberService;
  private LikeService likeService;

  public void init() {
    articleRepository = Container.getArticleRepository();
    memberService = Container.getMemberService();
    likeService = Container.getLikeService();
  }

  public void makeTestData() {

    List<Map<String, Object>> testData = new ArrayList<>();
    testData.add(Util.mapOf("title", "안녕하세요. 저는 홍길동 입니다.", "body", "저는 애플제품을 좋아합니다. 오랜 아이폰 유저인 저는..."));
    testData.add(Util.mapOf("title", "안녕하세요. 저는 홍길순 입니다.", "body", "저는 백엔드 웹 개발자로 5년 동안 근무하고 있는 개발자입니다. 저는 JAVA를 참 좋아합니다."));;

    int i = 0;
    for(Map<String, Object> testDataRow : testData ) {
      String title = (String) testDataRow.get("title");
      String body = (String) testDataRow.get("body");

      int id = write(i % 2 + 1, i % 2 + 1, title, body, Util.getRandomInt(1, 100));
      Article article = getArticleById(id);

      makeArticleEtcTestData(article);
      i++;
    }
  }

  private void makeArticleEtcTestData(Article article) {
    List<Member> members = memberService.getMembers();

    for(Member member : members) {
      int no = Util.getRandomInt(1, 3);

      if(no == 1) {
        likeService.goodlike("article", article.getId(), member.getId());
      } else if(no == 2) {
        likeService.dislike("article", article.getId(), member.getId());
      }
    }
  }

  private int write(int boardId, int loginedMemberId, String title, String body, int hitCount) {
    int id = articleRepository.write(boardId, loginedMemberId, title, body, "", hitCount);

    updateKeywordsStrAsync(id);

    return id;
  }

  private void updateKeywordsStrAsync(int id) {
    new Thread(() -> {
      Article article = getArticleById(id);
      String keywordsStr = Util.getKeywordsStrFormStr(article.getBody());
      articleRepository.updateKeywordsStrAsync(id, keywordsStr);
    }).start();
  }

  public int write(int boardId, int loginedMemberId, String title, String body) {
    return write(boardId, loginedMemberId, title, body, 0);
  }

  public List<Article> getArticles(String orderByColumn, String orderBy, int boardId, String searchKeyword, String searchKeywordTypeCode, int page, int pageItemCount) {
    int limitStart = (page - 1) * pageItemCount;
    int limitCount = pageItemCount;
    return articleRepository.getArticles(orderByColumn, orderBy, boardId, searchKeyword, searchKeywordTypeCode, limitStart, limitCount);
  }

  public Article getArticleById(int id) {
    return articleRepository.getArticleById(id);
  }

  public void deleteArticleById(int id) {
    articleRepository.deleteArticleById(id);
  }

  public void modify(int id, String title, String body) {
    String keywordsStr = Util.getKeywordsStrFormStr(body);
    articleRepository.modify(id, title, body, keywordsStr);
  }

  public int getTotalItemsCount(int boardId, String searchKeywordTypeCode, String searchKeyword) {
    return articleRepository.getTotalItemsCount(boardId, searchKeywordTypeCode, searchKeyword);
  }

  public void increaseHitCount(int id) {
    articleRepository.increaseHitCount(id);
  }

  public void increaseGoodlikePoint(int id) {
    articleRepository.increaseLikePoint(id);
  }

  public void decreaseGoodlikePoint(int id) {
    articleRepository.decreaseLikePoint(id);
  }

  public void increaseDislikePoint(int id) {
    articleRepository.increaseDislikePoint(id);
  }

  public void decreaseDislikePoint(int id) {
    articleRepository.decreaseDislikePoint(id);
  }
}
