package com.sbs.exam.board.interceptor;

import com.sbs.exam.board.vo.Rq;

public interface Interceptor {
  boolean run(Rq rq);
}
