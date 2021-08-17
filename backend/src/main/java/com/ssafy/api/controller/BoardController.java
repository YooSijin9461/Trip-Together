package com.ssafy.api.controller;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.api.request.BoardModifyPostReq;
import com.ssafy.api.request.BoardRegisterPostReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.service.BoardService;
import com.ssafy.db.entity.Board;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 게시판 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "게시판 API", tags = {"Board"})
@RestController
@RequestMapping("/api/v1/boards")
@CrossOrigin("*")
public class BoardController {
	@Autowired
	BoardService boardService;
	
	
	@PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	@ResponseBody
	@ApiOperation(value = "게시글 등록", notes = "게시글을 작성한다.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<Board> registerBoard(
			@RequestPart(value="file", required = false) MultipartFile file,
//			@RequestParam(required = true) String boardTitle,
//			@RequestParam(required = true) String boardContent
			@RequestBody
			@ApiParam(value="게시글 등록 정보", required = true) BoardRegisterPostReq registerInfo) throws IllegalStateException, IOException{
//		BoardRegisterPostReq registerInfo = new BoardRegisterPostReq();
		if(file != null && file.getSize() > 0) {
			// 이미지 저장 경로
//			String basePath = "/var/www/html/boards/upload";
			String basePath = "C:\\ssafy\\pjt3\\backend\\src\\main\\resources\\dist\\boards\\upload"; // 로컬

			String filePath = basePath + "/" + file.getOriginalFilename();

			File dest = new File(filePath);

			// 파일 업로드
			file.transferTo(dest);
			
			// 현재 날짜 구하기
			Calendar cal = Calendar.getInstance();
			int date = cal.get ( cal.DATE ) ;

			registerInfo.setBoardImg(date + "_" + file.getOriginalFilename());
			
		}
		Board board = boardService.createBoard(registerInfo);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setAccept(Collections.singletonList(MediaType.MULTIPART_FORM_DATA));
		return new ResponseEntity<>(board, responseHeaders, HttpStatus.OK);
		
	}
	
	@GetMapping()
	@ApiOperation(value = "게시글 목록", notes = "게시글 목록을 List로 반환")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<Page<Board>> selectBoard(@PageableDefault(size = 999, sort = "boardNo", direction = Sort.Direction.DESC) Pageable pageable){
		return new ResponseEntity<>(boardService.selectBoard(pageable), HttpStatus.OK);
	}
	
	@GetMapping("/search")
	@ApiOperation(value = "게시글 검색", notes = "키워드로 검색한 게시글 목록을 List로 반환")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<List<Board>> selectBoard(@RequestParam(required = false) String boardTitle,
												   @RequestParam(required = false) String boardContent,
												   @RequestParam(required = false) String userId,
												   @PageableDefault(size = 999, sort = "boardNo", direction = Sort.Direction.DESC) Pageable pageable){
		if(boardTitle != null)
			return new ResponseEntity<>(boardService.searchTitle(boardTitle, pageable), HttpStatus.OK);
		else if(boardContent != null)
			return new ResponseEntity<>(boardService.searchContent(boardContent, pageable), HttpStatus.OK);
		return new ResponseEntity<>(boardService.searchId(userId, pageable), HttpStatus.OK);
// 		return new ResponseEntity<>(boardService.selectBoard(pageable), HttpStatus.OK);
//		if(boardTitle != null)
//			return new ResponseEntity<>(boardRepository.findAll(BoardSpec.boardTitleLike(boardTitle)), HttpStatus.OK);
//		else if(boardContent != null)
//			return new ResponseEntity<>(boardRepository.findAll(BoardSpec.boardContentLike(boardContent)), HttpStatus.OK);
//		else if(userId != null)
//			return new ResponseEntity<>(boardRepository.findAll(BoardSpec.userIdLike(userId)), HttpStatus.OK);
//		return new ResponseEntity<>(boardService.selectBoard(), HttpStatus.OK);
	}
	
	@GetMapping("/{boardNo}")
	@ApiOperation(value = "게시글 상세 조회", notes = "<strong>게시글 번호</strong>를 통해 게시글 상세정보 조회")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<Board> getBoardByBoardeNo(@PathVariable int boardNo){
		return new ResponseEntity<>(boardService.getInfoByBoardNo(boardNo), HttpStatus.OK);
	}
	
	@GetMapping("user/{userId}")
	@ApiOperation(value = "게시글 목록", notes = "사용자가 등록한 게시글 목록 반환")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<List<Board>> getBoardByUserId(@PathVariable String userId){
		return new ResponseEntity<>(boardService.selectByUserId(userId), HttpStatus.OK);
	}
	
	@PatchMapping("/{boardNo}")
	@ApiOperation(value = "게시글 수정")
	@ApiResponses({
	        @ApiResponse(code = 200, message = "성공"),
	        @ApiResponse(code = 404, message = "게시글 없음"),
	        @ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<Board> modifyBoard(@PathVariable int boardNo, @RequestBody BoardModifyPostReq update){
		Board board = boardService.getInfoByBoardNo(boardNo);
		board = boardService.modifyBoard(update, boardNo);
		
		return new ResponseEntity<>(board, HttpStatus.OK);
	}
	
	@DeleteMapping("/{boardNo}")
	@ApiOperation(value = "게시글 삭제")
	@ApiResponses({
	        @ApiResponse(code = 200, message = "성공"),
	        @ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<String> deleteBoard(@PathVariable int boardNo){
		Board board = boardService.getInfoByBoardNo(boardNo);
		if(board != null) {
			boardService.deleteBoard(boardNo);
			return new ResponseEntity<>("success", HttpStatus.OK);
		}
		else
			return new ResponseEntity<>("fail", HttpStatus.OK);
	}
	
}
