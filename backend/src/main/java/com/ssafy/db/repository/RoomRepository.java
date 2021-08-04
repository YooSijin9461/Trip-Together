package com.ssafy.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ssafy.db.entity.ConferenceRoom;

/**
 * ConferenceRoom 모델 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
@Repository
public interface RoomRepository extends JpaRepository<ConferenceRoom, Integer>, JpaSpecificationExecutor<ConferenceRoom> {
	Optional<ConferenceRoom> findByConferenceNo(int conferenceNo);
	Page<ConferenceRoom> findAll(Pageable pageable);
	Page<ConferenceRoom> findByConferenceCategory(String conferenceCategory, Pageable pageable);
	List<ConferenceRoom> findByTitleContaining(String title, Pageable pageable);
	List<ConferenceRoom> findByOwnerContaining(String owner, Pageable pageable);
}
