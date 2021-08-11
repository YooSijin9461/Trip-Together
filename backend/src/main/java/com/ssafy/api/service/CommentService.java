package com.ssafy.api.service;

import java.util.List;

import com.ssafy.api.request.CommentModifyPostReq;
import com.ssafy.api.request.CommentRegisterPostReq;
import com.ssafy.db.entity.Comments;

/**
 *	댓글 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface CommentService {
	// CRUD
	Comments createComment(CommentRegisterPostReq commentRegisterInfo);
	List<Comments> selectComment(int boardNo);			// 게시글에 해당하는 댓글 리스트 가져오기
	List<Comments> selectCommentByUserId(String userId);
	Comments getInfoByCommentNo(int commentNo);
	Comments modifyComment(CommentModifyPostReq commentModifyInfo, int commentNo);
	void deleteComment(int commentNo);
}
