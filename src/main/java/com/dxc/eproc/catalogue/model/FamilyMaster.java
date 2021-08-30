package com.dxc.eproc.catalogue.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

// TODO: Auto-generated Javadoc
/**
 * The Class FamilyMaster.
 */
@Entity
@DynamicUpdate
@Table(name = "unspsc_family_master")
public class FamilyMaster {

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

	/** The segment master. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "segment_id")
	private SegmentMaster segmentMaster;

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
	 * Gets the segment master.
	 *
	 * @return the segmentMaster
	 */
	public SegmentMaster getSegmentMaster() {
		return segmentMaster;
	}

	/**
	 * Sets the segment master.
	 *
	 * @param segmentMaster the segmentMaster to set
	 */
	public void setSegmentMaster(SegmentMaster segmentMaster) {
		this.segmentMaster = segmentMaster;
	}

}
