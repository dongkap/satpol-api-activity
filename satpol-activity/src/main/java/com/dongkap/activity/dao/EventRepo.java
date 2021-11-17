package com.dongkap.activity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dongkap.activity.entity.EventEntity;

public interface EventRepo extends JpaRepository<EventEntity, String>, JpaSpecificationExecutor<EventEntity> {
	
}