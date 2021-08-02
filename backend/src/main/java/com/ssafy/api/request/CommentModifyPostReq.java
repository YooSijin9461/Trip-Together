package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 댓글 수정 API ([PATCH] /api/v1/comments) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("CommentModifyPostRequest")
public class CommentModifyPostReq {
	@ApiModelProperty(name="댓글 내용", example="댓글입니다")
	String comment;
}
