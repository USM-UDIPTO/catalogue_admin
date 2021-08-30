package com.dxc.eproc.catalogue.dto.request;

// TODO: Auto-generated Javadoc
/**
 * The Class CategoryDetailsDTO.
 */
public class CategoryDetailsDTO {

	/** The category code. */
	private String categoryCode;

	/** The category name. */
	private String categoryName;

	/**
	 * Instantiates a new category details DTO.
	 */
	public CategoryDetailsDTO() {
	}

	/**
	 * Instantiates a new category details DTO.
	 *
	 * @param categoryCode the category code
	 * @param categoryName the category name
	 */
	public CategoryDetailsDTO(String categoryCode, String categoryName) {
		this.categoryCode = categoryCode;
		this.categoryName = categoryName;
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
}
