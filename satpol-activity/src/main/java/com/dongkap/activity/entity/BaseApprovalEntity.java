package com.dongkap.activity.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseApprovalEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -810070551326900243L;

	@Column(name = "auto_approved", nullable = false)
    protected boolean autoApproved = true;

	@Column(name = "is_approved", nullable = false)
    protected boolean approved = true;

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="approved_date")
    @CreatedDate
    protected Date approvedDate;

	@Column(name = "approved_by") 
    @CreatedBy
    protected String approvedBy;

	@Column(name = "version", nullable = false)
	@Version
	protected Integer version = 1;

	@Column(name = "is_active", nullable = false)
    protected boolean active = true;

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_date", updatable = false)
    @CreatedDate
    protected Date createdDate;

	@Column(name = "created_by", updatable = false) 
    @CreatedBy
    protected String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="modified_date", insertable = false)
    @LastModifiedDate
    protected Date modifiedDate;

    @Column(name = "modified_by", insertable = false)
    @LastModifiedBy
    protected String modifiedBy;

	public void updateTimeStamps() {
		modifiedDate = new Date();
		if (createdDate == null) {
			createdDate = new Date();
		}
	}
	
	public void setActive(String activeSts) {
		if (activeSts != null && activeSts.equals("Active")) {
			this.active = true;
		} else if (activeSts != null && activeSts.equals("Deactivated")) {
			this.active = false;
		}
	}
	
	public void setActive(Boolean active) {
		this.active = active;
	}

}
