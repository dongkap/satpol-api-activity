package com.dongkap.activity.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.dongkap.common.utils.SchemaDatabase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false, exclude={"b2bSet", "assets"})
@ToString(exclude={"b2bSet", "assets"})
@Entity
@Table(name = "sec_corporate", schema = SchemaDatabase.ACTIVITY)
public class CorporateEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1932022761237540822L;

	@Id
	@Column(name = "corporate_uuid", nullable = false, unique = true)
	private String id;

	@Column(name = "corporate_code", nullable = false, unique = true)
	private String corporateCode;

	@Column(name = "corporate_name", nullable = false)
	private String corporateName;

	@Column(name = "is_active", nullable = false)
	private Boolean active = true;

	@ManyToMany(mappedBy = "corporate", targetEntity = AssignmentGroupEntity.class, fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	private Set<AssignmentGroupEntity> b2bSet = new HashSet<AssignmentGroupEntity>();

	@ManyToMany(mappedBy = "corporate", targetEntity = AssetEntity.class, fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	private Set<AssetEntity> assets = new HashSet<AssetEntity>();

}