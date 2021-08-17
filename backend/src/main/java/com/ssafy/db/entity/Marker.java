package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Marker {
	private double lat;
	private double lng;
	
	public Marker() {}
	
	public Marker(double lat, double lng) {
		this.lat = lat;
		this.lng = lng;
	}
	
}
