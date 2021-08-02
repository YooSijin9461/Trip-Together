package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 방 수정 API ([POST] /api/v1/conferences) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("RoomModifyPostReq")
public class RoomModifyPostReq {
	@ApiModelProperty(name="방 카테고리", example="normal")
	String conferenceCategory;
	@ApiModelProperty(name="방 비밀번호", example="4321")
	String conferencePassword;
	@ApiModelProperty(name="방 썸네일", example="abcd.jpg")
	String thumbnailUrl;
	@ApiModelProperty(name="방 이름", example="부산여행")
    String title;
	@ApiModelProperty(name="방 설명", example="부산 요트투어 같이 할 사람")
    String description;
	@ApiModelProperty(name="방 인원 제한", example="2")
    int limitUsersNum;
}
