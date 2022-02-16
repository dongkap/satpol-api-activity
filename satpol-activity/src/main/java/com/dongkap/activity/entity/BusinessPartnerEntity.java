package com.dongkap.activity.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@EqualsAndHashCode(callSuper=false, exclude={"assignmentGroup", "assets"})
@ToString(exclude={"assignmentGroup", "assets"})
@Entity
@Table(name = "mst_business_partner", schema = SchemaDatabase.ACTIVITY)
public class BusinessPartnerEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1932022761237540822L;

	@Id
	@Column(name = "bp_uuid", nullable = false, unique = true)
	private String id;

	@Column(name = "bp_name", nullable = false)
	private String bpName;

	@Column(name = "corporate_code", nullable = false)
	private String corporateCode;

	@Column(name = "is_active", nullable = false)
	private Boolean active = true;

	@OneToOne(mappedBy = "businessPartner", targetEntity = AssignmentGroupEntity.class, fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	private AssignmentGroupEntity assignmentGroup;

	@OneToMany(mappedBy = "businessPartner", targetEntity = AssetEntity.class, fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	private Set<AssetEntity> assets = new HashSet<AssetEntity>();

}