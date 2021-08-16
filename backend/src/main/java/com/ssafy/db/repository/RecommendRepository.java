package com.ssafy.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.db.entity.Recommend;

@Repository
public interface RecommendRepository extends JpaRepository<Recommend, Long> {
	Optional<Recommend> findByUserIdAndBoardNo(String userId, int boardNo);
}
