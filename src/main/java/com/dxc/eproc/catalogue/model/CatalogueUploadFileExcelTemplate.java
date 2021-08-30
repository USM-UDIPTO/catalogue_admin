package com.dxc.eproc.catalogue.model;

// TODO: Auto-generated Javadoc
//TODO: Auto-generated Javadoc
/**
 * The Class CatalogueItemFileExcelTemplate.
 */
public class CatalogueUploadFileExcelTemplate {

	/** The s no. */
	private Long sNo;

	/** The item code. */
	private String itemCode;

	/** The item name. */
	private String itemName;

	/** The unit. */
	private String unit;

	/** The category code. */
	private String categoryCode;

	/** The status. */
	private String status;

	/** The errorReason. */
	private String errorReason;

	/**
	 * Instantiates a new catalogue item file excel template.
	 */
	public CatalogueUploadFileExcelTemplate() {
	}

	/**
	 * Gets the s no.
	 *
	 * @return the s no
	 */
	public Long getsNo() {
		return sNo;
	}

	/**
	 * Sets the s no.
	 *
	 * @param sNo the new s no
	 */
	public void setsNo(Long sNo) {
		this.sNo = sNo;
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
	 * Gets the unit.
	 *
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * Sets the unit.
	 *
	 * @param unit the new unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
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
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the error reason.
	 *
	 * @return the errorReason
	 */
	public String getErrorReason() {
		return errorReason;
	}

	/**
	 * Sets the error reason.
	 *
	 * @param errorReason the errorReason to set
	 */
	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "CatalogueUploadFileExcelTemplate [sNo=" + sNo + ", itemCode=" + itemCode + ", itemName=" + itemName
				+ ", unit=" + unit + ", categoryCode=" + categoryCode + ", status=" + status + ", errorReason="
				+ errorReason + "]";
	}

}