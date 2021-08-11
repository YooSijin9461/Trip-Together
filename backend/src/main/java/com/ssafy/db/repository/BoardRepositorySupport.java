package com.ssafy.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Board;
import com.ssafy.db.entity.QBoard;
import com.ssafy.db.entity.QUser;

/**
 * 공지사항 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class BoardRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QBoard qBoard = QBoard.board;
    QUser qUser = QUser.user;

    public Optional<Board> findByBoardNo(int boardNo) {
       Board board = jpaQueryFactory.select(qBoard).from(qBoard)
    		   		.where(qBoard.boardNo.eq(boardNo)).fetchOne();
       if(board == null) return Optional.empty();
       return Optional.ofNullable(board);
    }
    
    public Optional<List<Board>> findAllByUserId(String userId){
    	List<Board> boards = jpaQueryFactory.select(qBoard).from(qBoard)
    						.where(qBoard.userId.eq(userId)).fetch();
    	if(boards == null) return Optional.empty();
    	return Optional.ofNullable(boards);
    						
    }
}
