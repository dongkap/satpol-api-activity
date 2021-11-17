package com.dongkap.activity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dongkap.activity.entity.TimesheetEntity;

public interface TimesheetRepo extends JpaRepository<TimesheetEntity, String>, JpaSpecificationExecutor<TimesheetEntity> {
	
}