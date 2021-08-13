package com.ssafy.db.entity;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Position {
	int conferencePosNo;
	double lat;
	double lng;
}
