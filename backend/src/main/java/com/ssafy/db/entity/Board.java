package com.ssafy.db.entity;

//import java.sql.Date;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

/**
 * 게시판 모델 정의.
 */
@Entity
@Getter
@Setter
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int boardNo;
	
	String userId;
	String boardTitle;
	String boardContent;
	double boardRate;
	
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	//@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
    Timestamp boardTime;
}
