package com.dongkap.activity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dongkap.activity.entity.FileMetadataEntity;

public interface FileMetadataRepo extends JpaRepository<FileMetadataEntity, String>, JpaSpecificationExecutor<FileMetadataEntity> {
	
}