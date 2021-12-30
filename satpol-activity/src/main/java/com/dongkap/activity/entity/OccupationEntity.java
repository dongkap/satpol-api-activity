package com.dongkap.activity.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "sec_occupation", schema = SchemaDatabase.ACTIVITY)
public class OccupationEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5870155744883664118L;
	
	@Id
	@Column(name = "occupation_uuid", nullable = false, unique = true)
	private String id;

	@Column(name = "occupation_code", unique = true)
	private String code;

	@Column(name = "occupation_name")
	private String name;

}