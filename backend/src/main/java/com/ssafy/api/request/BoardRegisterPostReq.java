package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 게시글 등록 API ([POST] /api/v1/boards) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("BoardRegisterPostRequest")
public class BoardRegisterPostReq {
	@ApiModelProperty(name="작성자", example="subin")
	String userId;
	@ApiModelProperty(name="게시글 제목", example="게시글1")
	String boardTitle;
	@ApiModelProperty(name="게시글 내용", example="게시글입니다1")
	String boardContent;
	@ApiModelProperty(name="게시글 좋아요 개수", example="10")	
	int likeCount;
	@ApiModelProperty(name="게시글 싫어요 개수", example="11")	
	int hateCount;
	
}
