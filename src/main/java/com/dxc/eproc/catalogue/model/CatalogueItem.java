package com.dxc.eproc.catalogue.model;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

// TODO: Auto-generated Javadoc
/**
 * The Class CatalogueItem.
 */
@Entity
@Table(name = "catalogue_item")
@DynamicUpdate
public class CatalogueItem extends EProcModel {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/** The item code. */
	@Column(name = "item_code")
	private String itemCode;

	/** The item name. */
	@Column(name = "item_name")
	private String itemName;

	/** The part number. */
	@Column(name = "part_number")
	private String partNumber;

	/** The uom id. */
	@Column(name = "uom_id")
	private Long uomId;

	/** The unit of measurement. */
	@Column(name = "uom_name")
	private String uomName;

	/** The active yn. */
	@Column(name = "active_yn")
	private Boolean activeYn;

	/** The catalogue code. */
	@Column(name = "catalogue_code")
	private String catalogueCode;

	/** The catalogue name. */
	@Column(name = "catalogue_name")
	private String catalogueName;

	/** The specifications. */
	@Convert(converter = StringMapConverter.class)
	@Column(name = "specifications", columnDefinition = "TEXT")
	private Map<String, String> specifications;

	/** The categories. */
	@Convert(converter = CategoryDetailsListConverter.class)
	@Column(name = "categories", columnDefinition = "TEXT")
	private List<CategoryDetails> categories;

	/** The unspsc details. */
	@Convert(converter = UnspscDetailsListConverter.class)
	@Column(name = "unspsc_details", columnDefinition = "TEXT")
	private List<UnspscDetails> unspscDetails;

	/** The unspsc item code. */
	@Column(name = "unspsc_item_code")
	private String unspscItemCode;

	/** The dept id. */
	@Column(name = "dept_id")
	private Integer deptId;

	/** The dept name. */
	@Column(name = "dept_name")
	private String deptName;

	/**
	 * Instantiates a new catalogue item.
	 */
	public CatalogueItem() {
	}

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
	 * Gets the item code.
	 *
	 * @return the item code
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * Sets the item code.
	 *
	 * @param itemCode the new item code
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * Gets the specifications.
	 *
	 * @return the specifications
	 */
	public Map<String, String> getSpecifications() {
		return specifications;
	}

	/**
	 * Sets the specifications.
	 *
	 * @param specifications the specifications
	 */
	public void setSpecifications(Map<String, String> specifications) {
		this.specifications = specifications;
	}

	/**
	 * Gets the categories.
	 *
	 * @return the categories
	 */
	public List<CategoryDetails> getCategories() {
		return categories;
	}

	/**
	 * Sets the categories.
	 *
	 * @param categories the new categories
	 */
	public void setCategories(List<CategoryDetails> categories) {
		this.categories = categories;
	}

	/**
	 * Gets the unspsc details.
	 *
	 * @return the unspsc details
	 */
	public List<UnspscDetails> getUnspscDetails() {
		return unspscDetails;
	}

	/**
	 * Sets the unspsc details.
	 *
	 * @param unspscDetails the new unspsc details
	 */
	public void setUnspscDetails(List<UnspscDetails> unspscDetails) {
		this.unspscDetails = unspscDetails;
	}

	/**
	 * Gets the unspsc item code.
	 *
	 * @return the unspsc item code
	 */
	public String getUnspscItemCode() {
		return unspscItemCode;
	}

	/**
	 * Sets the unspsc item code.
	 *
	 * @param unspscItemCode the new unspsc item code
	 */
	public void setUnspscItemCode(String unspscItemCode) {
		this.unspscItemCode = unspscItemCode;
	}

	/**
	 * Gets the item name.
	 *
	 * @return the item name
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * Sets the item name.
	 *
	 * @param itemName the new item name
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * Gets the part number.
	 *
	 * @return the part number
	 */
	public String getPartNumber() {
		return partNumber;
	}

	/**
	 * Sets the part number.
	 *
	 * @param partNumber the new part number
	 */
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	/**
	 * Gets the uom id.
	 *
	 * @return the uom id
	 */
	public Long getUomId() {
		return uomId;
	}

	/**
	 * Sets the uom id.
	 *
	 * @param uomId the new uom id
	 */
	public void setUomId(Long uomId) {
		this.uomId = uomId;
	}

	/**
	 * Gets the uomName.
	 *
	 * @return the uomName
	 */
	public String getUomName() {
		return uomName;
	}

	/**
	 * Sets the uomName.
	 *
	 * @param uomName the new uomName
	 */
	public void setUomName(String uomName) {
		this.uomName = uomName;
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
	 * Gets the catalogue code.
	 *
	 * @return the catalogue code
	 */
	public String getCatalogueCode() {
		return catalogueCode;
	}

	/**
	 * Sets the catalogue code.
	 *
	 * @param catalogueCode the new catalogue code
	 */
	public void setCatalogueCode(String catalogueCode) {
		this.catalogueCode = catalogueCode;
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
}
