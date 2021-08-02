package com.ssafy.db.entity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class UserSpec {
	public static Specification<User> userIdLike(String userId){
		return new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.like(root.get("userId"), "%" + userId + "%");
			}
		};
	}
	
	public static Specification<User> userNameLike(String userName){
		return new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.like(root.get("userName"), "%" + userName + "%");
			}
		};
	}
}
