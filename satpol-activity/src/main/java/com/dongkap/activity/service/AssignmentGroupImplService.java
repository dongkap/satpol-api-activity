package com.dongkap.activity.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dongkap.activity.common.CommonService;
import com.dongkap.activity.dao.BusinessPartnerRepo;
import com.dongkap.activity.dao.specification.BusinessPartnerSpecification;
import com.dongkap.activity.entity.BusinessPartnerEntity;
import com.dongkap.common.exceptions.SystemErrorException;
import com.dongkap.common.utils.ErrorCode;
import com.dongkap.dto.activity.AssignmentGroupDto;
import com.dongkap.dto.common.CommonResponseDto;
import com.dongkap.dto.common.FilterDto;

@Service("assignmentGroupService")
public class AssignmentGroupImplService extends CommonService {

	protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BusinessPartnerRepo businessPartnerRepo;

	@Transactional
	public CommonResponseDto<AssignmentGroupDto> getDatatable(Map<String, Object> additionalInfo, FilterDto filter) throws Exception {
		if(additionalInfo.get("corporate_code") == null) {
			throw new SystemErrorException(ErrorCode.ERR_SYS0001);
		}
		filter.getKeyword().put("corporateCode", additionalInfo.get("corporate_code"));
		Page<BusinessPartnerEntity> assignmentGroup = this.businessPartnerRepo.findAll(BusinessPartnerSpecification.getDatatable(filter.getKeyword()), page(filter.getOrder(), filter.getOffset(), filter.getLimit()));
		final CommonResponseDto<AssignmentGroupDto> response = new CommonResponseDto<AssignmentGroupDto>();
		response.setTotalFiltered(Long.valueOf(assignmentGroup.getContent().size()));
		response.setTotalRecord(businessPartnerRepo.count(BusinessPartnerSpecification.getDatatable(filter.getKeyword())));
		assignmentGroup.getContent().forEach(value -> {
			AssignmentGroupDto assignmentGroupTemp = new AssignmentGroupDto();
			assignmentGroupTemp.setBpId(value.getId());
			assignmentGroupTemp.setBpName(value.getBpName());
			if(value.getAssignmentGroup() != null) {
				assignmentGroupTemp.setId(value.getAssignmentGroup().getId());
				assignmentGroupTemp.setCorporateCode(value.getAssignmentGroup().getCorporate().getCorporateCode());
				assignmentGroupTemp.setCorporateName(value.getAssignmentGroup().getCorporate().getCorporateName());
				assignmentGroupTemp.setTotalAssignment(value.getAssignmentGroup().getTotalAssignment());
				assignmentGroupTemp.setActive(value.getAssignmentGroup().getActive());
				assignmentGroupTemp.setVersion(value.getAssignmentGroup().getVersion());
				assignmentGroupTemp.setCreatedDate(value.getAssignmentGroup().getCreatedDate());
				assignmentGroupTemp.setCreatedBy(value.getAssignmentGroup().getCreatedBy());
				assignmentGroupTemp.setModifiedDate(value.getAssignmentGroup().getModifiedDate());
				assignmentGroupTemp.setModifiedBy(value.getAssignmentGroup().getModifiedBy());
			} else {
				assignmentGroupTemp.setTotalAssignment(0);				
			}
			response.getData().add(assignmentGroupTemp);
		});
		return response;
	}

}
