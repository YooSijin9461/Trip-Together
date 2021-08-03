package com.ssafy.api.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.api.request.UserLoginPostReq;
import com.ssafy.api.request.UserModifyPostReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.response.UserLoginPostRes;
import com.ssafy.api.response.UserRes;
import com.ssafy.api.service.RoomService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.common.util.JwtTokenUtil;
import com.ssafy.db.entity.Board;
import com.ssafy.db.entity.BoardSpec;
import com.ssafy.db.entity.ConferenceRoom;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.UserSpec;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	@Autowired
	UserService userService;
	
	@Autowired
	RoomService roomService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ResourceLoader resourceLoader;
	
	@PostMapping()
	@ApiOperation(value = "회원 가입", notes = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<User> register(
			@RequestBody @ApiParam(value="회원가입 정보", required = true) UserRegisterPostReq registerInfo) throws IllegalStateException, IOException {
		//임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
		MultipartFile file = registerInfo.getFile();
		if(file != null && file.getSize() > 0) {
			Resource res = resourceLoader.getResource("resources/upload");
			registerInfo.setImg(System.currentTimeMillis() + "_" + file.getOriginalFilename());
			registerInfo.setOrgImg(file.getOriginalFilename());
			file.transferTo(new File(res.getFile().getCanonicalFile() + "/" + registerInfo.getImg()));
		}
		User user = userService.createUser(registerInfo);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@GetMapping()
	@ApiOperation(value = "사용자 목록", notes = "사용자 목록을 List로 반환")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공"),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<List<User>> selectUser(@RequestParam(required = false) String userName,
												 @RequestParam(required = false) String userId){
		if(userName != null)
			return new ResponseEntity<>(userRepository.findAll(UserSpec.userNameLike(userName)), HttpStatus.OK);
		else if(userId != null)
			return new ResponseEntity<>(userRepository.findAll(UserSpec.userIdLike(userId)), HttpStatus.OK);
		return new ResponseEntity<>(userService.selectUser(), HttpStatus.OK);
	}
	
//	@GetMapping()
//	@ApiOperation(value = "사용자 목록", notes = "사용자 목록을 List로 반환")
//	@ApiResponses({
//		@ApiResponse(code = 200, message = "성공"),
//		@ApiResponse(code = 500, message = "서버 오류")
//	})
//	public ResponseEntity<List<User>> selectUser(){
//		return new ResponseEntity<>(userService.selectUser(), HttpStatus.OK);
//	}

	@GetMapping("/me")
	@ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<UserRes> getUserInfo(@ApiIgnore Authentication authentication) {
		/**
		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
		 * 정당한 사용자인지 확인하는 용도? -> 정보를 파악하기 위해서는 다른 API가 필요할 걸
		 */
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userId = userDetails.getUsername();
		User user = userService.getUserByUserId(userId);
		
		return ResponseEntity.status(200).body(UserRes.of(user));
	}
	
//	@PostMapping("/{userId}")
//	@ApiOperation(value = "유저 정보 확인", notes = "유저 정보 보내기(토큰에 다 넣는 것 대신)")
//	@ApiResponses({
//		@ApiResponse(code = 409, message = "이미 존재하는 유저"),
//		@ApiResponse(code = 500, message = "성공"),
//		@ApiResponse(code = 200, message = "성공")
//	})
//	public ResponseEntity<User> readUserInfo(@PathVariable String userId){
//		return new ResponseEntity<>(userService.getUserByUserId(userId), HttpStatus.OK);
//	}

	// PostMapping된 유저 정보 확인 API로 같이 할 수 있지 않을까?
//	@GetMapping("/{userId}")
//	@ApiOperation(value = "유저 정보 확인(존재여부)", notes = "존재하는 회원 확인용.")
//	@ApiResponses({
//		@ApiResponse(code = 409, message = "이미 존재하는 유저"),
//		@ApiResponse(code = 500, message = "성공"),
//		@ApiResponse(code = 200, message = "성공")
//	})
//	public ResponseEntity<UserRes> getUserInfoById(@PathVariable String userId){
//		// 에러 뜨면서 Exception 발생하면 우리 입장에서는 존재하지 않는다는 걸로 판단해서 성공한건데 어떻게 할지
//		User user = userService.getUserByUserId(userId);
//		try {
//			if(user != null)
//				return ResponseEntity.status(409).body(UserRes.of(user));
//			else
//				return ResponseEntity.status(200).body(UserRes.of(user));
//		} catch(Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.status(200).body(UserRes.of(user));
//		}
//	}
	
//	@PatchMapping("")
//	@ApiOperation(value = "유저 정보 수정")
//	@ApiResponses({
//	        @ApiResponse(code = 200, message = "성공"),
//	        @ApiResponse(code = 404, message = "사용자 없음"),
//	        @ApiResponse(code = 500, message = "서버 오류")
//	})
//	public ResponseEntity<UserRes> modifyInfo(@ApiIgnore Authentication authentication, User update) {
//		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
//		String userId = userDetails.getUsername();
//		User user = userService.getUserByUserId(userId);		// 로그인한 사용자 받아옴
//		user = userService.modifyUser(update, userId);
//		
//		return ResponseEntity.status(200).body(UserRes.of(user));
//	}
	
	@PatchMapping("/{userId}")
	@ApiOperation(value = "유저 정보 수정")
	@ApiResponses({
	        @ApiResponse(code = 200, message = "성공"),
	        @ApiResponse(code = 404, message = "사용자 없음"),
	        @ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<UserRes> modifyInfoById(@PathVariable String userId, @RequestBody UserModifyPostReq update) {
		User user = userService.getUserByUserId(userId);		// 아이디에 맞는 사용자 찾아옴
		user = userService.modifyUser(update, userId);
		return ResponseEntity.status(200).body(UserRes.of(user));
	}
	
	// 삭제 완료
	@DeleteMapping("/{userId}")
	@ApiOperation(value = "유저 정보 삭제")
	@ApiResponses({
	        @ApiResponse(code = 204, message = "성공"),
	        @ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<UserRes> deleteUser(@PathVariable String userId) {
		User user = userService.getUserByUserId(userId);
		if(user != null) {
			userService.deleteUser(userId);
			return ResponseEntity.status(204).body(UserRes.of(user));
		}
		else
			return ResponseEntity.status(500).body(UserRes.of(user));
	}
	
	@GetMapping("/{conferenceNo}")
	@ApiOperation(value = "방 번호로 참여중인 유저 리스트 가져오기")
	public ResponseEntity<List<User>> getUsersByConferenceNo(@PathVariable int conferenceNo){
		return new ResponseEntity<List<User>>(userService.getUsersByConferenceNo(conferenceNo), HttpStatus.OK);
	}
}
