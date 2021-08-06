package com.ssafy.api.request;

import javax.annotation.Nullable;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 회원가입 API ([POST] /api/v1/users) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("UserRegisterPostRequest")
public class UserRegisterPostReq {
    @ApiModelProperty(name="유저 ID", example="subin")
    String userId;
    @ApiModelProperty(name="유저 Password", example="12345")
    String password;
    @ApiModelProperty(name="유저 Name", example="김수빈")
    String userName;
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
    @ApiModelProperty(name="유저 평점", example="2.5")
    double avgScore;
    @ApiModelProperty(name="유저 가이드여부", example="true")
    boolean isGuide;
    @ApiModelProperty(name="저장될 파일명", example="")
    @Nullable
    String img;
    @ApiModelProperty(name="선택한 파일명", example="")
    @Nullable
    String orgImg;
//    @ApiModelProperty(name="이미지 등록을 위한 파일")
//    @Nullable
//    MultipartFile file;
}