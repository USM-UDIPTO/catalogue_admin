package com.dxc.eproc.catalogue.model;

// TODO: Auto-generated Javadoc
/**
 * The Class UnspscDetails.
 */
public class UnspscDetails {

	/** The unspsc code. */
	private String unspscCode;

	/** The unspsc title. */
	private String unspscTitle;

	/** The unspsc type. */
	private String unspscType;

	/**
	 * Instantiates a new unspsc details.
	 */
	public UnspscDetails() {
	}

	/**
	 * Instantiates a new unspsc details.
	 *
	 * @param unspscCode  the unspsc code
	 * @param unspscTitle the unspsc title
	 * @param unspscType  the unspsc type
	 */
	public UnspscDetails(String unspscCode, String unspscTitle, String unspscType) {
		this.unspscCode = unspscCode;
		this.unspscTitle = unspscTitle;
		this.unspscType = unspscType;
	}

	/**
	 * Gets the unspsc code.
	 *
	 * @return the unspsc code
	 */
	public String getUnspscCode() {
		return unspscCode;
	}

	/**
	 * Sets the unspsc code.
	 *
	 * @param unspscCode the new unspsc code
	 */
	public void setUnspscCode(String unspscCode) {
		this.unspscCode = unspscCode;
	}

	/**
	 * Gets the unspsc title.
	 *
	 * @return the unspsc title
	 */
	public String getUnspscTitle() {
		return unspscTitle;
	}

	/**
	 * Sets the unspsc title.
	 *
	 * @param unspscTitle the new unspsc title
	 */
	public void setUnspscTitle(String unspscTitle) {
		this.unspscTitle = unspscTitle;
	}

	/**
	 * Gets the unspsc type.
	 *
	 * @return the unspsc type
	 */
	public String getUnspscType() {
		return unspscType;
	}

	/**
	 * Sets the unspsc type.
	 *
	 * @param unspscType the new unspsc type
	 */
	public void setUnspscType(String unspscType) {
		this.unspscType = unspscType;
	}
}
