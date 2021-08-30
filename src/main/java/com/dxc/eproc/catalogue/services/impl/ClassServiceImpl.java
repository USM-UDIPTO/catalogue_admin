package com.dxc.eproc.catalogue.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dxc.eproc.catalogue.dto.request.ClassMasterDTO;
import com.dxc.eproc.catalogue.mapper.ClassMasterMapper;
import com.dxc.eproc.catalogue.model.ClassMaster;
import com.dxc.eproc.catalogue.model.FamilyMaster;
import com.dxc.eproc.catalogue.repository.ClassRepository;
import com.dxc.eproc.catalogue.services.ClassService;

// TODO: Auto-generated Javadoc
/**
 * The Class ClassServiceImpl.
 */
@Service
@Transactional
public class ClassServiceImpl implements ClassService {

	/** The ClassMaster repository. */
	@Autowired
	private ClassRepository classRepository;

	/** The ClassMaster mapper. */
	@Autowired
	private ClassMasterMapper classMasterMapper;

	/**
	 * Gets the class master.
	 *
	 * @param classMasterId the class master id
	 * @return the class master
	 * @throws Exception the exception
	 */
	@Override
	public Optional<ClassMasterDTO> getClassMaster(Long classMasterId) throws Exception {
		return classRepository.findById(classMasterId).map(classMaster -> {
			ClassMasterDTO classMasterDto = classMasterMapper.toDto(classMaster);
			classMasterDto.setFamilyId(classMaster.getFamilyMaster().getId());
			return classMasterDto;
		});
	}

	/**
	 * Gets the class master by code.
	 *
	 * @param code the code
	 * @return the class master by code
	 * @throws Exception the exception
	 */
	@Override
	public Optional<ClassMasterDTO> getClassMasterByCode(String code) throws Exception {
		return classRepository.findByCode(code).map(classMaster -> {
			ClassMasterDTO classMasterDto = classMasterMapper.toDto(classMaster);
			classMasterDto.setFamilyId(classMaster.getFamilyMaster().getId());
			return classMasterDto;
		});
	}

	/**
	 * Gets the all class masters.
	 *
	 * @param pageable the pageable
	 * @param family   the family
	 * @return the all class masters
	 * @throws Exception the exception
	 */
	@Override
	public Page<ClassMasterDTO> getAllClasses(Pageable pageable, FamilyMaster family) throws Exception {
		Page<ClassMaster> classMasterList = classRepository.findByFamilyMaster(pageable, family);
		long totalRows = classMasterList.getTotalElements();
		List<ClassMasterDTO> classMasterDTOList = classMasterList.stream().map(classMaster -> {
			ClassMasterDTO classMasterDto = classMasterMapper.toDto(classMaster);
			classMasterDto.setFamilyId(classMaster.getFamilyMaster().getId());
			return classMasterDto;
		}).collect(Collectors.toList());
		return new PageImpl<ClassMasterDTO>(classMasterDTOList, pageable, totalRows);
	}
}
