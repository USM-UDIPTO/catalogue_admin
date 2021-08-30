package com.dxc.eproc.catalogue.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

// TODO: Auto-generated Javadoc
/**
 * The Class CategoryDTO.
 */
public class CategoryDTO extends EprocModelDTO {

	/** The id. */
	private Long id;

	/** The category code. */
	@NotBlank(message = "{category.categoryCode.NotBlank}")
	@Size(max = 25, message = "{category.categoryCode.Size}")
	@Pattern(regexp = "[A-Za-z0-9 _[-()/]]*", message = "{category.categoryCode.Pattern}")
	private String categoryCode;

	/** The category name. */
	@NotBlank(message = "{category.categoryName.NotBlank}")
	@Size(max = 200, message = "{category.categoryName.Size}")
	@Pattern(regexp = "[A-Za-z0-9 _[-()/!@#$%^*&]]*", message = "{category.categoryName.Pattern}")
	private String categoryName;

	/** The active yn. */
	private Boolean activeYn;

	/** The catalogue id. */
	@JsonIgnore
	private CatalogueDTO catalogue;

	/** The catalogue id. */
	private Long catalogueId;

	/** The Catalogue name. */
	private String catalogueName;

	/** The dept id. */
	@NotNull(message = "{category.deptId.NotNull}")
	private Integer deptId;

	/** The dept name. */
	@NotBlank(message = "{category.deptName.NotBlank}")
	@Size(max = 200, message = "{category.deptName.Size}")
	@Pattern(regexp = "[A-Za-z ]*", message = "{category.deptName.Pattern}")
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
	 * Gets the category code.
	 *
	 * @return the category code
	 */
	public String getCategoryCode() {
		return categoryCode;
	}

	/**
	 * Sets the category code.
	 *
	 * @param categoryCode the new category code
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	/**
	 * Gets the category name.
	 *
	 * @return the category name
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * Sets the category name.
	 *
	 * @param categoryName the new category name
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
	 * Gets the catalogue id.
	 *
	 * @return the catalogue id
	 */
	public CatalogueDTO getCatalogue() {
		return catalogue;
	}

	/**
	 * Sets the Catalogue.
	 *
	 * @param catalogue the catalogue
	 */
	public void setCatalogue(CatalogueDTO catalogue) {
		this.catalogue = catalogue;
	}

	/**
	 * Gets the catalogue.
	 *
	 * @return the catalogue
	 */
	public Long getCatalogueId() {
		return catalogueId;
	}

	/**
	 * Sets the catalogue id.
	 *
	 * @param catalogueId the new catalogue Id
	 */
	public void setCatalogueId(Long catalogueId) {
		this.catalogueId = catalogueId;
	}

	/**
	 * Gets the catalogue name.
	 *
	 * @return the catalogue name
	 */
	public String getCatalogueName() {
		return catalogueName;
	}

	/**
	 * Sets the catalogue name.
	 *
	 * @param catalogueName the new catalogue name
	 */
	public void setCatalogueName(String catalogueName) {
		this.catalogueName = catalogueName;
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
		return "CategoryDTO [id=" + id + ", categoryCode=" + categoryCode + ", categoryName=" + categoryName
				+ ", activeYn=" + activeYn + ", catalogueId=" + catalogueId + ", catalogueName=" + catalogueName
				+ ", deptId=" + deptId + ", deptName=" + deptName + "]";
	}
}
