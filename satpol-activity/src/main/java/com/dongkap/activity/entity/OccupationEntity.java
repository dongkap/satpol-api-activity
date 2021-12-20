package com.dongkap.activity.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
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
@EqualsAndHashCode(callSuper=false, exclude={"employees"})
@ToString(exclude={"employees"})
@Entity
@Table(name = "sec_occupation", schema = SchemaDatabase.ACTIVITY)
public class OccupationEntity extends BaseAuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5870155744883664118L;
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(name = "occupation_uuid", nullable = false, unique = true)
	private String id;

	@Column(name = "occupation_code", unique = true)
	private String code;

	@Column(name = "occupation_name")
	private String name;

	@ManyToMany(mappedBy = "occupation", targetEntity = EmployeeEntity.class, fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	private Set<EmployeeEntity> employees = new HashSet<EmployeeEntity>();

}