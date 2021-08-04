package com.ssafy.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.RoomModifyPostReq;
import com.ssafy.api.request.RoomRegisterPostReq;
import com.ssafy.db.entity.ConferenceRoom;
import com.ssafy.db.repository.RoomRepository;
import com.ssafy.db.repository.RoomRepositorySupport;

/**
 * ConferenceRoom 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("roomService")
public class RoomServiceImpl implements RoomService{
	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	RoomRepositorySupport roomRepositorySupport;
	
	@Override
	public ConferenceRoom createRoom(RoomRegisterPostReq roomRegisterInfo) {
		ConferenceRoom room = new ConferenceRoom();
		room.setConferenceCategory(roomRegisterInfo.getConferenceCategory());
		room.setConferencePassword(roomRegisterInfo.getConferencePassword());
		room.setOwner(roomRegisterInfo.getOwner());
		room.setThumbnailUrl(roomRegisterInfo.getThumbnailUrl());
		room.setTitle(roomRegisterInfo.getTitle());
		room.setDescription(roomRegisterInfo.getDescription());
		room.setActive(roomRegisterInfo.isActive());
		room.setLimitUsersNum(roomRegisterInfo.getLimitUsersNum());
		return roomRepository.save(room);
	}
	
	@Override
	public Page<ConferenceRoom> selectRoom(Pageable pageable) {
		return roomRepository.findAll(pageable);
	}

	@Override
	public Page<ConferenceRoom> selectRoom(String conferenceCategory, Pageable pageable) {
		return roomRepository.findByConferenceCategory(conferenceCategory, pageable);
	}
	
	@Override
	public List<ConferenceRoom> searchTitle(String conferenceCategory, String title, Pageable pageable) {
		return roomRepository.findByTitleContaining(conferenceCategory, title, pageable);
	}

	@Override
	public List<ConferenceRoom> searchOwner(String conferenceCategory, String owner, Pageable pageable) {
		return roomRepository.findByOwnerContaining(conferenceCategory, owner, pageable);
	}
	
	@Override
	public ConferenceRoom getInfo(int confernceNo) {
		// 방 id를 통한 조회가 필요함
		ConferenceRoom room = roomRepositorySupport.findByConferenceNo(confernceNo).get();
		return room;
	}

	@Override
	public ConferenceRoom modifyRoom(RoomModifyPostReq r, int confernceNo) {
		// 뭐를 못바꾸게 하지?(id, owner, start_time, is_active, join_users_num?)
		ConferenceRoom room = roomRepositorySupport.findByConferenceNo(confernceNo).get();
		room.setConferenceCategory(r.getConferenceCategory());
		room.setConferencePassword(r.getConferencePassword());
		room.setThumbnailUrl(r.getThumbnailUrl());
		room.setTitle(r.getTitle());
		room.setDescription(r.getDescription());
		room.setLimitUsersNum(r.getLimitUsersNum());
		return roomRepository.save(room);
	}

	@Override
	public void deleteRoom(int conferenceNo) {
		ConferenceRoom room = roomRepositorySupport.findByConferenceNo(conferenceNo).get();
		roomRepository.delete(room);
	}

}
