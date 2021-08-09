package com.ssafy.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.api.request.BoardModifyPostReq;
import com.ssafy.api.request.BoardRegisterPostReq;
import com.ssafy.api.request.CommentModifyPostReq;
import com.ssafy.api.request.CommentRegisterPostReq;
import com.ssafy.api.request.NoticeRegisterPostReq;
import com.ssafy.api.request.RoomModifyPostReq;
import com.ssafy.api.request.RoomRegisterPostReq;
import com.ssafy.api.service.BoardService;
import com.ssafy.api.service.CommentService;
import com.ssafy.api.service.NoticeService;
import com.ssafy.api.service.RoomService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Board;
import com.ssafy.db.entity.Comments;
import com.ssafy.db.entity.ConferenceRoom;
import com.ssafy.db.entity.Notice;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 댓글 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "댓글 API", tags = {"Comment"})
@RestController
@RequestMapping("/api/v1/comments")
@CrossOrigin("*")
public class CommentController {
	@Autowired
	CommentService commentService;
	
	@PostMapping()
	@ApiOperation(value = "댓글 등록", notes = "댓글을 작성한다.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<Comments> registerComment(
			@RequestBody @ApiParam(value="댓글 등록 정보", required = true) CommentRegisterPostReq registerInfo){
		Comments comment = commentService.createComment(registerInfo);
		return new ResponseEntity<>(comment, HttpStatus.OK);
		
	}
	
	@GetMapping("/{boardNo}")
	@ApiOperation(value = "댓글 목록", notes = "게시글 번호에 해당하는 댓글 목록 반환")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<List<Comments>> selectComment(int boardNo){
		return new ResponseEntity<List<Comments>>(commentService.selectComment(boardNo), HttpStatus.OK);
	}
	
	@PostMapping("/{commentNo}")
	@ApiOperation(value = "댓글 상세 조회", notes = "<strong>댓글 번호</strong>를 통해 댓글 상세정보 조회")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<Comments> getCommentByCommentNo(@PathVariable int commentNo){
		return new ResponseEntity<>(commentService.getInfoByCommentNo(commentNo), HttpStatus.OK);
	}
	
	@PatchMapping("/{commentNo}")
	@ApiOperation(value = "댓글 수정")
	@ApiResponses({
	        @ApiResponse(code = 200, message = "성공"),
	        @ApiResponse(code = 404, message = "게시글 없음"),
	        @ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<Comments> modifyComment(@PathVariable int commentNo, @RequestBody CommentModifyPostReq update){
		Comments comment = commentService.getInfoByCommentNo(commentNo);
		comment = commentService.modifyComment(update, commentNo);
		
		return new ResponseEntity<>(comment, HttpStatus.OK);
	}
	
	@DeleteMapping("/{commentNo}")
	@ApiOperation(value = "댓글 삭제")
	@ApiResponses({
	        @ApiResponse(code = 200, message = "성공"),
	        @ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<String> deleteComment(@PathVariable int commentNo){
		Comments comment = commentService.getInfoByCommentNo(commentNo);
		if(comment != null) {
			commentService.deleteComment(commentNo);
			return new ResponseEntity<>("success", HttpStatus.OK);
		}
		else
			return new ResponseEntity<>("fail", HttpStatus.OK);
	}
}
