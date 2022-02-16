package com.dongkap.activity.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@EqualsAndHashCode(callSuper=false, exclude={"businessPartner", "corporate"})
@ToString(exclude={"businessPartner", "corporate"})
@Entity
@Table(name = "assignment_group", schema = SchemaDatabase.ACTIVITY)
public class AssignmentGroupEntity extends BaseAuditEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1932022761237540822L;

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(name = "assignment_group_uuid", nullable = false, unique = true)
	private String id;

	@Column(name = "total_assignment", nullable = false)
	private Integer totalAssignment = 0;

	@Column(name = "bp_uuid", nullable = false)
	private String bpId;

	@OneToOne(targetEntity = BusinessPartnerEntity.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "bp_uuid", nullable = false, insertable = false, updatable = false)
	private BusinessPartnerEntity businessPartner;

	@ManyToOne(targetEntity = CorporateEntity.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "corporate_uuid", nullable = false)
	private CorporateEntity corporate;

	@OneToMany(mappedBy = "assignmentGroup", fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@Fetch(FetchMode.JOIN)
	private Set<AssignmentEntity> assignments = new HashSet<AssignmentEntity>();

}