package com.ssafy.db.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

/**
 * 미팅방 모델 정의.
 */
@Entity
@Getter
@Setter
public class ConferenceRoom extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int conferenceNo;
    String conferenceCategory;
    String conferencePassword;
    String owner;
    String ownerId;
    String thumbnailUrl;
    String title;
    String description;
    String ownerImg;
    boolean isActive;
    int limitUsersNum;
    
    //@OneToMany(mappedBy = "conferenceRoom")
    //List<User> users = new ArrayList<>();
    
    //@Column(columnDefinition = "datetime default current_timestamp")
	@CreationTimestamp
    Timestamp startTime;
}
