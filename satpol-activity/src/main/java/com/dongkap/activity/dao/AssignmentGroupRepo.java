package com.dongkap.activity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dongkap.activity.entity.AssignmentGroupEntity;

public interface AssignmentGroupRepo extends JpaRepository<AssignmentGroupEntity, String>, JpaSpecificationExecutor<AssignmentGroupEntity> {
	
}