package com.ssafy.db.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

/**
 * 댓글 모델 정의.
 */
@Entity
@Getter
@Setter
public class Comments {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int commentNo;
	
	String userId;
	String comment;
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@CreationTimestamp
    Timestamp commentTime;
	int boardNo;
	String userImg;
	char userGender;
}
