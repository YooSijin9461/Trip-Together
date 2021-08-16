package com.ssafy.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.RecommendRegisterPostReq;
import com.ssafy.db.entity.Recommend;
import com.ssafy.db.repository.BoardRepository;
import com.ssafy.db.repository.RecommendRepository;

@Service("recommendService")
public class RecommendServiceImpl implements RecommendService {
	@Autowired
	RecommendRepository recommendRepository;
	
	@Autowired
	BoardRepository boardRepository;
	
	@Override
	public Recommend createRecommend(RecommendRegisterPostReq recommendRegisterInfo) {
		if (recommendRepository.findByUserIdAndBoardNo(recommendRegisterInfo.getUserId(), recommendRegisterInfo.getBoardNo()).isPresent()) {
			return null;
		}
		
		Recommend recommend = new Recommend();
		recommend.setUserId(recommendRegisterInfo.getUserId());
		recommend.setBoardNo(recommendRegisterInfo.getBoardNo());
		return recommendRepository.save(recommend);
	}
	
	@Override
	public Recommend getInfoByUserIdAndBoardNo(String userId, int boardNo) {
		return recommendRepository.findByUserIdAndBoardNo(userId, boardNo).get();
	}

	@Override
	public Recommend modifyLikeCheckByBoard(String userId, int boardNo) {
		Recommend recommend = null;
		if (!recommendRepository.findByUserIdAndBoardNo(userId, boardNo).isPresent()) {
			RecommendRegisterPostReq recommendRegisterInfo = new RecommendRegisterPostReq();
			recommendRegisterInfo.setUserId(userId);
			recommendRegisterInfo.setBoardNo(boardNo);
			recommend = createRecommend(recommendRegisterInfo);
		}
		
		recommend = recommendRepository.findByUserIdAndBoardNo(userId, boardNo).get();
		recommend.setLikeCheck(!recommend.isLikeCheck());
		return recommendRepository.save(recommend);
	}

	@Override
	public Recommend modifyHateCheckByBoard(String userId, int boardNo) {
		Recommend recommend = null;
		if (!recommendRepository.findByUserIdAndBoardNo(userId, boardNo).isPresent()) {
			RecommendRegisterPostReq recommendRegisterInfo = new RecommendRegisterPostReq();
			recommendRegisterInfo.setUserId(userId);
			recommendRegisterInfo.setBoardNo(boardNo);
			recommend = createRecommend(recommendRegisterInfo);
		}
		
		recommend = recommendRepository.findByUserIdAndBoardNo(userId, boardNo).get();
		recommend.setHateCheck(!recommend.isHateCheck());
		return recommendRepository.save(recommend);
	}

}
