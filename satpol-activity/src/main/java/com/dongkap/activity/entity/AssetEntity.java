package com.dongkap.activity.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
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
@EqualsAndHashCode(callSuper=false, exclude={"businessPartner", "corporate"})
@ToString(exclude={"businessPartner", "corporate"})
@Entity
@Table(name = "mst_asset", schema = SchemaDatabase.ACTIVITY)
public class AssetEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1932022761237540822L;

	@Id
	@Column(name = "asset_uuid", nullable = false, unique = true)
	private String id;

	@Column(name = "asset_name", nullable = false)
	private String assetName;

	@Column(name = "is_active", nullable = false)
	private Boolean active = true;

	@ManyToOne(targetEntity = BusinessPartnerEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "bp_uuid", nullable = true)
	private BusinessPartnerEntity businessPartner;

	@ManyToOne(targetEntity = CorporateEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "corporate_uuid", nullable = false)
	private CorporateEntity corporate;

}