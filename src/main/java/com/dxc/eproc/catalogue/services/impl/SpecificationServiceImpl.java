package com.dxc.eproc.catalogue.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.catalogue.dto.request.SpecificationDTO;
import com.dxc.eproc.catalogue.mapper.SpecificationMapper;
import com.dxc.eproc.catalogue.model.Specification;
import com.dxc.eproc.catalogue.repository.SpecificationRepository;
import com.dxc.eproc.catalogue.services.SpecificationService;

// TODO: Auto-generated Javadoc
/**
 * The Class SpecificationServiceImpl.
 */
@Service
@Transactional
public class SpecificationServiceImpl implements SpecificationService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(SpecificationServiceImpl.class);

	/** The specification mapper. */
	@Autowired
	private SpecificationMapper specificationMapper;

	/** The specification repository. */
	@Autowired
	private SpecificationRepository specificationRepository;

	/**
	 * Save or update specification.
	 *
	 * @param specification the specification
	 * @return the specification DTO
	 * @throws Exception the exception
	 */
	@Override
	public SpecificationDTO saveOrUpdateSpecification(Specification specification) throws Exception {
		specification = specificationRepository.save(specification);
		return specificationMapper.toDto(specification);
	}

	/**
	 * Gets the all specifications.
	 *
	 * @param pageable the pageable
	 * @param deptId   the dept id
	 * @return the all specifications
	 * @throws Exception the exception
	 */
	@Override
	public Page<SpecificationDTO> getAllSpecifications(Pageable pageable, Integer deptId) throws Exception {
		List<SpecificationDTO> specificationDTOList = new ArrayList<>();
		long totalRows = 0;
		Page<Specification> specificationList = specificationRepository.findByDeptIdOrderByLastModifiedTsDesc(deptId,
				pageable);
		totalRows = specificationList.getTotalElements();
		specificationDTOList = specificationList.stream().map(specification -> {
			return specificationMapper.toDto(specification);
		}).collect(Collectors.toList());
		return new PageImpl<>(specificationDTOList, pageable, totalRows);
	}

	/**
	 * Search specifications.
	 *
	 * @param pageable the pageable
	 * @param deptId   the dept id
	 * @param specName the spec name
	 * @return the page
	 * @throws Exception the exception
	 */
	@Override
	public Page<SpecificationDTO> searchSpecifications(Pageable pageable, Integer deptId, String specName)
			throws Exception {
		List<SpecificationDTO> specificationDTOList = new ArrayList<>();
		long totalRows = 0;
		Page<Specification> specificationList = specificationRepository
				.findByDeptIdAndSpecNameContainingOrderByLastModifiedTsDesc(deptId, specName, pageable);
		totalRows = specificationList.getTotalElements();
		specificationDTOList = specificationList.stream().map(specification -> {
			return specificationMapper.toDto(specification);
		}).collect(Collectors.toList());
		return new PageImpl<>(specificationDTOList, pageable, totalRows);
	}

	/**
	 * Gets the specification.
	 *
	 * @param id the id
	 * @return the specification
	 * @throws Exception the exception
	 */
	@Override
	public Optional<SpecificationDTO> getSpecification(Long id) throws Exception {
		return specificationRepository.findById(id).map(specificationMapper::toDto);
	}

	/**
	 * Delete specification.
	 *
	 * @param specificationId the specification id
	 * @throws Exception the exception
	 */
	@Override
	public void deleteSpecification(Long specificationId) throws Exception {
		specificationRepository.deleteById(specificationId);
	}

	/**
	 * Checks for duplicate Specification name or code.
	 *
	 * @param specificationDTO the specificationDTO
	 * @return boolean value
	 * @throws Exception the Exception
	 */
	public boolean specificationNameCheck(SpecificationDTO specificationDTO) throws Exception {
		Specification specification = specificationRepository.findByDeptIdAndSpecName(specificationDTO.getDeptId(),
				specificationDTO.getSpecName());
		// save check
		if (specificationDTO.getId() == null) {
			if (Optional.ofNullable(specification).isPresent()) {
				return true;
			}
		} else { // update check
			if (Optional.ofNullable(specification).isPresent()) {
				if (!specification.getId().equals(specificationDTO.getId())) {
					return true;
				}
			}
		}
		return false;
	}
}
