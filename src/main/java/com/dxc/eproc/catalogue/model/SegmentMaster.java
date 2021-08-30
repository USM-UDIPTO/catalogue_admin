package com.dxc.eproc.catalogue.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

// TODO: Auto-generated Javadoc
/**
 * The Class SegmentMaster.
 */
@Entity
@DynamicUpdate
@Table(name = "unspsc_segment_master")
public class SegmentMaster {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/** The code. */
	@Column(name = "code")
	private String code;

	/** The title. */
	@Column(name = "title")
	private String title;

	/** The family master. */
	@OneToMany(mappedBy = "segmentMaster")
	private List<FamilyMaster> families;

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
	public List<FamilyMaster> getFamilies() {
		return families;
	}

	/**
	 * Sets the families.
	 *
	 * @param families the new families
	 */
	public void setFamilies(List<FamilyMaster> families) {
		this.families = families;
	}
}
