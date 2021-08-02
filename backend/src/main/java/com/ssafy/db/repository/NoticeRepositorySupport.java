package com.ssafy.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Comments;
import com.ssafy.db.entity.Notice;
import com.ssafy.db.entity.QNotice;
import com.ssafy.db.entity.User;

/**
 * 공지사항 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class NoticeRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QNotice qNotice = QNotice.notice;

    public Optional<Notice> findByNoticeNo(int noticeNo) {
       Notice notice = jpaQueryFactory.select(qNotice).from(qNotice)
    		   		.where(qNotice.noticeNo.eq(noticeNo)).fetchOne();
       if(notice == null) return Optional.empty();
       return Optional.ofNullable(notice);
    }
}
