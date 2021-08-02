package com.ssafy.db.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

/**
 * 공지사항 모델 정의.
 */
@Entity
@Getter
@Setter
public class Notice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int noticeNo;
	
	String noticeTitle;
	String noticeContent;
	
	@CreationTimestamp
    Timestamp noticeTime;
}
