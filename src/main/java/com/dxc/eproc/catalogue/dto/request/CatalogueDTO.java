package com.dxc.eproc.catalogue.dto.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

// TODO: Auto-generated Javadoc
/**
 * The Class CatalogueDTO.
 */
public class CatalogueDTO extends EprocModelDTO {

	/** The id. */
	private Long id;

	/** The String catalogue code. */
	@NotBlank(message = "{catalogue.catalogueCode.NotBlank}")
	@Size(max = 25, message = "{catalogue.catalogueCode.Size}")
	@Pattern(regexp = "[A-Za-z0-9 _[-()/]]*", message = "{catalogue.catalogueCode.Pattern}")
	private String catalogueCode;

	/** The String catalogue name. */
	@NotBlank(message = "{catalogue.catalogueName.NotBlank}")
	@Size(max = 200, message = "{catalogue.catalogueName.Size}")
	@Pattern(regexp = "[A-Za-z0-9 _[-()/!@#$%^*&]]*", message = "{catalogue.catalogueName.Pattern}")
	private String catalogueName;

	/** The boolean activeYn. */

	private Boolean activeYn;

	/** The Integer department Id. */
	@NotNull(message = "{catalogue.deptId.NotNull}")
	private Integer deptId;

	/** The Integer department Name. */
	@NotBlank(message = "{catalogue.deptName.NotBlank}")
	@Size(max = 200, message = "{catalogue.deptName.Size}")
	@Pattern(regexp = "[A-Za-z ]*", message = "{catalogue.deptName.Pattern}")
	private String deptName;

	/** The categories. */
	@JsonIgnore
	private List<CategoryDTO> categories;

	/**
	 * Gets the catalogueCode.
	 * 
	 * @return catalogueCode
	 */
	public String getCatalogueCode() {
		return catalogueCode;
	}

	/**
	 * Gets the id.
	 * 
	 * @return id
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
	 * @param catalogueName the new catalogue name
	 */
	public void setCatalogueName(String catalogueName) {
		this.catalogueName = catalogueName;
	}

	/**
	 * Gets the activeYn.
	 * 
	 * @return activeYn
	 */
	public Boolean getActiveYn() {
		return activeYn;
	}

	/**
	 * Sets the activeYn.
	 * 
	 * @param activeYn the new activeYn
	 */
	public void setActiveYn(Boolean activeYn) {
		this.activeYn = activeYn;
	}

	/**
	 * Gets the deptId.
	 * 
	 * @return deptId
	 */
	public Integer getDeptId() {
		return deptId;
	}

	/**
	 * Sets the deptId.
	 * 
	 * @param deptId the new deptId
	 */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	/**
	 * Gets the deptName.
	 * 
	 * @return deptName
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * Sets the deptName.
	 * 
	 * @param deptName the new deptName
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * Gets the categories.
	 * 
	 * @return the categories
	 */
	public List<CategoryDTO> getCategories() {
		return categories;
	}

	/**
	 * Sets the categories.
	 * 
	 * @param categories the new categories
	 */
	public void setCategories(List<CategoryDTO> categories) {
		this.categories = categories;
	}

	/**
	 * To string.
	 * 
	 * @return the string
	 */
	@Override
	public String toString() {
		return "CatalogueDTO [id=" + id + ", catalogueCode=" + catalogueCode + ", catalogueName=" + catalogueName
				+ ", activeYn=" + activeYn + ", deptId=" + deptId + ", deptName=" + deptName + "]";
	}

}
