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
@EqualsAndHashCode(callSuper=false, exclude={"timesheet", "file"})
@ToString(exclude={"timesheet", "file"})
@Entity
@Table(name = "event", schema = SchemaDatabase.ACTIVITY)
public class EventEntity extends BaseAuditEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1932022761237540822L;

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(name = "event_uuid", nullable = false, unique = true)
	private String id;

	@Column(name = "event_description", nullable = false)
	private String description;

	@Column(name = "event_status", nullable = false)
	private String status;

	@Column(name = "latitude", nullable = false)
	private String latitude;

	@Column(name = "longitude", nullable = false)
	private String longitude;

	@Column(name = "formatted_address")
	private String formattedAddress;

	@Column(name = "province")
	private String province;

	@Column(name = "city")
	private String city;

	@Column(name = "district")
	private String district;

	@Column(name = "device_name")
	private String deviceName;

	@OneToOne(targetEntity = TimesheetEntity.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "timesheet_uuid", nullable = false)
	private TimesheetEntity timesheet;

	@OneToOne(targetEntity = FileMetadataEntity.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "file_metadata_uuid", nullable = false)
	private FileMetadataEntity file;

}