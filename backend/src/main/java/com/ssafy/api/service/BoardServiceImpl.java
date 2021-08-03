package com.ssafy.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.BoardModifyPostReq;
import com.ssafy.api.request.BoardRegisterPostReq;
import com.ssafy.db.entity.Board;
import com.ssafy.db.repository.BoardRepository;
import com.ssafy.db.repository.BoardRepositorySupport;

/**
 * Board 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("boardService")
public class BoardServiceImpl implements BoardService {
	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	BoardRepositorySupport boardRepositorySupport;
	
	@Override
	public Board createBoard(BoardRegisterPostReq boardRegisterInfo) {
		Board board = new Board();
		board.setBoardTitle(boardRegisterInfo.getBoardTitle());
		board.setBoardContent(boardRegisterInfo.getBoardContent());
		board.setBoardRate(boardRegisterInfo.getBoardRate());
		board.setUserId(boardRegisterInfo.getUserId());
		return boardRepository.save(board);
	}

	@Override
	public Page<Board> selectBoard(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}
	
	@Override
	public List<Board> searchTitle(String title, Pageable pageable) {
		return boardRepository.findByBoardTitleContaining(title, pageable);
	}

	@Override
	public List<Board> searchContent(String content, Pageable pageable) {
		return boardRepository.findByBoardContentContaining(content, pageable);
	}

	@Override
	public List<Board> searchId(String userId, Pageable pageable) {
		return boardRepository.findByUserIdContaining(userId, pageable);
	}

	@Override
	public Board getInfoByBoardNo(int boardNo) {
		Board board = boardRepositorySupport.findByBoardNo(boardNo).get();
		return board;
	}

	@Override
	public Board modifyBoard(BoardModifyPostReq boardModifyInfo, int boardNo) {
		Board board = boardRepositorySupport.findByBoardNo(boardNo).get();
		board.setBoardTitle(boardModifyInfo.getBoardTitle());
		board.setBoardContent(boardModifyInfo.getBoardContent());
		board.setBoardRate(boardModifyInfo.getBoardRate());
		return boardRepository.save(board);
	}

	@Override
	public void deleteBoard(int boardNo) {
		Board board = boardRepositorySupport.findByBoardNo(boardNo).get();
		boardRepository.delete(board);
	}

}
