package com.ssafy.api.service;

import java.util.List;

import com.ssafy.api.request.RecommendRegisterPostReq;
import com.ssafy.db.entity.Recommend;

public interface RecommendService {
	Recommend createRecommend(RecommendRegisterPostReq recommendRegisterInfo);
	Recommend getInfoByUserIdAndBoardNo(String userId, int boardNo);
	Recommend modifyLikeCheckByBoard(String userId, int boardNo);
	Recommend modifyHateCheckByBoard(String userId, int boardNo);
	List<Recommend> selectRecommendByBoardNo(int boardNo);
}
