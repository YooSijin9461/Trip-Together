package com.ssafy.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.api.request.RoomModifyPostReq;
import com.ssafy.api.request.RoomRegisterPostReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.entity.ConferenceRoom;
import com.ssafy.db.entity.User;

/**
 *	미팅방 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface RoomService {
	//List<ConferenceRoom> selectCategory();
	// CRUD + 키워드를 통한 검색(방 이름, 방 번호, 생성자 정도?)
	ConferenceRoom createRoom(RoomRegisterPostReq roomRegisterInfo);			// 방 생성
	Page<ConferenceRoom> selectRoom(Pageable pageable);		// 방 목록
	List<ConferenceRoom> searchTitle(String title, Pageable pageable);// 방 이름으로 검색
	List<ConferenceRoom> searchOwner(String owner, Pageable pageable);// 방 소유자 이름으로 검색
	ConferenceRoom getInfo(int conferenceNo);				// 방 상세정보 조회
	ConferenceRoom modifyRoom(RoomModifyPostReq r, int confernceNo);			// 방 정보 수정
	void deleteRoom(int conferenceNo);// 방 삭제
}
