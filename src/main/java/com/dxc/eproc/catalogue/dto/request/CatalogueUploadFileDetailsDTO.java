package com.dxc.eproc.catalogue.dto.request;

// TODO: Auto-generated Javadoc
/**
 * The Class CatalogueUploadFileDetailsDTO.
 */
public class CatalogueUploadFileDetailsDTO extends EprocModelDTO {

	/**
	 * The constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The catalogueFileId. */
	private Long catalogueFileId;

	/** The catalogueCode. */
	private String catalogueCode;

	/** The itemCode. */
	private String itemCode;

	/** The itemName. */
	private String itemName;

	/** The uom. */
	private String uom;

	/** The categoryCode. */
	private String categoryCode;

	/** The status. */
	private String status;

	/** The errorReason. */
	private String errorReason;

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
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the catalogue file id.
	 *
	 * @return the catalogueFileId
	 */
	public Long getCatalogueFileId() {
		return catalogueFileId;
	}

	/**
	 * Sets the catalogue file id.
	 *
	 * @param catalogueFileId the catalogueFileId to set
	 */
	public void setCatalogueFileId(Long catalogueFileId) {
		this.catalogueFileId = catalogueFileId;
	}

	/**
	 * Gets the catalogue code.
	 *
	 * @return the catalogueCode
	 */
	public String getCatalogueCode() {
		return catalogueCode;
	}

	/**
	 * Sets the catalogue code.
	 *
	 * @param catalogueCode the catalogueCode to set
	 */
	public void setCatalogueCode(String catalogueCode) {
		this.catalogueCode = catalogueCode;
	}

	/**
	 * Gets the itemCode.
	 *
	 * @return the itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * Sets the itemCode.
	 *
	 * @param itemCode the new item code
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * Gets the itemName.
	 *
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * Sets the itemName.
	 *
	 * @param itemName the new item name
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * Gets the uom.
	 *
	 * @return the uom
	 */
	public String getUom() {
		return uom;
	}

	/**
	 * Sets the uom.
	 *
	 * @param uom the new uom
	 */
	public void setUom(String uom) {
		this.uom = uom;
	}

	/**
	 * Gets the categoryCode.
	 *
	 * @return the categoryCode
	 */
	public String getCategoryCode() {
		return categoryCode;
	}

	/**
	 * Sets the categoryCode.
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
	 * Gets the errorReason.
	 *
	 * @return the errorReason
	 */
	public String getErrorReason() {
		return errorReason;
	}

	/**
	 * Sets the errorReason.
	 *
	 * @param errorReason the new error reason
	 */
	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}

}
