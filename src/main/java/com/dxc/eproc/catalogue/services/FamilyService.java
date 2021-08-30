package com.dxc.eproc.catalogue.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.catalogue.dto.request.FamilyMasterDTO;
import com.dxc.eproc.catalogue.model.SegmentMaster;

// TODO: Auto-generated Javadoc
/**
 * The Interface FamilyService.
 */
public interface FamilyService {

	/**
	 * Gets the family master.
	 *
	 * @param familyId the family id
	 * @return the family master
	 * @throws Exception the exception
	 */
	public Optional<FamilyMasterDTO> getFamilyMaster(Long familyId) throws Exception;

	/**
	 * Gets the family master by code.
	 *
	 * @param code the code
	 * @return the family master by code
	 * @throws Exception the exception
	 */
	public Optional<FamilyMasterDTO> getFamilyMasterByCode(String code) throws Exception;

	/**
	 * Gets the all families.
	 *
	 * @param pageable the pageable
	 * @param segment  the segment
	 * @return the all families
	 * @throws Exception the exception
	 */
	public Page<FamilyMasterDTO> getAllFamilies(Pageable pageable, SegmentMaster segment) throws Exception;
}
