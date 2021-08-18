package com.ssafy.api.request;

import javax.annotation.Nullable;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 게시글 수정 API ([POST] /api/v1/boards) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("BoardModifyPostRequest")
public class BoardModifyPostReq {
	@ApiModelProperty(name="게시글 제목", example="게시글1")
	String boardTitle;
	@ApiModelProperty(name="게시글 내용", example="게시글입니다1")
	String boardContent;
	@ApiModelProperty(name="게시글 이미지")
	@Nullable
	String img;
	@ApiModelProperty(name="이미지 등록을 위한 파일")
	@Nullable
	MultipartFile file;
}
