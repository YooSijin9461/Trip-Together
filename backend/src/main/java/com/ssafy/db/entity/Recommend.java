package com.ssafy.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Recommend {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long recommendNo;
	
	String userId;
	int boardNo;
	boolean likeCheck;
	boolean hateCheck;
	
}
