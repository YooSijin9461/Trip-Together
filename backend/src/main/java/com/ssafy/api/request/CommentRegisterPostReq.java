package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 댓글 등록 API ([POST] /api/v1/comments) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("CommentRegisterPostRequest")
public class CommentRegisterPostReq {
	@ApiModelProperty(name="작성자", example="subin")
	String userId;
	@ApiModelProperty(name="댓글 내용", example="댓글입니다")
	String comment;
	@ApiModelProperty(name="게시글 번호", example="1")		// 이걸 직접 등록하는게 맞는지?
	int boardNo;
	String userImg;
}
