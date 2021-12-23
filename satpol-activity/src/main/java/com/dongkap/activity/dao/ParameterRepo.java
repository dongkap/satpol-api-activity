package com.dongkap.activity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dongkap.activity.entity.ParameterEntity;

public interface ParameterRepo extends JpaRepository<ParameterEntity, String>, JpaSpecificationExecutor<ParameterEntity> {
	
	ParameterEntity findByParameterCode(String parameterCode);
	
}