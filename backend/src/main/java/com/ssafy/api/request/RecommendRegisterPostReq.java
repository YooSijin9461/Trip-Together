package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 추천 정보 등록 API ([POST] /api/v1/recommend) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("BoardRegisterPostRequest")
public class RecommendRegisterPostReq {
	@ApiModelProperty(name="작성자", example="ksh7628")
	String userId;
	@ApiModelProperty(name="게시글 번호", example="1")
	int boardNo;
	
}
