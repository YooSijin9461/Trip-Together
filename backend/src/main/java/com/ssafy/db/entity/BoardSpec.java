package com.ssafy.db.entity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class BoardSpec {
	public static Specification<Board> boardTitleLike(String boardTitle){
		return new Specification<Board>() {
			@Override
			public Predicate toPredicate(Root<Board> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.like(root.get("boardTitle"), "%" + boardTitle + "%");
			}
		};
	}
	
	public static Specification<Board> boardContentLike(String boardContent){
		return new Specification<Board>() {
			@Override
			public Predicate toPredicate(Root<Board> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.like(root.get("boardContent"), "%" + boardContent + "%");
			}
		};
	}
	
	public static Specification<Board> userIdLike(String userId){
		return new Specification<Board>() {
			@Override
			public Predicate toPredicate(Root<Board> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.like(root.get("userId"), "%" + userId + "%");
			}
		};
	}
}
