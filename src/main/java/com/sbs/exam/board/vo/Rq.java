package com.sbs.exam.board.vo;

import com.sbs.exam.board.container.Container;
import com.sbs.exam.board.session.Session;
import com.sbs.exam.board.util.Util;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
public class Rq {
  String url;
  Map<String, String> params;
  String urlPath;

  public Rq(String url) {
    this.url = url;
    params = Util.getUrlParamsFromUrl(this.url);
    urlPath = Util.getUrlPathFromUrl(this.url);
  }

  public Map<String, String> getParams() {
    return params;
  }

  public int getIntParam(String paramName, int defaultValue) {
    if (params.containsKey(paramName) == false) {
      return defaultValue;
    }

    try {
      return Integer.parseInt(params.get(paramName));
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  public String getParam(String paramName, String defaultValue) {
    if (params.containsKey(paramName) == false) {
      return defaultValue;
    }

    return params.get(paramName);
  }

  public String getUrlPath() {
    return urlPath;
  }


  public void setSessionAttr(String key, Object value) {
    Session session = Container.getSession();
    session.setAttribute(key, value);
  }

  public Object getSessionAttr(String key) {
    Session session = Container.getSession();
    return session.getAttribute(key);
  }

  public void removeSessionAttr(String key) {
    Session session = Container.getSession();
    session.removeAttribute(key);
  }

  public boolean hasSessionAttr(String key) {
    Session session = Container.getSession();
    return session.hasAttribute(key);
  }

  public Member getLoginedMember() {
    return (Member) getSessionAttr("loginedMember");
  }

  public boolean isLogined() {
    return hasSessionAttr("loginedMember");
  }

  public void setCommand(String url) {
    urlPath = Util.getUrlPathFromUrl(url);
    params = Util.getUrlParamsFromUrl(url);
  }

  public void login(Member member) {
    setSessionAttr("loginedMember", member);
  }

  public void logout() {
    removeSessionAttr("loginedMember");
  }

  public int getLoginedMemberId() {
    return getLoginedMember().getId();
  }
}
