package com.dongkap.activity.dao.specification;

import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.dongkap.activity.entity.AssignmentEntity;

public class AssignmentSpecification {
	
	private static final String IS_ACTIVE = "active";

	public static Specification<AssignmentEntity> getDatatable(final Map<String, Object> keyword) {
		return new Specification<AssignmentEntity>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -637621292944403277L;

			@Override
			public Predicate toPredicate(Root<AssignmentEntity> root, CriteriaQuery<?> criteria, CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (!keyword.isEmpty()) {
					for(Map.Entry<String, Object> filter : keyword.entrySet()) {
						String key = filter.getKey();
						Object value = filter.getValue();
						if (value != null) {
							switch (key) {
								case "employeeName" :
									// builder.upper for PostgreSQL
									predicate.getExpressions().add(builder.like(builder.upper(root.join("employee").<String>get("fullname")), String.format("%%%s%%", value.toString().toUpperCase())));
									break;
								case "assignmentGroupId" :
									predicate.getExpressions().add(builder.equal(root.join("assignmentGroup").<String>get("id"), value.toString()));
									break;
								case "corporateCode" :
									predicate.getExpressions().add(builder.equal(root.join("assignmentGroup").join("corporate").<String>get("corporateCode"), value.toString()));
									break;
								case "_all" :
									predicate.getExpressions().add(builder.like(builder.upper(root.<String>get("bpName")), String.format("%%%s%%", value.toString().toUpperCase())));
									break;
								default :
									break;
							}	
						}
					}
				}
				predicate = builder.and(predicate, builder.equal(root.get(IS_ACTIVE), true));
				return predicate;
			}
		};
	}

}
