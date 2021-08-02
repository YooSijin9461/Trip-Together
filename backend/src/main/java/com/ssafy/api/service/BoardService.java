package com.ssafy.api.service;

import java.util.List;

import com.ssafy.api.request.BoardModifyPostReq;
import com.ssafy.api.request.BoardRegisterPostReq;
import com.ssafy.api.request.NoticeRegisterPostReq;
import com.ssafy.db.entity.Board;

/**
 *	게시판 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface BoardService {
	// CRUD
	Board createBoard(BoardRegisterPostReq boardRegisterInfo);
	List<Board> selectBoard();
	Board getInfoByBoardNo(int boardNo);
	Board modifyBoard(BoardModifyPostReq boardModifyInfo, int noticeNo);
	void deleteBoard(int boardNo);
}
