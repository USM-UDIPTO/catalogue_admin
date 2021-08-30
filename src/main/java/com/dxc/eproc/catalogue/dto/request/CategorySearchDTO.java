/**
 * 
 */
package com.dxc.eproc.catalogue.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

// TODO: Auto-generated Javadoc
/**
 * The class CategorySearchDTO.
 */
public class CategorySearchDTO {

	/** The dept id. */
	@NotNull(message = "{category.deptId.NotNull}")
	private Integer deptId;

	/** The category code. */
	@Size(max = 25, message = "{category.categoryCode.Size}")
	@Pattern(regexp = "[A-Za-z0-9_]*", message = "{category.categoryCode.Pattern}")
	private String categoryCode;

	/** The category name. */
	@Size(max = 200, message = "{category.categoryName.Size}")
	@Pattern(regexp = "[A-Za-z0-9 _]*", message = "{category.categoryName.Pattern}")
	private String categoryName;

	/**
	 * Instantiates a new categorySearchDTO.
	 */
	public CategorySearchDTO() {
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
	 * Gets the categoryCode.
	 * 
	 * @return categoryCode
	 */
	public String getCategoryCode() {
		return categoryCode;
	}

	/**
	 * Sets the categoryCode.
	 * 
	 * @param categoryCode the new categoryCode
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	/**
	 * Gets the categoryName.
	 * 
	 * @return categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * Sets the categoryName.
	 * 
	 * @param categoryName the new categoryName
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "CategorySearchDTO [deptId=" + deptId + ", categoryCode=" + categoryCode + ", categoryName="
				+ categoryName + "]";
	}
}
