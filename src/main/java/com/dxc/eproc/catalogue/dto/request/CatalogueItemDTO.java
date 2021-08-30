package com.dxc.eproc.catalogue.dto.request;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

// TODO: Auto-generated Javadoc
/**
 * The Class CatalogueItemDTO.
 */
public class CatalogueItemDTO extends EprocModelDTO {

	/** The id. */
	private Long id;

	/** The item code. */
	@NotBlank(message = "{catalogueItem.itemCode.NotBlank}")
	@Size(max = 25, message = "{catalogueItem.itemCode.Size}")
	@Pattern(regexp = "[a-zA-Z0-9 _[-()/]]*", message = "{catalogueItem.itemCode.Pattern}")
	private String itemCode;

	/** The item name. */
	@NotBlank(message = "{catalogueItem.itemName.NotBlank}")
	private String itemName;

	/** The part number. */
	private String partNumber;

	/** The uom id. */
	@NotNull(message = "{catalogueItem.uomId.NotNull}")
	private Long uomId;

	/** The uom name. */
	@NotBlank(message = "{catalogueItem.uomName.NotBlank}")
	@Size(max = 100, message = "{catalogueItem.uomName.Size}")
	private String uomName;

	/** The active yn. */
	private Boolean activeYn;

	/** The catalogue code. */
	private String catalogueCode;

	/** The catalogue name. */
	private String catalogueName;

	/** The categories. */
	private List<CategoryDetailsDTO> categories;

	/** The unspsc details. */
	private List<UnspscDetailsDTO> unspscDetails;

	/** The unspsc item code. */
	private String unspscItemCode;

	/** The specifications. */
	private Map<@Pattern(regexp = "[A-Za-z0-9 _[-()/!@#$%^*&]]*", message = "{specification.specName.Pattern}") String, String> specifications;

	/** The dept id. */
	@NotNull(message = "{catalogueItem.deptId.NotNull}")
	private Integer deptId;

	/** The dept name. */
	@NotBlank(message = "{catalogueItem.deptName.NotBlank}")
	@Size(max = 200, message = "{catalogueItem.deptName.Size}")
	@Pattern(regexp = "[A-Za-z ]*", message = "{catalogueItem.deptName.Pattern}")
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
	 * Gets the uom name.
	 *
	 * @return the uom name
	 */
	public String getUomName() {
		return uomName;
	}

	/**
	 * Sets the uom name.
	 *
	 * @param uomName the new uom name
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
	 * Gets the categories.
	 *
	 * @return the categories
	 */
	public List<CategoryDetailsDTO> getCategories() {
		return categories;
	}

	/**
	 * Sets the categories.
	 *
	 * @param categories the new categories
	 */
	public void setCategories(List<CategoryDetailsDTO> categories) {
		this.categories = categories;
	}

	/**
	 * Gets the unspsc details.
	 *
	 * @return the unspsc details
	 */
	public List<UnspscDetailsDTO> getUnspscDetails() {
		return unspscDetails;
	}

	/**
	 * Sets the unspsc details.
	 *
	 * @param unspscDetails the new unspsc details
	 */
	public void setUnspscDetails(List<UnspscDetailsDTO> unspscDetails) {
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
