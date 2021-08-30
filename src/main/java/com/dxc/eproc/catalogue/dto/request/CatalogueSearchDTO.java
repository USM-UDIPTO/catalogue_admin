/**
 * 
 */
package com.dxc.eproc.catalogue.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

// TODO: Auto-generated Javadoc
/**
 * The class CatalogueSearchDTO.
 */
public class CatalogueSearchDTO {

	/** The dept id. */
	@NotNull(message = "{catalogue.deptId.NotNull}")
	private Integer deptId;

	/** The catalogue code. */
	@Size(max = 25, message = "{catalogue.catalogueCode.Size}")
	@Pattern(regexp = "[A-Za-z0-9_]*", message = "{catalogue.catalogueCode.Pattern}")
	private String catalogueCode;

	/** The catalogue name. */
	@Size(max = 200, message = "{catalogue.catalogueName.Size}")
	@Pattern(regexp = "[A-Za-z0-9 _]*", message = "{catalogue.catalogueName.Pattern}")
	private String catalogueName;

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
	 * Gets the catalogueCode.
	 * 
	 * @return catalogueCode
	 */
	public String getCatalogueCode() {
		return catalogueCode;
	}

	/**
	 * Sets the catalogueCode.
	 * 
	 * @param catalogueCode the new catalogueCode
	 */
	public void setCatalogueCode(String catalogueCode) {
		this.catalogueCode = catalogueCode;
	}

	/**
	 * Gets the catalogueName.
	 * 
	 * @return catalogueName
	 */
	public String getCatalogueName() {
		return catalogueName;
	}

	/**
	 * Sets the catalogueName.
	 * 
	 * @param catalogueName the new catalogueName
	 */
	public void setCatalogueName(String catalogueName) {
		this.catalogueName = catalogueName;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "CatalogueSearchDTO [deptId=" + deptId + ", catalogueCode=" + catalogueCode + ", catalogueName="
				+ catalogueName + "]";
	}
}
