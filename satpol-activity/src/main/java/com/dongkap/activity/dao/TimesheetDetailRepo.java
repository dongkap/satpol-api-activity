package com.dongkap.activity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dongkap.activity.entity.TimesheetDetailEntity;

public interface TimesheetDetailRepo extends JpaRepository<TimesheetDetailEntity, String>, JpaSpecificationExecutor<TimesheetDetailEntity> {
	
}