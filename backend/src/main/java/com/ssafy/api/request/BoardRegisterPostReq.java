package com.ssafy.api.request;

import javax.annotation.Nullable;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 게시글 등록 API ([POST] /api/v1/boards) 요청에 필요한 리퀘스트 바디 정의.
 */
//@Getter
//@Setter
@ApiModel("BoardRegisterPostRequest")
public class BoardRegisterPostReq {
	@ApiModelProperty(name="작성자", example="subin")
	String userId;
	@ApiModelProperty(name="게시글 제목", example="게시글1")
	String boardTitle;
	@ApiModelProperty(name="게시글 내용", example="게시글입니다1")
	String boardContent;
	@ApiModelProperty(name="작성자 사진", example="abc.jpg")
	String userImg;
	@ApiModelProperty(name="게시글 사진", example="def.jpg")
	String boardImg;
	@ApiModelProperty(name="이미지 등록을 위한 파일")
	@Nullable
	MultipartFile file;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	public String getBoardImg() {
		return boardImg;
	}
	public void setBoardImg(String boardImg) {
		this.boardImg = boardImg;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
