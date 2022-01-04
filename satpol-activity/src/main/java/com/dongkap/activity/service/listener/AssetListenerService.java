package com.dongkap.activity.service.listener;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dongkap.activity.dao.AssetRepo;
import com.dongkap.activity.dao.BusinessPartnerRepo;
import com.dongkap.activity.dao.CorporateRepo;
import com.dongkap.activity.entity.AssetEntity;
import com.dongkap.activity.entity.BusinessPartnerEntity;
import com.dongkap.activity.entity.CorporateEntity;
import com.dongkap.common.exceptions.SystemErrorException;
import com.dongkap.common.stream.CommonStreamListener;
import com.dongkap.common.utils.ErrorCode;
import com.dongkap.common.utils.ParameterStatic;
import com.dongkap.common.utils.StreamKeyStatic;
import com.dongkap.dto.common.CommonStreamMessageDto;
import com.dongkap.dto.master.AssetDto;

import lombok.SneakyThrows;

@Service
public class AssetListenerService extends CommonStreamListener<CommonStreamMessageDto> {

	@Autowired
	private AssetRepo assetRepo;

	@Autowired
	private BusinessPartnerRepo businessPartnerRepo;

	@Autowired
	private CorporateRepo corporateRepo;

    public AssetListenerService(
    		@Value("${spring.application.name}") String appName,
    		@Value("${spring.application.name}") String groupId) {
		super(appName, groupId, StreamKeyStatic.ASSET, CommonStreamMessageDto.class);
	}
	
	@Override
    @SneakyThrows
	@Transactional(noRollbackFor = { ConstraintViolationException.class }, propagation = Propagation.REQUIRES_NEW)
	public void onMessage(ObjectRecord<String, CommonStreamMessageDto> message) {
		try {
	        String stream = message.getStream();
	        RecordId id = message.getId();
			LOGGER.info("A message was received stream: [{}], id: [{}]", stream, id);
	        CommonStreamMessageDto value = message.getValue();
	        if(value != null) {
	        	for(Object data: value.getDatas()) {
		        	if(data instanceof AssetDto) {
		        		AssetDto request = (AssetDto) data;
		        		if(value.getStatus().equalsIgnoreCase(ParameterStatic.PERSIST_DATA)) {
		        			this.persist(request);
		        		}
		        		if(value.getStatus().equalsIgnoreCase(ParameterStatic.DELETE_DATA)) {
			        		this.delete(request);
		        		}
		        	}
		        }
	        }
		} catch (Exception e) {
			LOGGER.warn("Stream On Message : {}", e.getMessage());
		}
	}
	
	public void persist(AssetDto request) {
		try {
			AssetEntity asset = assetRepo.findById(request.getId()).orElse(null);
			if(asset == null) {
				asset = new AssetEntity();	
			}
			asset.setId(request.getId());
			asset.setAssetName(request.getAssetName());
			if(request.getCorporate() != null) {
				CorporateEntity corporate = corporateRepo.findById(request.getCorporate().getId()).orElse(null);
				if(corporate == null) {
					corporate = new CorporateEntity();
					corporate.setId(request.getCorporate().getId());
					corporate.setCorporateCode(request.getCorporate().getCorporateCode());
					corporate.setCorporateName(request.getCorporate().getCorporateName());
					corporate.getAssets().add(asset);
				}
				asset.setCorporate(corporate);
			} else {
				throw new SystemErrorException(ErrorCode.ERR_SYS0415);
			}
			if(request.getBusinessPartner() != null) {
				BusinessPartnerEntity businessPartner = businessPartnerRepo.findById(request.getBusinessPartner().getId()).orElse(null);
				if(businessPartner == null) {
					businessPartner = new BusinessPartnerEntity();
					businessPartner.setId(request.getBusinessPartner().getId());
					businessPartner.setBpName(request.getBusinessPartner().getBpName());
					businessPartner.getAssets().add(asset);
				} 
				asset.setBusinessPartner(businessPartner);
			}
			assetRepo.saveAndFlush(asset);
		} catch (DataIntegrityViolationException e) {
			LOGGER.warn("Stream Persist : {}", e.getMessage());
		} catch (ConstraintViolationException e) {
			LOGGER.warn("Stream Persist : {}", e.getMessage());
		} catch (Exception e) {
			LOGGER.warn("Stream Persist : {}", e.getMessage());
		}
	}
	
	public void delete(AssetDto request) {
		try {
			AssetEntity asset = assetRepo.findById(request.getId()).orElse(null);
			if(asset != null) {
				asset.setActive(false);
				assetRepo.saveAndFlush(asset);
			}
		} catch (Exception e) {
			LOGGER.warn("Stream Soft Delete : {}", e.getMessage());
		}
	}
}
