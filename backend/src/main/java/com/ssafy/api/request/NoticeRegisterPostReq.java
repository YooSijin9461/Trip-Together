package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 공지사항 "등록 및 수정" API ([POST] /api/v1/notices) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("NoticeRegisterPostRequest")
public class NoticeRegisterPostReq {
	@ApiModelProperty(name="공지사항 제목", example="공지사항1")
	String noticeTitle;
	@ApiModelProperty(name="공지사항 내용", example="안녕")
	String noticeContent;
}
