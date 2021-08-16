package com.ssafy.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

import com.ssafy.api.request.NoticeRegisterPostReq;
import com.ssafy.api.request.RoomModifyPostReq;
import com.ssafy.api.request.RoomRegisterPostReq;
import com.ssafy.api.service.NoticeService;
import com.ssafy.api.service.RoomService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.ConferenceRoom;
import com.ssafy.db.entity.Notice;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 공지사항 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "공지사항 API", tags = {"Notice"})
@RestController
@RequestMapping("/api/v1/notices")
@CrossOrigin("*")
public class NoticeController {
	
	@Autowired
	NoticeService noticeService;
	
	@PostMapping()
	@ApiOperation(value = "공지사항 등록", notes = "공지사항을 작성한다.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<Notice> registerNotice(
			@RequestBody @ApiParam(value="공지사항 등록 정보", required = true) NoticeRegisterPostReq registerInfo){
		Notice notice = noticeService.createNotice(registerInfo);
		return new ResponseEntity<>(notice, HttpStatus.OK);
		
	}
	
	@GetMapping()
	@ApiOperation(value = "공지사항 목록", notes = "공지사항 목록을 List로 반환")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<Page<Notice>> selectNotice(@PageableDefault(size = 999, sort = "noticeNo", direction = Sort.Direction.DESC) Pageable pageable){
		return new ResponseEntity<>(noticeService.selectNotice(pageable), HttpStatus.OK);
	}
	
	@GetMapping("/{noticeNo}")
	@ApiOperation(value = "공지사항 상세 조회", notes = "<strong>공지사항 번호</strong>를 통해 공지사항 상세정보 조회")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<Notice> getNoticeByNoticeNo(@PathVariable int noticeNo){
		return new ResponseEntity<>(noticeService.getInfoByNoticeNo(noticeNo), HttpStatus.OK);
	}
	
	@PatchMapping("/{noticeNo}")
	@ApiOperation(value = "공지사항 수정")
	@ApiResponses({
	        @ApiResponse(code = 200, message = "성공"),
	        @ApiResponse(code = 404, message = "방 없음"),
	        @ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<Notice> modifyRoom(@PathVariable int noticeNo, @RequestBody NoticeRegisterPostReq update){
		Notice notice = noticeService.getInfoByNoticeNo(noticeNo);
		notice = noticeService.modifyNotice(update, noticeNo);
		
		return new ResponseEntity<>(notice, HttpStatus.OK);
	}
	
	@DeleteMapping("/{noticeNo}")
	@ApiOperation(value = "공지사항 삭제")
	@ApiResponses({
	        @ApiResponse(code = 200, message = "성공"),
	        @ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<String> deleteNotice(@PathVariable int noticeNo){
		Notice notice = noticeService.getInfoByNoticeNo(noticeNo);
		if(notice != null) {
			noticeService.deleteNotice(noticeNo);
			return new ResponseEntity<>("success", HttpStatus.OK);
		}
		else
			return new ResponseEntity<>("fail", HttpStatus.OK);
	}
}
