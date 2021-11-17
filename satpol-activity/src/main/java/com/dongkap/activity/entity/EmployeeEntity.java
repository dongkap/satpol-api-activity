package com.dongkap.activity.entity;

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
@EqualsAndHashCode(callSuper=false, exclude={"occupation"})
@ToString(exclude={"occupation"})
@Entity
@Table(name = "sec_employee", schema = SchemaDatabase.ACTIVITY)
public class EmployeeEntity extends BaseAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2442773369159964802L;
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
    @Column(name = "employee_uuid", nullable = false, unique=true)
	private String id;

	@Column(name = "id_employee", nullable = false)
	private String idEmployee;
	
	@Column(name = "username", nullable = false, unique=true)
	private String username;

	@OneToOne(targetEntity = OccupationEntity.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "occupation_uuid", nullable = false)
	private OccupationEntity occupation;

}