package com.dxc.eproc.catalogue.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

// TODO: Auto-generated Javadoc
/**
 * The Class SpecificationDTO.
 */
public class SpecificationDTO extends EprocModelDTO {

	/** The id. */
	private Long id;

	/** The spec name. */
	@NotBlank(message = "{specification.specName.NotBlank}")
	@Size(max = 200, message = "{specification.specName.Size}")
	@Pattern(regexp = "[A-Za-z0-9 _[-()/!@#$%^*&]]*", message = "{specification.specName.Pattern}")
	private String specName;

	/** The active yn. */
	private Boolean activeYn;

	/** The dept id. */
	@NotNull(message = "{specification.deptId.NotNull}")
	private Integer deptId;

	/** The dept name. */
	@NotBlank(message = "{specification.deptName.NotBlank}")
	@Size(max = 200, message = "{specification.deptName.Size}")
	@Pattern(regexp = "[a-zA-Z ]*", message = "{specification.deptName.Pattern}")
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
		return "SpecificationDTO [id=" + id + ", specName=" + specName + ", activeYn=" + activeYn + ", deptId=" + deptId
				+ ", deptName=" + deptName + "]";
	}
}
