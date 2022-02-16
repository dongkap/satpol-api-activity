package com.dongkap.activity.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dongkap.activity.entity.AssignmentEntity;

public interface AssignmentRepo extends JpaRepository<AssignmentEntity, String>, JpaSpecificationExecutor<AssignmentEntity> {

	List<AssignmentEntity> findByIdIn(List<String> ids);
	
	AssignmentEntity findByEmployee_Id(String employeeUUID);
	
	long countByAssignmentGroup_Id(String assignmentGroupId);
	
}