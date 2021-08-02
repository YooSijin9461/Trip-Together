package com.ssafy.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

/**
 * 채팅 모델 정의.
 */
@Entity
@Getter
@Setter
public class Chat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int chatNo;
	
	String userId;
	String chatting;
	String chatTime;
	int coferenceNo;
}
