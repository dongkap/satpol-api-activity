package com.dongkap.activity.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@EqualsAndHashCode(callSuper=false, exclude={"assignment", "nextShift"})
@ToString(exclude={"assignment", "nextShift"})
@Entity
@Table(name = "timesheet", schema = SchemaDatabase.ACTIVITY)
public class TimesheetEntity extends BaseApprovalEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1932022761237540822L;

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(name = "assignment_uuid", nullable = false, unique = true)
	private String id;

	@Column(name = "shift", nullable = false)
	private String shift;

	@Column(name = "checkin_time")
	private Date checkinTime;

	@Column(name = "checkout_time")
	private Date checkoutTime;

	@OneToOne(targetEntity = AssignmentEntity.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "assignment_uuid", nullable = false)
	private AssignmentEntity assignment;

	@OneToOne(targetEntity = AssignmentEntity.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "next_shift_uuid", nullable = false)
	private AssignmentEntity nextShift;

}