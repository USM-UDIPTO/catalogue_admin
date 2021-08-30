package com.dxc.eproc.catalogue.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

// TODO: Auto-generated Javadoc
/**
 * The Class ClassMaster.
 */
@Entity
@DynamicUpdate
@Table(name = "unspsc_class_master")
public class ClassMaster {

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

	/** The commodities. */
	@OneToMany(mappedBy = "classMaster")
	private List<CommodityMaster> commodities;

	/** The family master. */
	@JoinColumn(name = "family_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private FamilyMaster familyMaster;

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
	public List<CommodityMaster> getCommodities() {
		return commodities;
	}

	/**
	 * Sets the commodities.
	 *
	 * @param commodities the new commodities
	 */
	public void setCommodities(List<CommodityMaster> commodities) {
		this.commodities = commodities;
	}

	/**
	 * Gets the family master.
	 *
	 * @return the family master
	 */
	public FamilyMaster getFamilyMaster() {
		return familyMaster;
	}

	/**
	 * Sets the family master.
	 *
	 * @param familyMaster the new family master
	 */
	public void setFamilyMaster(FamilyMaster familyMaster) {
		this.familyMaster = familyMaster;
	}
}
