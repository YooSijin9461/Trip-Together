package com.ssafy.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.db.entity.Board;
import com.ssafy.db.entity.Comments;

/**
 * 댓글 모델 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comments, Integer> {
    // 아래와 같이, Query Method 인터페이스(반환값, 메소드명, 인자) 정의를 하면 자동으로 Query Method 구현됨.
//    Optional<User> findByUserId(String userId);
//    Optional<List<User>> findByConferenceRoomNo(int conferenceNo);
	  Optional<Comments> findByCommentNo(int commentNo);
	  Optional<List<Comments>> findByBoardNo(int boardNo);
	  Optional<List<Comments>> findAllByUserId(String userId);
}