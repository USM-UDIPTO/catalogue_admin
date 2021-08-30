package com.dxc.eproc.catalogue.documentstore;

// TODO: Auto-generated Javadoc
/**
 * The Enum ReferenceTypes.
 */
public enum ReferenceTypes {

	/** The catalogue upload file. */
	CATALOGUE_UPLOAD_FILE("CATALOGUE_UPLOAD_FILE"),

	/** The segment. */
	SEGMENT("SEGMENT"),

	/** The family. */
	FAMILY("FAMILY"),

	/** The class. */
	CLASS("CLASS"),

	/** The commodity. */
	COMMODITY("COMMODITY");

	/** The value. */
	private String value;

	/**
	 * Instantiates a new reference types.
	 *
	 * @param value the value
	 */
	ReferenceTypes(String value) {
		this.value = value;
	}

}
