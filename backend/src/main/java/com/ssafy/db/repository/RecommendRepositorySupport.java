package com.ssafy.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.ConferenceRoom;
import com.ssafy.db.entity.QConferenceRoom;
import com.ssafy.db.entity.QRecommend;
import com.ssafy.db.entity.Recommend;

/**
 * ConferenceRoom 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class RecommendRepositorySupport {
	@Autowired
    private JPAQueryFactory jpaQueryFactory;
	QRecommend qRecommend = QRecommend.recommend;
	
	public Optional<List<Recommend>> findAllByBoardNo(int boardNo){
		List<Recommend> recommends = jpaQueryFactory.select(qRecommend).from(qRecommend)
				.where(qRecommend.boardNo.eq(boardNo)).fetch();
		if(recommends == null) return Optional.empty();
		return Optional.ofNullable(recommends);
	}
}
