package com.ssafy.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.api.request.RecommendRegisterPostReq;
import com.ssafy.api.service.BoardService;
import com.ssafy.api.service.RecommendService;
import com.ssafy.db.entity.Board;
import com.ssafy.db.entity.Recommend;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 좋아요 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "좋아요 API", tags = {"Recommend"})
@RestController
@RequestMapping("/api/v1/recommend")
@CrossOrigin("*")
public class RecommendController {
	@Autowired
	RecommendService recommendService;
	
	@Autowired
	BoardService boardService;
	
	@PostMapping()
	@ApiOperation(value = "좋아요 정보 등록", notes = "사용자가 좋아요를 누른 정보를 등록한다")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<Recommend> registerRecommend(
			@RequestBody @ApiParam(value="좋아요 등록 정보", required = true) RecommendRegisterPostReq registerInfo){
		Recommend recommend = recommendService.createRecommend(registerInfo);
		return new ResponseEntity<Recommend>(recommend, HttpStatus.OK);
	}
	
	@GetMapping("/info")
	@ApiOperation(value = "추천 정보 조회", notes = "사용자가 해당 게시물에 대한 좋아요, 싫어요 체크 정보를 조회한다")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<Recommend> selectRecommend(
			@RequestParam(required = false) String userId, @RequestParam(required = false) int boardNo) {
		Recommend recommend = recommendService.getInfoByUserIdAndBoardNo(userId, boardNo);
		return new ResponseEntity<Recommend>(recommend, HttpStatus.OK);
	}
	
	@PatchMapping("/like")
	@ApiOperation(value = "좋아요 정보 수정", notes = "사용자가 해당 게시물에 좋아요를 눌렀는지 안눌렀는지 판단할 수 있도록 정보를 수정")
	@ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 404, message = "게시글 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<Recommend> modifyLikeCheck(@RequestParam(required = false) String userId, @RequestParam(required = false) int boardNo) {
		Recommend recommend = recommendService.modifyLikeCheckByBoard(userId, boardNo);
		modifyLikeCount(boardNo, recommend.isLikeCheck());
		return new ResponseEntity<Recommend>(recommend, HttpStatus.OK);
	}
	
	@PatchMapping("/like/{boardNo}")
	@ApiOperation(value = "좋아요 등록 및 취소", notes = "사용자가 해당 게시물에 좋아요를 누른 적 없다면 개수 증가 후 등록, 아니라면 감소 후 취소")
	@ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 404, message = "게시글 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<Board> modifyLikeCount(@PathVariable int boardNo, boolean check) {
		if (check) 
			return new ResponseEntity<Board>(boardService.modifyLikeCountAdd(boardNo), HttpStatus.OK);
		else
			return new ResponseEntity<Board>(boardService.modifyLikeCountCancel(boardNo), HttpStatus.OK);
	}

	@PatchMapping("/hate")
	@ApiOperation(value = "싫어요 정보 수정", notes = "사용자가 해당 게시물에 싫어요를 눌렀는지 안눌렀는지 판단할 수 있도록 정보를 수정")
	@ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 404, message = "게시글 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<Recommend> modifyHateCheck(@RequestParam(required = false) String userId, @RequestParam(required = false) int boardNo) {
		Recommend recommend = recommendService.modifyHateCheckByBoard(userId, boardNo);
		modifyHateCount(boardNo, recommend.isHateCheck());
		return new ResponseEntity<Recommend>(recommend, HttpStatus.OK);
	}
	
	@PatchMapping("/hate/{boardNo}")
	@ApiOperation(value = "싫어요 등록 및 취소", notes = "사용자가 해당 게시물에 싫어요를 누른 적 없다면 개수 증가 후 등록, 아니라면 감소 후 취소")
	@ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 404, message = "게시글 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<Board> modifyHateCount(@PathVariable int boardNo, boolean check) {
		if (check) 
			return new ResponseEntity<Board>(boardService.modifyHateCountAdd(boardNo), HttpStatus.OK);
		else
			return new ResponseEntity<Board>(boardService.modifyHateCountCancel(boardNo), HttpStatus.OK);
	}
}
