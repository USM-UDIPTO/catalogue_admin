package com.dxc.eproc.catalogue.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

// TODO: Auto-generated Javadoc
/**
 * The class SpecificationSearchDTO.
 */
public class SpecificationSearchDTO {

	/** The dept id. */
	@NotNull(message = "{specification.deptId.NotNull}")
	private Integer deptId;

	/** The spec name. */
	@Size(max = 200, message = "{specification.specName.Size}")
	@Pattern(regexp = "[A-Za-z][A-Za-z0-9 _]*", message = "{specification.specName.Pattern}")
	private String specName;

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
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "SpecificationSearchDTO [deptId=" + deptId + ", specName=" + specName + "]";
	}
}
