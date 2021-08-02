package com.ssafy.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QConferenceRoom;
import com.ssafy.db.entity.QUser;
import com.ssafy.db.entity.User;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class UserRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QUser qUser = QUser.user;
    QConferenceRoom qRoom = QConferenceRoom.conferenceRoom;

    public Optional<User> findUserByUserId(String userId) {
        User user = jpaQueryFactory.select(qUser).from(qUser)
                .where(qUser.userId.eq(userId)).fetchOne();
        if(user == null) return Optional.empty();
        return Optional.ofNullable(user);
    }
    
    public Optional<List<User>> findByConferenceRoomNo(int conferenceNo){
    	List<User> users = jpaQueryFactory.select(qUser).from(qUser)
    					.join(qRoom).on(qUser.conferenceRoomNo.eq(qRoom.conferenceNo))
    					.where(qRoom.conferenceNo.eq(conferenceNo))
    					.fetch();
    	if(users == null) return Optional.empty();
    	return Optional.ofNullable(users);
    }
}
