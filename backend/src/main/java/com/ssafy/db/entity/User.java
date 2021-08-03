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
@Getter
@Setter
public class User extends BaseEntity{
	@Id
	String userId;
	
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
    String orgImg;	// 선택한 파일명
    @Transient
    MultipartFile file;		// 이미지 등록을 위한 파일
    //
    
//    @ManyToOne
//    @JoinColumn(name = "conference_no")
//    ConferenceRoom room;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;
}
