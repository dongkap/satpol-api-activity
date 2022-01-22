package com.dongkap.activity.service.listener;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dongkap.activity.dao.EmployeeRepo;
import com.dongkap.activity.dao.OccupationRepo;
import com.dongkap.activity.entity.EmployeeEntity;
import com.dongkap.activity.entity.OccupationEntity;
import com.dongkap.common.stream.CommonStreamListener;
import com.dongkap.common.utils.ParameterStatic;
import com.dongkap.common.utils.StreamKeyStatic;
import com.dongkap.dto.common.CommonStreamMessageDto;
import com.dongkap.dto.security.EmployeeDto;

import lombok.SneakyThrows;

@Service
public class EmployeeListenerService extends CommonStreamListener<CommonStreamMessageDto> {

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private OccupationRepo occupationRepo;

    public EmployeeListenerService(
    		@Value("${spring.application.name}") String appName,
    		@Value("${spring.application.name}") String groupId) {
		super(appName, groupId, StreamKeyStatic.EMPLOYEE, CommonStreamMessageDto.class);
	}
	
	@Override
    @SneakyThrows
    @Transactional
	public void onMessage(ObjectRecord<String, CommonStreamMessageDto> message) {
		try {
	        String stream = message.getStream();
	        RecordId id = message.getId();
			LOGGER.info("A message was received stream: [{}], id: [{}]", stream, id);
	        CommonStreamMessageDto value = message.getValue();
	        if(value != null) {
	        	for(Object data: value.getDatas()) {
		        	if(data instanceof EmployeeDto) {
		        		EmployeeDto request = (EmployeeDto) data;
		        		if(value.getStatus().equalsIgnoreCase(ParameterStatic.PERSIST_DATA)) {
		        			this.persist(request);
		        		}
		        	}
		        }
	        }
		} catch (Exception e) {
			LOGGER.warn("Stream On Message : {}", e.getMessage());
		}
	}
	
	public void persist(EmployeeDto request) {
		try {
			EmployeeEntity employee = new EmployeeEntity(); 
			employee.setId(request.getId()); 
			employee.setIdEmployee(request.getIdEmployee());
			employee.setUsername(request.getUsername());
			employee.setFullname(request.getFullname());
			if(request.getOccupation().getCode() != null) {
				OccupationEntity occupation = occupationRepo.findByCode(request.getOccupation().getCode());
				employee.setOccupation(occupation);
	    		employeeRepo.saveAndFlush(employee);
			}
		} catch (DataIntegrityViolationException e) {
			LOGGER.warn("Stream Persist : {}", e.getMessage());
		} catch (ConstraintViolationException e) {
			LOGGER.warn("Stream Persist : {}", e.getMessage());
		} catch (Exception e) {
			LOGGER.warn("Stream Persist : {}", e.getMessage());
		}
	}

}
