package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 정보 수정 API ([POST] /api/v1/users) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("UserModifyPostRequest")
public class UserModifyPostReq {
	@ApiModelProperty(name="유저 Password", example="12345")
	String password;
	@ApiModelProperty(name="유저 성별", example="f")
	char gender;
	@ApiModelProperty(name="유저 폰번호", example="123")
	String phoneNum;
	@ApiModelProperty(name="유저 email", example="1234")
	String email;
	@ApiModelProperty(name="유저 나이", example="25")
	int age;
	@ApiModelProperty(name="유저 mbti", example="ISFJ")
	String mbti;
	@ApiModelProperty(name="유저 가이드여부", example="false")
	boolean isGuide;
	@ApiModelProperty(name="유저 프로필", example="b.jpg")
	String img;
}
