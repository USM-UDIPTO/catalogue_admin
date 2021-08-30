package com.dxc.eproc.catalogue.dto.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

// TODO: Auto-generated Javadoc
/**
 * The Class SegmentMasterDTO.
 */
public class SegmentMasterDTO {

	/** The id. */
	private Long id;

	/** The code. */
	@NotBlank(message = "{segment.code.NotBlank}")
	@Size(max = 25, message = "{segment.code.Size}")
	@Pattern(regexp = "[0-9]*", message = "{segment.code.Pattern}")
	private String code;

	/** The title. */
	@NotBlank(message = "{segment.title.NotBlank}")
	@Size(max = 100, message = "{segment.title.Size}")
	@Pattern(regexp = "[A-Za-z0-9_ ]*", message = "{segment.title.Pattern}")
	private String title;

	/** The families. */
	private List<FamilyMasterDTO> families;

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
	 * Gets the families.
	 *
	 * @return the families
	 */
	public List<FamilyMasterDTO> getFamilies() {
		return families;
	}

	/**
	 * Sets the families.
	 *
	 * @param families the families to set
	 */
	public void setFamilies(List<FamilyMasterDTO> families) {
		this.families = families;
	}

}
