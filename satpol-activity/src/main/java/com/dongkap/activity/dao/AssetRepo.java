package com.dongkap.activity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dongkap.activity.entity.AssetEntity;

public interface AssetRepo extends JpaRepository<AssetEntity, String>, JpaSpecificationExecutor<AssetEntity> {
	
}