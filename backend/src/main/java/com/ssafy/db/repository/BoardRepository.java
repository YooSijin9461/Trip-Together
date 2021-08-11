package com.ssafy.db.repository;

import com.ssafy.db.entity.Board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 공지사항 모델 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
@Repository
public interface BoardRepository extends JpaRepository<Board, Integer>, JpaSpecificationExecutor<Board> {
    // 아래와 같이, Query Method 인터페이스(반환값, 메소드명, 인자) 정의를 하면 자동으로 Query Method 구현됨.
//    Optional<User> findByUserId(String userId);
//    Optional<List<User>> findByConferenceRoomNo(int conferenceNo);
	Optional<Board> findByBoardNo(int boardNo);
	Optional<List<Board>> findAllByUserId(String userId);
	Page<Board> findAll(Pageable pageable);
	List<Board> findByBoardTitleContaining(String title, Pageable pageable);
	List<Board> findByBoardContentContaining(String content, Pageable pageable);
	List<Board> findByUserIdContaining(String userId, Pageable pageable);
}