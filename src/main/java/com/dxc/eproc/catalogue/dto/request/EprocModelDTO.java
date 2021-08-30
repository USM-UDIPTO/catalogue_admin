package com.dxc.eproc.catalogue.dto.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

// TODO: Auto-generated Javadoc
/** The class EprocModelDTO. */
public class EprocModelDTO {

	/** The createdTs. */
	@JsonIgnore
	protected Date createdTs;
	/** The createdBy. */
	@JsonIgnore
	protected String createdBy;
	/** The lastModifiedTs. */
	@JsonIgnore
	protected Date lastModifiedTs;
	/** The lastModifiedBy. */
	@JsonIgnore
	protected String lastModifiedBy;
	/** The version. */
	@JsonIgnore
	protected Integer version;

	/**
	 * Gets the createdBy.
	 *
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the createdBy.
	 *
	 * @param createdBy the createdBy
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the createdTS.
	 *
	 * @return the createdTs
	 */
	public Date getCreatedTs() {
		return createdTs;
	}

	/**
	 * Sets the createdTs.
	 *
	 * @param createdTs the createdTS
	 */
	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	/**
	 * Gets the lastModifiedBy.
	 *
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * Sets the lastModifiedBy.
	 *
	 * @param lastModifiedBy the lastModifiedBy
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * Gets the lastModifiedTs.
	 *
	 * @return the lastModifiedTs
	 */
	public Date getLastModifiedTs() {
		return lastModifiedTs;
	}

	/**
	 * Sets the lastModifiedTs.
	 *
	 * @param lastModifiedTs the lastModifiedTs
	 */
	public void setLastModifiedTs(Date lastModifiedTs) {
		this.lastModifiedTs = lastModifiedTs;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version the version
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

}