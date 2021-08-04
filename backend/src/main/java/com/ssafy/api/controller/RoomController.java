package com.ssafy.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.api.request.RoomModifyPostReq;
import com.ssafy.api.request.RoomRegisterPostReq;
import com.ssafy.api.service.RoomService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.BoardSpec;
import com.ssafy.db.entity.ConferenceRoom;
import com.ssafy.db.entity.RoomSpec;
import com.ssafy.db.repository.RoomRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 미팅방 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "미팅방 API", tags = {"ConferenceRoom"})
@RestController
@RequestMapping("/api/v1/conferences")
public class RoomController {
	@Autowired
	RoomService roomService;
	
	@Autowired
	RoomRepository roomRepository;
	
	@PostMapping()
	@ApiOperation(value = "방 생성", notes = "방을 만든다.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<ConferenceRoom> registerRoom(
			@RequestBody @ApiParam(value="방 등록 정보", required = true) RoomRegisterPostReq registerInfo){
		ConferenceRoom room = roomService.createRoom(registerInfo);
		return new ResponseEntity<>(room, HttpStatus.OK);
	}
	
	@GetMapping()
	@ApiOperation(value = "방 목록", notes = "방 전체 목록을 List로 반환")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<Page<ConferenceRoom>> selectRoom(@RequestParam(required = false) String conferenceCategory,
														@PageableDefault(size = 999, sort = "conferenceNo", direction = Sort.Direction.DESC) Pageable pageable){
		if(conferenceCategory != null)
			return new ResponseEntity<>(roomService.selectRoom(conferenceCategory, pageable), HttpStatus.OK);
		return new ResponseEntity<>(roomService.selectRoom(pageable), HttpStatus.OK);
	}
	
	@GetMapping("/search")
	@ApiOperation(value = "방 검색", notes = "키워드로 검색한 방 목록을 List로 반환")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<List<ConferenceRoom>> selectRoom(@RequestParam(required = false) String title,
			   											   @RequestParam(required = false) String owner,
			   											@PageableDefault(size = 999, sort = "conferenceNo", direction = Sort.Direction.DESC) Pageable pageable){
		
		if(title != null)
			return new ResponseEntity<>(roomService.searchTitle(title, pageable), HttpStatus.OK);
		return new ResponseEntity<>(roomService.searchOwner(owner, pageable), HttpStatus.OK);
	}
	
	@GetMapping("/{conferenceNo}")
	@ApiOperation(value = "방 상세 조회", notes = "<strong>conference_no</strong>를 통해 방 상세정보 조회")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<ConferenceRoom> getRoomInfoById(@PathVariable int conferenceNo){
		return new ResponseEntity<>(roomService.getInfo(conferenceNo), HttpStatus.OK);
	}
	
	@PatchMapping("/{conferenceNo}")
	@ApiOperation(value = "방 정보 수정")
	@ApiResponses({
	        @ApiResponse(code = 200, message = "성공"),
	        @ApiResponse(code = 404, message = "방 없음"),
	        @ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<String> modifyRoom(@PathVariable int conferenceNo, @RequestBody RoomModifyPostReq update){
		ConferenceRoom room = roomService.getInfo(conferenceNo);
		room = roomService.modifyRoom(update, conferenceNo);
		
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@DeleteMapping("/{conferenceNo}")
	@ApiOperation(value = "방 삭제")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<String> deleteRoom(@PathVariable int conferenceNo) {
		ConferenceRoom room = roomService.getInfo(conferenceNo);
		if(room != null) {
			roomService.deleteRoom(conferenceNo);
			return new ResponseEntity<>("success", HttpStatus.OK);
		}
		return new ResponseEntity<>("fail", HttpStatus.OK);
	}
}
