package com.ssafy.db.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.ssafy.db.entity.Position;

@Repository
public interface PositionRepository {
	Optional<Position> findAllByConferencePosNo(int conferencePosNo);
}
