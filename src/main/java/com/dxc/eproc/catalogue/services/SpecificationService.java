package com.dxc.eproc.catalogue.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.catalogue.dto.request.SpecificationDTO;
import com.dxc.eproc.catalogue.model.Specification;

// TODO: Auto-generated Javadoc
/**
 * The Interface SpecificationService.
 */
public interface SpecificationService {

	/**
	 * Saves or Updates the Specification.
	 *
	 * @param specification the specification
	 * @return specificationDTO the specificationDTO
	 * @throws Exception the Exception
	 */
	public SpecificationDTO saveOrUpdateSpecification(Specification specification) throws Exception;

	/**
	 * Returns a list of Specifications.
	 *
	 * @param pageable the pageable
	 * @param deptId   the deptId
	 * @return the list
	 * @throws Exception the Exception
	 */
	public Page<SpecificationDTO> getAllSpecifications(Pageable pageable, Integer deptId) throws Exception;

	/**
	 * Returns a list of Specifications.
	 *
	 * @param pageable the pageable
	 * @param deptId   the deptId
	 * @param specName the specName
	 * @return the list
	 * @throws Exception the Exception
	 */
	public Page<SpecificationDTO> searchSpecifications(Pageable pageable, Integer deptId, String specName)
			throws Exception;

	/**
	 * Returns Optional SpecificationDTO.
	 *
	 * @param specificationId the specificationId
	 * @return Optional SpecificationDTO
	 * @throws Exception the Exception
	 */
	public Optional<SpecificationDTO> getSpecification(Long specificationId) throws Exception;

	/**
	 * Checks duplicate specification name or code.
	 *
	 * @param specificationDTO the specificationDTO
	 * @return boolean value
	 * @throws Exception the Exception
	 */
	public boolean specificationNameCheck(SpecificationDTO specificationDTO) throws Exception;

	/**
	 * Deletes specification based on id.
	 *
	 * @param specificationId the specificationId
	 * @throws Exception the Exception
	 */
	public void deleteSpecification(Long specificationId) throws Exception;
}