package com.dongkap.activity.service.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dongkap.activity.dao.BusinessPartnerRepo;
import com.dongkap.activity.entity.BusinessPartnerEntity;
import com.dongkap.common.stream.CommonStreamListener;
import com.dongkap.common.utils.ParameterStatic;
import com.dongkap.common.utils.StreamKeyStatic;
import com.dongkap.dto.common.CommonStreamMessageDto;
import com.dongkap.dto.master.B2BDto;

import lombok.SneakyThrows;

@Service
public class BusinessPartnerListenerService extends CommonStreamListener<CommonStreamMessageDto> {

	@Autowired
	private BusinessPartnerRepo businessPartnerRepo;

    public BusinessPartnerListenerService(
    		@Value("${spring.application.name}") String appName,
    		@Value("${spring.application.name}") String groupId) {
		super(appName, groupId, StreamKeyStatic.BUSINESS_PARTNER, CommonStreamMessageDto.class);
	}
	
	@Override
    @SneakyThrows
    @Transactional
	public void onMessage(ObjectRecord<String, CommonStreamMessageDto> message) {
        String stream = message.getStream();
        RecordId id = message.getId();
		LOGGER.info("A message was received stream: [{}], id: [{}]", stream, id);
        CommonStreamMessageDto value = message.getValue();
        if(value != null) {
        	for(Object data: value.getDatas()) {
	        	if(data instanceof B2BDto) {
	        		B2BDto request = (B2BDto) data;
	        		if(value.getStatus().equalsIgnoreCase(ParameterStatic.PERSIST_DATA)) {
	        			this.persist(request);
	        		}
	        	}
	        }
        }
	}
	
	public void persist(B2BDto request) {
		BusinessPartnerEntity businessPartner = new BusinessPartnerEntity(); 
		businessPartner.setId(request.getBusinessPartner().getId());
		businessPartner.setBpName(request.getBusinessPartner().getBpName());
		try {
    		businessPartnerRepo.saveAndFlush(businessPartner);
		} catch (Exception e) {
			LOGGER.warn("Stream Persist : {}", e.getMessage());
		}
	}

}
