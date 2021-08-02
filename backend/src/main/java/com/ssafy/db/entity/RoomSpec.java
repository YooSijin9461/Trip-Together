package com.ssafy.db.entity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class RoomSpec {
	public static Specification<ConferenceRoom> titleLike(String title){
		return new Specification<ConferenceRoom>() {
			@Override
			public Predicate toPredicate(Root<ConferenceRoom> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.like(root.get("title"), "%" + title + "%");
			}
		};
	}
	
	public static Specification<ConferenceRoom> ownerLike(String owner){
		return new Specification<ConferenceRoom>() {
			@Override
			public Predicate toPredicate(Root<ConferenceRoom> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.like(root.get("owner"), "%" + owner + "%");
			}
		};
	}
}
