package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

/**
 * 유저 모델 정의.
 */
@Entity
//@Getter
//@Setter
public class User extends BaseEntity{
	@Id
	String userId;
	
    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getMbti() {
		return mbti;
	}

	public void setMbti(String mbti) {
		this.mbti = mbti;
	}

	public double getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(double avgScore) {
		this.avgScore = avgScore;
	}

	public boolean isGuide() {
		return isGuide;
	}

	public void setGuide(boolean isGuide) {
		this.isGuide = isGuide;
	}

	public Integer getConferenceRoomNo() {
		return conferenceRoomNo;
	}

	public void setConferenceRoomNo(Integer conferenceRoomNo) {
		this.conferenceRoomNo = conferenceRoomNo;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getOrgImg() {
		return orgImg;
	}

	public void setOrgImg(String orgImg) {
		this.orgImg = orgImg;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	String userName;
    char gender;
    String phoneNum;
    String email;
    int age;
    String mbti;
    double avgScore;
    boolean isGuide;
    Integer conferenceRoomNo;
    String img;		// 저장될 파일명
    
    // 이미지 등록 추가
    @Transient
    String orgImg;	// 선택한 파일명
    @Transient		// DB에 컬럼으로 저장안되도록 무시하는 어노테이션
    MultipartFile file;		// 이미지 등록을 위한 파일
    //
    
//    @ManyToOne
//    @JoinColumn(name = "conference_no")
//    ConferenceRoom room;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;
}
