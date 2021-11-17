package com.dongkap.activity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dongkap.activity.entity.GuestBookEntity;

public interface GuestBookRepo extends JpaRepository<GuestBookEntity, String>, JpaSpecificationExecutor<GuestBookEntity> {
	
}