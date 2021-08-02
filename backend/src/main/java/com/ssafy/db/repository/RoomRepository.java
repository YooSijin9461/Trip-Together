package com.ssafy.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ssafy.db.entity.Board;
import com.ssafy.db.entity.ConferenceRoom;
import com.ssafy.db.entity.User;

/**
 * ConferenceRoom 모델 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
@Repository
public interface RoomRepository extends JpaRepository<ConferenceRoom, Integer>, JpaSpecificationExecutor<ConferenceRoom> {
	Optional<ConferenceRoom> findByConferenceNo(int conferenceNo);
}
