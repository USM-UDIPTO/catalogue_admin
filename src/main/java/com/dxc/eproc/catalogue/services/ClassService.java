package com.dxc.eproc.catalogue.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.catalogue.dto.request.ClassMasterDTO;
import com.dxc.eproc.catalogue.model.FamilyMaster;

// TODO: Auto-generated Javadoc
/**
 * The Interface ClassService.
 */
public interface ClassService {

	/**
	 * Gets the class master.
	 *
	 * @param classId the class id
	 * @return the class master
	 * @throws Exception the exception
	 */
	public Optional<ClassMasterDTO> getClassMaster(Long classId) throws Exception;

	/**
	 * Gets the class master by code.
	 *
	 * @param code the code
	 * @return the class master by code
	 * @throws Exception the exception
	 */
	public Optional<ClassMasterDTO> getClassMasterByCode(String code) throws Exception;

	/**
	 * Gets the all classes.
	 *
	 * @param pageable the pageable
	 * @param family   the family
	 * @return the all classes
	 * @throws Exception the exception
	 */
	public Page<ClassMasterDTO> getAllClasses(Pageable pageable, FamilyMaster family) throws Exception;
}
