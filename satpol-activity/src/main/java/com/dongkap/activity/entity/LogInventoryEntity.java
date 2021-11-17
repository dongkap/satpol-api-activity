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
@EqualsAndHashCode(callSuper=false, exclude={"timesheet", "file", "asset"})
@ToString(exclude={"timesheet", "file", "asset"})
@Entity
@Table(name = "log_inventory", schema = SchemaDatabase.ACTIVITY)
public class LogInventoryEntity extends BaseAuditEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1932022761237540822L;

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(name = "log_inventory_uuid", nullable = false, unique = true)
	private String id;

	@Column(name = "asset_condition", nullable = false)
	private String condition;

	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@Column(name = "description")
	private String description;

	@OneToOne(targetEntity = TimesheetEntity.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "timesheet_uuid", nullable = false)
	private TimesheetEntity timesheet;

	@OneToOne(targetEntity = FileMetadataEntity.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "file_metadata_uuid")
	private FileMetadataEntity file;

	@OneToOne(targetEntity = AssetEntity.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "asset_uuid", nullable = false)
	private AssetEntity asset;

}