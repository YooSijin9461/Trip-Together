package com.ssafy.db.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.ConferenceRoom;
import com.ssafy.db.entity.QConferenceRoom;

/**
 * ConferenceRoom 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class RoomRepositorySupport {
	@Autowired
    private JPAQueryFactory jpaQueryFactory;
	QConferenceRoom qRoom = QConferenceRoom.conferenceRoom;
	
	public Optional<ConferenceRoom> findByConferenceNo(int conferenceNo){
		ConferenceRoom room = jpaQueryFactory.select(qRoom).from(qRoom)
				.where(qRoom.conferenceNo.eq(conferenceNo)).fetchOne();
		if(room == null) return Optional.empty();
		return Optional.ofNullable(room);
	}
}
