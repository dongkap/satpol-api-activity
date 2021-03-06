package com.dongkap.activity.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
public class EmployeeEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2442773369159964802L;
	
	@Id
    @Column(name = "employee_uuid", nullable = false, unique=true)
	private String id;

	@Column(name = "id_employee", nullable = false)
	private String idEmployee;
	
	@Column(name = "username", nullable = false, unique=true)
	private String username;
	
	@Column(name = "fullname", nullable = false)
	private String fullname;

	@Column(name = "is_active", nullable = false)
	private Boolean active = true;

	@ManyToOne(targetEntity = OccupationEntity.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "occupation_uuid", nullable = false)
	private OccupationEntity occupation;

}