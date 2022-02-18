package com.dongkap.activity.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dongkap.activity.common.CommonService;
import com.dongkap.activity.dao.AssignmentGroupRepo;
import com.dongkap.activity.dao.AssignmentRepo;
import com.dongkap.activity.dao.BusinessPartnerRepo;
import com.dongkap.activity.dao.CorporateRepo;
import com.dongkap.activity.dao.EmployeeRepo;
import com.dongkap.activity.dao.specification.AssignmentSpecification;
import com.dongkap.activity.entity.AssignmentEntity;
import com.dongkap.activity.entity.AssignmentGroupEntity;
import com.dongkap.activity.entity.BusinessPartnerEntity;
import com.dongkap.activity.entity.CorporateEntity;
import com.dongkap.activity.entity.EmployeeEntity;
import com.dongkap.common.exceptions.SystemErrorException;
import com.dongkap.common.utils.ErrorCode;
import com.dongkap.common.utils.SuccessCode;
import com.dongkap.dto.activity.AssignmentDto;
import com.dongkap.dto.activity.AssignmentRequestDto;
import com.dongkap.dto.common.ApiBaseResponse;
import com.dongkap.dto.common.CommonResponseDto;
import com.dongkap.dto.common.FilterDto;
import com.dongkap.dto.security.EmployeeDto;
import com.dongkap.dto.security.OccupationDto;

@Service("assignmentService")
public class AssignmentImplService extends CommonService {

	protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AssignmentRepo assignmentRepo;

	@Autowired
	private AssignmentGroupRepo assignmentGroupRepo;

	@Autowired
	private CorporateRepo corporateRepo;

	@Autowired
	private BusinessPartnerRepo businessPartnerRepo;

	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private MessageSource messageSource;

	@Value("${dongkap.locale}")
	private String localeCode;

	@Transactional
	public CommonResponseDto<AssignmentDto> getDatatable(Map<String, Object> additionalInfo, FilterDto filter) throws Exception {
		if(additionalInfo.get("corporate_code") == null) {
			throw new SystemErrorException(ErrorCode.ERR_SYS0001);
		}
		filter.getKeyword().put("corporateCode", additionalInfo.get("corporate_code"));
		final CommonResponseDto<AssignmentDto> response = new CommonResponseDto<AssignmentDto>();
		if(filter.getKeyword().get("assignmentGroupId") != null) {
			Page<AssignmentEntity> assignmentGroup = this.assignmentRepo.findAll(AssignmentSpecification.getDatatable(filter.getKeyword()), page(filter.getOrder(), filter.getOffset(), filter.getLimit()));
			response.setTotalFiltered(Long.valueOf(assignmentGroup.getContent().size()));
			response.setTotalRecord(assignmentRepo.count(AssignmentSpecification.getDatatable(filter.getKeyword())));
			assignmentGroup.getContent().forEach(value -> {
				AssignmentDto assignment = new AssignmentDto();
				EmployeeDto employeeTemp = new EmployeeDto();
				if(value.getEmployee() != null) {
					employeeTemp.setId(value.getEmployee().getId());
					employeeTemp.setFullname(value.getEmployee().getFullname());
					employeeTemp.setIdEmployee(value.getEmployee().getIdEmployee());
					if(value.getEmployee().getOccupation() != null) {
						OccupationDto occupation = new OccupationDto();
						occupation.setCode(value.getEmployee().getOccupation().getCode());
						occupation.setName(value.getEmployee().getOccupation().getName());
						employeeTemp.setOccupation(occupation);
					}
				}
				assignment.setId(value.getId());
				assignment.setEmployee(employeeTemp);
				response.getData().add(assignment);
			});	
		} else {
			response.setTotalFiltered(Long.valueOf(0));
			response.setTotalRecord(Long.valueOf(0));
		}
		return response;
	}

	@Transactional
	public ApiBaseResponse postAssignment(Map<String, Object> additionalInfo, AssignmentRequestDto request, String p_locale) throws Exception {
		if(additionalInfo.get("corporate_code") == null) {
			throw new SystemErrorException(ErrorCode.ERR_SYS0001);
		}
		Integer totalAssignment = 0;
		AssignmentGroupEntity assignmentGroup;
		if(request.getId() == null) {
			assignmentGroup = new AssignmentGroupEntity();
			BusinessPartnerEntity businessPartner = this.businessPartnerRepo.findById(request.getBpId()).orElse(null);
			if(businessPartner == null){
				throw new SystemErrorException(ErrorCode.ERR_SYS0001);
			}
			assignmentGroup.setBpId(request.getBpId());
			assignmentGroup.setBusinessPartner(businessPartner);
			CorporateEntity corporate = corporateRepo.findByCorporateCode(additionalInfo.get("corporate_code").toString());
			if(corporate == null) {
				throw new SystemErrorException(ErrorCode.ERR_SYS0001);
			}
			assignmentGroup.setCorporate(corporate);
			assignmentGroup.setTotalAssignment(totalAssignment); 
		} else {
			assignmentGroup = this.assignmentGroupRepo.findById(request.getId()).orElse(null);
			if(assignmentGroup == null){
				throw new SystemErrorException(ErrorCode.ERR_SYS0001);
			}
			totalAssignment = assignmentGroup.getTotalAssignment();
		}
		request.getEmployeeIds().forEach(id->{
			AssignmentEntity assignment = assignmentRepo.findByEmployee_Id(id);
			if(assignment == null) {
				assignment = new AssignmentEntity();
				EmployeeEntity employee = this.employeeRepo.findById(id).orElse(null);
				if(employee != null) {
					assignment.setEmployee(employee);
					assignment.setAssignmentGroup(assignmentGroup);
					assignmentGroup.getAssignments().add(assignment);
					assignmentGroup.setTotalAssignment(assignmentGroup.getTotalAssignment()+1);
				}	
			}
		});
		if(p_locale == null) {
			p_locale = this.localeCode;
		}
		Locale locale = Locale.forLanguageTag(p_locale);
		AssignmentGroupEntity assignmentGroupResult = this.assignmentGroupRepo.saveAndFlush(assignmentGroup);
		SuccessCode status = SuccessCode.OK_DEFAULT;
		ApiBaseResponse response = new ApiBaseResponse();
		response.setRespStatusCode(status.name());
		response.getRespStatusMessage().put("assignmentGroupId", assignmentGroupResult.getId());
		response.getRespStatusMessage().put(response.getRespStatusCode(), messageSource.getMessage(status.name(), null, locale));
		return response;
	}
	
	public void deleteAssignments(AssignmentRequestDto request) throws Exception {
		List<AssignmentEntity> assignments = assignmentRepo.findByIdIn(request.getAssignmentIds());
		try {
			this.assignmentRepo.deleteInBatch(assignments);
			Long total = this.assignmentRepo.countByAssignmentGroup_Id(request.getId());
			AssignmentGroupEntity assignmentGroup = this.assignmentGroupRepo.findById(request.getId()).orElse(null);
			if(assignmentGroup == null){
				throw new SystemErrorException(ErrorCode.ERR_SYS0001);
			}
			assignmentGroup.setTotalAssignment(total.intValue());
			this.assignmentGroupRepo.saveAndFlush(assignmentGroup);
		} catch (DataIntegrityViolationException e) {
			throw new SystemErrorException(ErrorCode.ERR_SCR0009);
		} catch (ConstraintViolationException e) {
			throw new SystemErrorException(ErrorCode.ERR_SCR0009);
		}
	}
	

}
