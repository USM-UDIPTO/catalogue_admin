package com.dxc.eproc.catalogue.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

// TODO: Auto-generated Javadoc
/**
 * The Class CommodityMasterDTO.
 */
public class CommodityMasterDTO {

	/** The id. */
	private Long id;

	/** The String commodity code. */
	@NotBlank(message = "{commodity.code.NotBlank}")
	@Size(max = 25, message = "{commodity.code.Size}")
	@Pattern(regexp = "[0-9]*", message = "{commodity.code.Pattern}")
	private String code;

	/** The String commodity title. */
	@NotBlank(message = "{commodity.title.NotBlank}")
	@Size(max = 100, message = "{commodity.title.Size}")
	@Pattern(regexp = "[A-Za-z0-9_ ]*", message = "{commodity.title.Pattern}")
	private String title;

	/** The class id. */
	private Long classId;

	/** The class master. */
	private ClassMasterDTO classMaster;

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
	 * @param id the new id
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
	 * @param code the new code
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
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the class id.
	 *
	 * @return the class id
	 */
	public Long getClassId() {
		return classId;
	}

	/**
	 * Sets the class id.
	 *
	 * @param classId the new class id
	 */
	public void setClassId(Long classId) {
		this.classId = classId;
	}

	/**
	 * Gets the class master.
	 *
	 * @return the class master
	 */
	public ClassMasterDTO getClassMaster() {
		return classMaster;
	}

	/**
	 * Sets the class master.
	 *
	 * @param classMaster the new class master
	 */
	public void setClassMaster(ClassMasterDTO classMaster) {
		this.classMaster = classMaster;
	}

}