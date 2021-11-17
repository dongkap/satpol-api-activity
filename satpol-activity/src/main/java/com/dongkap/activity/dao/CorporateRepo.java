package com.dongkap.activity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dongkap.activity.entity.CorporateEntity;

public interface CorporateRepo extends JpaRepository<CorporateEntity, String>, JpaSpecificationExecutor<CorporateEntity> {

	CorporateEntity findByCorporateCode(String corporateCode);
	
}