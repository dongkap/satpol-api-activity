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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;

import com.dongkap.common.utils.SchemaDatabase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false, exclude={"assignmentGroup", "file"})
@ToString(exclude={"assignmentGroup", "file"})
@Entity
@Table(name = "guest_book", schema = SchemaDatabase.ACTIVITY)
public class GuestBookEntity extends BaseAuditEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1932022761237540822L;

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(name = "guest_book_uuid", nullable = false, unique = true)
	private String id;

	@Column(name = "guest_name", nullable = false)
	private String guestName;

	@Column(name = "purpose", nullable = false)
	private Date purpose;

	@Column(name = "guest_card_no")
	private String guestCardNo;

	@Column(name = "plate_no")
	private String plateNo;

	@Column(name = "vehicle_type")
	private String vehicleType;

	@Column(name = "id_card_type")
	private String idCardType;

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="in_checked_date", updatable = false)
	private Date inCheckedDate;

	@Column(name = "in_checked_by", updatable = false) 
    @CreatedBy
    private String inCheckedBy;

	@Column(name = "in_checked_fullname", updatable = false) 
    @CreatedBy
    private String inCheckedFullname;

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="out_checked_date", updatable = false)
	private Date outCheckedDate;

    @Column(name = "out_checked_by", updatable = false)
    private String outCheckedBy;

    @Column(name = "out_checked_fullname", updatable = false)
    private String outCheckedFullname;

	@OneToOne(targetEntity = AssignmentGroupEntity.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "assignment_group_uuid", nullable = false)
	private AssignmentGroupEntity assignmentGroup;

	@OneToOne(targetEntity = FileMetadataEntity.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "file_metadata_uuid", nullable = false)
	private FileMetadataEntity file;

}