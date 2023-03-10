package com.sbs.exam.board.service;

import com.sbs.exam.board.container.Container;
import com.sbs.exam.board.repository.ArticleRepository;
import com.sbs.exam.board.util.Util;
import com.sbs.exam.board.vo.Article;
import com.sbs.exam.board.vo.Member;

import java.util.List;

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
    for(int i = 0; i < 100; i++) {
      String title = "제목" + (i + 1);
      String body = "내용" + (i + 1);
      int id = write(i % 2 + 1, i % 2 + 1, title, body, Util.getRandomInt(1, 100));
      Article article = getArticleById(id);

      makeArticleEtcTestData(article);
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

  public int write(int boardId, int loginedMemberId, String title, String body, int hitCount) {
    return articleRepository.write(boardId, loginedMemberId, title, body, hitCount);
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
    articleRepository.modify(id, title, body);
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
