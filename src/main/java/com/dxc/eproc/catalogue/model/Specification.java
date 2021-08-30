package com.dxc.eproc.catalogue.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

// TODO: Auto-generated Javadoc
/**
 * The Class Specification.
 */
@Entity
@Table(name = "specification")
@DynamicUpdate
public class Specification extends EProcModel {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The spec name. */
	@Column(name = "spec_name")
	private String specName;

	/** The active yn. */
	@Column(name = "active_yn")
	private Boolean activeYn;

	/** The dept id. */
	@Column(name = "dept_id")
	private Integer deptId;

	/** The dept name. */
	@Column(name = "dept_name")
	private String deptName;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the spec name.
	 *
	 * @return the spec name
	 */
	public String getSpecName() {
		return specName;
	}

	/**
	 * Sets the spec name.
	 *
	 * @param specName the new spec name
	 */
	public void setSpecName(String specName) {
		this.specName = specName;
	}

	/**
	 * Gets the active yn.
	 *
	 * @return the active yn
	 */
	public Boolean getActiveYn() {
		return activeYn;
	}

	/**
	 * Sets the active yn.
	 *
	 * @param activeYn the new active yn
	 */
	public void setActiveYn(Boolean activeYn) {
		this.activeYn = activeYn;
	}

	/**
	 * Gets the dept id.
	 *
	 * @return the dept id
	 */
	public Integer getDeptId() {
		return deptId;
	}

	/**
	 * Sets the dept id.
	 *
	 * @param deptId the new dept id
	 */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	/**
	 * Gets the dept name.
	 *
	 * @return the dept name
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * Sets the dept name.
	 *
	 * @param deptName the new dept name
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Specification [id=" + id + ", specName=" + specName + ", activeYn=" + activeYn + ", deptId=" + deptId
				+ ", deptName=" + deptName + "]";
	}
}
