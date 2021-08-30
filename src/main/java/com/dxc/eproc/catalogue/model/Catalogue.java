package com.dxc.eproc.catalogue.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

// TODO: Auto-generated Javadoc
/**
 * The Class CatalogueModel.
 */
@Entity
@Table(name = "catalogue")
@DynamicUpdate
public class Catalogue extends EProcModel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The catalogue id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/** The catalogueCode. */
	@Column(name = "catalogue_code")
	private String catalogueCode;

	/** The catalogueName. */
	@Column(name = "catalogue_name")
	private String catalogueName;

	/** The activeYn. */
	@Column(name = "active_yn")
	private Boolean activeYn;

	/** The deptId. */
	@Column(name = "dept_id")
	private Integer deptId;

	/** The deptName. */
	@Column(name = "dept_name")
	private String deptName;

	/** The categories. */
	@OneToMany(mappedBy = "catalogue")
	private List<Category> categories;

	/**
	 * Gets the Id.
	 *
	 * @return the Id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the Id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the catalogueCode.
	 *
	 * @return the catalogueCode
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
	 * @return the catalogueName
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
	 * Gets the isActiveYn.
	 *
	 * @return the isActiveYn
	 */

	public Boolean getActiveYn() {
		return activeYn;
	}

	/**
	 * Sets the isActiveYn.
	 *
	 * @param activeYn the activeYn
	 */
	public void setActiveYn(Boolean activeYn) {
		this.activeYn = activeYn;
	}

	/**
	 * Gets the deptId.
	 *
	 * @return the deptId
	 */

	public Integer getDeptId() {
		return deptId;
	}

	/**
	 * Sets the deptId.
	 *
	 * @param deptId the new dept id
	 */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	/**
	 * Gets the deptName.
	 *
	 * @return the deptName
	 */

	public String getDeptName() {
		return deptName;
	}

	/**
	 * Sets the deptName.
	 *
	 * @param deptName the new dept name
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * Gets the categories.
	 *
	 * @return the categories
	 */
	public List<Category> getCategories() {
		return categories;
	}

	/**
	 * Sets the categories.
	 *
	 * @param categories the new categories
	 */
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "CatalogueModel [id=" + id + ", catalogueCode=" + catalogueCode + ", catalogueName=" + catalogueName
				+ ", activeYn=" + activeYn + ", deptId=" + deptId + ", deptName=" + deptName + "]";
	}
}
