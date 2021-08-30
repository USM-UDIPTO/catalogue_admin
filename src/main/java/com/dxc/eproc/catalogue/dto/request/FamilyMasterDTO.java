package com.dxc.eproc.catalogue.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

// TODO: Auto-generated Javadoc
/**
 * The Class FamilyMasterDTO.
 */
public class FamilyMasterDTO {

	/** The id. */
	private Long id;

	/** The code. */
	@NotBlank(message = "{family.code.NotBlank}")
	@Size(max = 25, message = "{family.code.Size}")
	@Pattern(regexp = "[0-9]*", message = "{family.code.Pattern}")
	private String code;

	/** The title. */
	@NotBlank(message = "{family.title.NotBlank}")
	@Size(max = 100, message = "{family.title.Size}")
	@Pattern(regexp = "[A-Za-z0-9_ ]*", message = "{family.title.Pattern}")
	private String title;

	/** The segment id. */
	private Long segmentId;

	/** The segment master. */
	private SegmentMasterDTO segmentMaster;

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
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the segment id.
	 *
	 * @return the segment id
	 */
	public Long getSegmentId() {
		return segmentId;
	}

	/**
	 * Sets the segment id.
	 *
	 * @param segmentId the new segment id
	 */
	public void setSegmentId(Long segmentId) {
		this.segmentId = segmentId;
	}

	/**
	 * Gets the segment master.
	 *
	 * @return the segment master
	 */
	public SegmentMasterDTO getSegmentMaster() {
		return segmentMaster;
	}

	/**
	 * Sets the segment master.
	 *
	 * @param segmentMaster the new segment master
	 */
	public void setSegmentMaster(SegmentMasterDTO segmentMaster) {
		this.segmentMaster = segmentMaster;
	}

}
