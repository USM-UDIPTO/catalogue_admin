package com.dxc.eproc.catalogue.dto.request;

import java.util.List;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

// TODO: Auto-generated Javadoc
/**
 * The Class CatalogueItemSearchDTO.
 */
public class CatalogueItemSearchDTO {

	/** The catalogue code. */
	@Size(max = 25)
	@Pattern(regexp = "[A-Za-z0-9_]*")
	private String catalogueCode;

	/** The category code. */
	private List<String> categoryCodeList;

	/** The catalogue item code. */
	@Size(max = 25)
	@Pattern(regexp = "[A-Za-z0-9_]*")
	private String catalogueItemCode;

	/** The catalogue item name. */
	@Size(max = 25)
	@Pattern(regexp = "[A-Za-z0-9 _]*")
	private String catalogueItemName;

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
	 * Gets the category code list.
	 *
	 * @return the category code list
	 */
	public List<String> getCategoryCodeList() {
		return categoryCodeList;
	}

	/**
	 * Sets the category code list.
	 *
	 * @param categoryCodeList the new category code list
	 */
	public void setCategoryCodeList(List<String> categoryCodeList) {
		this.categoryCodeList = categoryCodeList;
	}

	/**
	 * Gets the catalogue item code.
	 *
	 * @return the catalogue item code
	 */
	public String getCatalogueItemCode() {
		return catalogueItemCode;
	}

	/**
	 * Sets the catalogue item code.
	 *
	 * @param catalogueItemCode the new catalogue item code
	 */
	public void setCatalogueItemCode(String catalogueItemCode) {
		this.catalogueItemCode = catalogueItemCode;
	}

	/**
	 * Gets the catalogue item name.
	 *
	 * @return the catalogue item name
	 */
	public String getCatalogueItemName() {
		return catalogueItemName;
	}

	/**
	 * Sets the catalogue item name.
	 *
	 * @param catalogueItemName the new catalogue item name
	 */
	public void setCatalogueItemName(String catalogueItemName) {
		this.catalogueItemName = catalogueItemName;
	}

}
