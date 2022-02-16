package com.dongkap.activity.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.dongkap.common.utils.SchemaDatabase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false, exclude={"assignmentGroup", "employee"})
@ToString(exclude={"assignmentGroup", "employee"})
@Entity
@Table(name = "assignment", schema = SchemaDatabase.ACTIVITY)
public class AssignmentEntity extends BaseApprovalEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1932022761237540822L;

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(name = "assignment_uuid", nullable = false, unique = true)
	private String id;

	@Column(name = "assignment_start_date")
	private Date assignmentStartDate;

	@Column(name = "assignment_end_date")
	private Date assignmentEndDate;

	@ManyToOne(targetEntity = AssignmentGroupEntity.class, fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "assignment_group_uuid", nullable = false)
	private AssignmentGroupEntity assignmentGroup;

	@OneToOne(targetEntity = EmployeeEntity.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_uuid", nullable = false)
	private EmployeeEntity employee;

}