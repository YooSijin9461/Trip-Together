package com.ssafy.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Board;
import com.ssafy.db.entity.Comments;
import com.ssafy.db.entity.Notice;
import com.ssafy.db.entity.QBoard;
import com.ssafy.db.entity.QComments;
import com.ssafy.db.entity.QNotice;
import com.ssafy.db.entity.User;

/**
 * 공지사항 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class CommentRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QComments qComment = QComments.comments;

    public Optional<Comments> findByCommentNo(int commentNo) {
    	Comments comment = jpaQueryFactory.select(qComment).from(qComment)
    		   		.where(qComment.commentNo.eq(commentNo)).fetchOne();
       if(comment == null) return Optional.empty();
       return Optional.ofNullable(comment);
    }
    
    public Optional<List<Comments>> findByBoardNo(int boardNo){
    	List<Comments> comments = jpaQueryFactory.select(qComment).from(qComment)
							.where(qComment.boardNo.eq(boardNo))
							.fetch();
		if(comments == null) return Optional.empty();
		return Optional.ofNullable(comments);
    }

	public Optional<List<Comments>> findAllByUserId(String userId) {
		List<Comments> comments = jpaQueryFactory.select(qComment).from(qComment)
								.where(qComment.userId.eq(userId))
								.fetch();
		if(comments == null) return Optional.empty();
		return Optional.ofNullable(comments);
	}
}
