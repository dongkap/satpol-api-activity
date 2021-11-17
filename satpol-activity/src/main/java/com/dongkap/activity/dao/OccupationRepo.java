package com.dongkap.activity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dongkap.activity.entity.OccupationEntity;

public interface OccupationRepo extends JpaRepository<OccupationEntity, String>, JpaSpecificationExecutor<OccupationEntity> {
	
}