package com.ssafy.api.service;

import java.util.List;

import com.ssafy.api.request.UserModifyPostReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.entity.User;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
	User createUser(UserRegisterPostReq userRegisterInfo);
	List<User> selectUser();
	User getUserByUserId(String userId);
	User modifyUser(UserModifyPostReq update, String userId);
	void deleteUser(String userId);
	List<User> getUsersByConferenceNo(int conferenceNo);
	User modifyConferenceRoomNo(String userId, Integer conferenceRoomNo);
	User modifyConferenceRoomNoNull(String userId);
}
