package com.dongkap.activity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dongkap.activity.entity.LogInventoryEntity;

public interface LogInventoryRepo extends JpaRepository<LogInventoryEntity, String>, JpaSpecificationExecutor<LogInventoryEntity> {
	
}