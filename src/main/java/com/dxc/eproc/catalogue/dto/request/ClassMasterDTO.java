package com.dxc.eproc.catalogue.dto.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

// TODO: Auto-generated Javadoc
/**
 * The Class ClassMasterDTO.
 */
public class ClassMasterDTO {

	/** The id. */
	private Long id;

	/** The String class code. */
	@NotBlank(message = "{class.code.NotBlank}")
	@Size(max = 25, message = "{class.code.Size}")
	@Pattern(regexp = "[0-9]*", message = "{class.code.Pattern}")
	private String code;

	/** The String class title. */
	@NotBlank(message = "{class.title.NotBlank}")
	@Size(max = 100, message = "{class.title.Size}")
	@Pattern(regexp = "[A-Za-z0-9_ ]*", message = "{class.title.Pattern}")
	private String title;

	/** The commodities. */
	private List<CommodityMasterDTO> commodities;

	/** The family master. */
	private FamilyMasterDTO familyMaster;

	/** The familyid. */
	private Long familyId;

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
	 * Gets the commodities.
	 *
	 * @return the commodities
	 */
	public List<CommodityMasterDTO> getCommodities() {
		return commodities;
	}

	/**
	 * Sets the commodities.
	 *
	 * @param commodities the new commodities
	 */
	public void setCommodities(List<CommodityMasterDTO> commodities) {
		this.commodities = commodities;
	}

	/**
	 * Gets the family master.
	 *
	 * @return the family master
	 */
	public FamilyMasterDTO getFamilyMaster() {
		return familyMaster;
	}

	/**
	 * Sets the family master.
	 *
	 * @param familyMaster the new family master
	 */
	public void setFamilyMaster(FamilyMasterDTO familyMaster) {
		this.familyMaster = familyMaster;
	}

	/**
	 * Gets the family id.
	 *
	 * @return the family id
	 */
	public Long getFamilyId() {
		return familyId;
	}

	/**
	 * Sets the family id.
	 *
	 * @param familyId the new family id
	 */
	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}

}
