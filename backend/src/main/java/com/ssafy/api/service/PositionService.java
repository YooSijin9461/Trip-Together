package com.ssafy.api.service;

import java.util.List;

import com.ssafy.db.entity.Position;

public interface PositionService {
	Position createMarker(Position marker);
	List<Position> markerList(int conferencePosNo);
	void deleteMarker(double lat, double lng);
}
