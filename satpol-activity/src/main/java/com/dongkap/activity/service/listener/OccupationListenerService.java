package com.dongkap.activity.service.listener;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dongkap.activity.dao.OccupationRepo;
import com.dongkap.activity.entity.OccupationEntity;
import com.dongkap.common.stream.CommonStreamListener;
import com.dongkap.common.utils.ParameterStatic;
import com.dongkap.common.utils.StreamKeyStatic;
import com.dongkap.dto.common.CommonStreamMessageDto;
import com.dongkap.dto.security.OccupationDto;

import lombok.SneakyThrows;

@Service
public class OccupationListenerService extends CommonStreamListener<CommonStreamMessageDto> {

	@Autowired
	private OccupationRepo occupationRepo;

    public OccupationListenerService(
    		@Value("${spring.application.name}") String appName,
    		@Value("${spring.application.name}") String groupId) {
		super(appName, groupId, StreamKeyStatic.OCCUPATION, CommonStreamMessageDto.class);
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
	        	if(data instanceof OccupationDto) {
	        		OccupationDto request = (OccupationDto) data;
	        		if(value.getStatus().equalsIgnoreCase(ParameterStatic.PERSIST_DATA)) {
	        			this.persist(request);
	        		}
	        		if(value.getStatus().equalsIgnoreCase(ParameterStatic.DELETE_DATA)) {
		        		this.delete(request);
	        		}
	        	}
	        }
        }
	}
	
	public void persist(OccupationDto request) {
		OccupationEntity occupation = new OccupationEntity(); 
		occupation.setId(request.getId());
		occupation.setCode(request.getCode());
		occupation.setName(request.getName());
		try {
			occupationRepo.saveAndFlush(occupation);
		} catch (Exception e) {
			LOGGER.warn("Stream Persist : {}", e.getMessage());
		}
	}
	
	public void delete(OccupationDto request) {
		OccupationEntity occupation = occupationRepo.findById(request.getId()).orElse(null);
		try {
			occupationRepo.delete(occupation);
		} catch (DataIntegrityViolationException e) {
			LOGGER.warn("Stream Delete : {}", e.getMessage());
		} catch (ConstraintViolationException e) {
			LOGGER.warn("Stream Delete : {}", e.getMessage());
		}
	}
}
