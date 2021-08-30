package com.dxc.eproc.catalogue.dto.request;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class SpecificationItemDTO.
 */
public class SpecificationItemDTO {

	/** The item code. */
	private String itemCode;

	/** The item name. */
	private String itemName;

	/** The spec details. */
	private List<SpecificationDetailsDTO> specDetails;

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
	 * Gets the spec details.
	 *
	 * @return the spec details
	 */
	public List<SpecificationDetailsDTO> getSpecDetails() {
		return specDetails;
	}

	/**
	 * Sets the spec details.
	 *
	 * @param specDetails the new spec details
	 */
	public void setSpecDetails(List<SpecificationDetailsDTO> specDetails) {
		this.specDetails = specDetails;
	}
}
