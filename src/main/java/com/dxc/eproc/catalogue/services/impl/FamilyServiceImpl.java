package com.dxc.eproc.catalogue.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.catalogue.dto.request.FamilyMasterDTO;
import com.dxc.eproc.catalogue.mapper.FamilyMasterMapper;
import com.dxc.eproc.catalogue.model.FamilyMaster;
import com.dxc.eproc.catalogue.model.SegmentMaster;
import com.dxc.eproc.catalogue.repository.FamilyRepository;
import com.dxc.eproc.catalogue.services.FamilyService;

// TODO: Auto-generated Javadoc
/**
 * The Class FamilyServiceImpl.
 */
@Service
@Transactional
public class FamilyServiceImpl implements FamilyService {

	/** The family repository. */
	@Autowired
	private FamilyRepository familyRepository;

	/** The family master mapper. */
	@Autowired
	private FamilyMasterMapper familyMasterMapper;

	/**
	 * Gets the family master.
	 *
	 * @param familyId the family id
	 * @return the family master
	 * @throws Exception the exception
	 */
	@Override
	public Optional<FamilyMasterDTO> getFamilyMaster(Long familyId) throws Exception {
		return familyRepository.findById(familyId).map(family -> {
			FamilyMasterDTO familyDto = familyMasterMapper.toDto(family);
			familyDto.setSegmentId(family.getSegmentMaster().getId());
			return familyDto;
		});
	}

	/**
	 * Gets the family master by code.
	 *
	 * @param code the code
	 * @return the family master by code
	 * @throws Exception the exception
	 */
	@Override
	public Optional<FamilyMasterDTO> getFamilyMasterByCode(String code) throws Exception {
		return familyRepository.findByCode(code).map(family -> {
			FamilyMasterDTO familyDto = familyMasterMapper.toDto(family);
			familyDto.setSegmentId(family.getSegmentMaster().getId());
			return familyDto;
		});
	}

	/**
	 * Gets the all families.
	 *
	 * @param pageable the pageable
	 * @param segment  the segment
	 * @return the all families
	 * @throws Exception the exception
	 */
	@Override
	public Page<FamilyMasterDTO> getAllFamilies(Pageable pageable, SegmentMaster segment) throws Exception {
		Page<FamilyMaster> familyList = familyRepository.findBysegmentMaster(pageable, segment);
		long totalRows = familyList.getTotalElements();
		List<FamilyMasterDTO> familyDTOList = familyList.stream().map(family -> {
			FamilyMasterDTO familyDto = familyMasterMapper.toDto(family);
			familyDto.setSegmentId(family.getSegmentMaster().getId());
			return familyDto;
		}).collect(Collectors.toList());
		return new PageImpl<FamilyMasterDTO>(familyDTOList, pageable, totalRows);
	}
}
