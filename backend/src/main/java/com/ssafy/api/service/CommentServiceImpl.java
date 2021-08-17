package com.ssafy.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.CommentModifyPostReq;
import com.ssafy.api.request.CommentRegisterPostReq;
import com.ssafy.db.entity.Comments;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.CommentRepository;
import com.ssafy.db.repository.CommentRepositorySupport;
import com.ssafy.db.repository.UserRepository;

/**
 * Comment 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CommentRepositorySupport commentRepositorySupport;
	
	@Override
	public Comments createComment(CommentRegisterPostReq commentRegisterInfo) {
		User user = userRepository.findByUserId(commentRegisterInfo.getUserId()).get();
		Comments comment = new Comments();
		comment.setComment(commentRegisterInfo.getComment());
		comment.setBoardNo(commentRegisterInfo.getBoardNo());
		comment.setUserId(commentRegisterInfo.getUserId());
		comment.setUserImg(user.getImg());
		comment.setUserGender(user.getGender());
		return commentRepository.save(comment);
	}

	@Override
	public List<Comments> selectComment(int boardNo) {
		List<Comments> comments = commentRepositorySupport.findByBoardNo(boardNo).get();
		return comments;
	}

	@Override
	public Comments getInfoByCommentNo(int commentNo) {
		Comments comment = commentRepositorySupport.findByCommentNo(commentNo).get();
		return comment;
	}

	@Override
	public Comments modifyComment(CommentModifyPostReq commentModifyInfo, int commentNo) {
		Comments comment = commentRepositorySupport.findByCommentNo(commentNo).get();
		comment.setComment(commentModifyInfo.getComment());
		return commentRepository.save(comment);
	}

	@Override
	public void deleteComment(int commentNo) {
		Comments comment = commentRepositorySupport.findByCommentNo(commentNo).get();
		commentRepository.delete(comment);
	}

	@Override
	public List<Comments> selectCommentByUserId(String userId) {
		List<Comments> comments = commentRepositorySupport.findAllByUserId(userId).get();
		return comments;
	}

}
