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

import com.dxc.eproc.catalogue.dto.request.CatalogueDTO;
import com.dxc.eproc.catalogue.mapper.CatalogueMapper;
import com.dxc.eproc.catalogue.model.Catalogue;
import com.dxc.eproc.catalogue.repository.CatalogueRepository;
import com.dxc.eproc.catalogue.services.CatalogueService;
import com.querydsl.core.BooleanBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Class CatalogueServiceImpl.
 */
@Service
@Transactional
public class CatalogueServiceImpl implements CatalogueService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(CatalogueServiceImpl.class);

	/** The catalogue repository. */
	@Autowired
	private CatalogueRepository catalogueRepository;

	/** The catalogue mapper. */
	@Autowired
	private CatalogueMapper catalogueMapper;

	/**
	 * Save or update catalogue.
	 *
	 * @param catalogue the catalogue
	 * @return the catalogue DTO
	 * @throws Exception the exception
	 */
	@Override
	public CatalogueDTO saveOrUpdateCatalogue(Catalogue catalogue) throws Exception {
		catalogue = catalogueRepository.save(catalogue);
		return catalogueMapper.toDto(catalogue);
	}

	/**
	 * Gets the all catalogues.
	 *
	 * @param deptId   the dept id
	 * @param activeYn the active yn
	 * @return the all catalogues
	 * @throws Exception the exception
	 */
	@Override
	public List<CatalogueDTO> getAllCatalogues(Integer deptId, Boolean activeYn) throws Exception {
		log.info("getAllCatalogues");
		List<Catalogue> catalogueList = new ArrayList<>();
		if (activeYn == null) {
			catalogueList = catalogueRepository.findByDeptIdOrderByLastModifiedTsDesc(deptId);
		} else {
			catalogueList = catalogueRepository.findByDeptIdAndActiveYnOrderByLastModifiedTsDesc(deptId, activeYn);
		}
		return catalogueMapper.toDto(catalogueList);
	}

	/**
	 * Gets the all catalogues.
	 *
	 * @param pageable the pageable
	 * @param deptId   the dept id
	 * @param activeYn the active yn
	 * @return the all catalogues
	 * @throws Exception the exception
	 */
	@Override
	public Page<CatalogueDTO> getAllCataloguesPagination(Pageable pageable, Integer deptId, Boolean activeYn)
			throws Exception {
		Page<Catalogue> catalogueList = new PageImpl<>(new ArrayList<>());
		if (activeYn == null) {
			catalogueList = catalogueRepository.findByDeptIdOrderByLastModifiedTsDesc(pageable, deptId);
		} else {
			catalogueList = catalogueRepository.findByDeptIdAndActiveYnOrderByLastModifiedTsDesc(pageable, deptId,
					activeYn);
		}
		long totalRows = catalogueList.getTotalElements();
		List<CatalogueDTO> catalogueDTOList = catalogueList.stream().map(catalogue -> {
			return catalogueMapper.toDto(catalogue);
		}).collect(Collectors.toList());
		return new PageImpl<CatalogueDTO>(catalogueDTOList, pageable, totalRows);
	}

	/**
	 * Search catalogues.
	 *
	 * @param builder  the builder
	 * @param pageable the pageable
	 * @return the page
	 * @throws Exception the exception
	 */
	@Override
	public Page<CatalogueDTO> searchCatalogues(BooleanBuilder builder, Pageable pageable) throws Exception {
		Page<Catalogue> catalogueList = catalogueRepository.findAll(builder, pageable);
		long totalRows = catalogueList.getTotalElements();
		List<CatalogueDTO> catalogueDTOList = catalogueList.stream().map(catalogue -> {
			return catalogueMapper.toDto(catalogue);
		}).collect(Collectors.toList());
		return new PageImpl<CatalogueDTO>(catalogueDTOList, pageable, totalRows);
	}

	/**
	 * Gets the catalogue.
	 *
	 * @param catalogueId the catalogue id
	 * @return the catalogue
	 * @throws Exception the exception
	 */
	@Override
	public Optional<CatalogueDTO> getCatalogue(Long catalogueId) throws Exception {
		return catalogueRepository.findById(catalogueId).map(catalogueMapper::toDto);
	}

	/**
	 * Delete catalogue.
	 *
	 * @param catalogueId the catalogue id
	 */
	@Override
	public void deleteCatalogue(Long catalogueId) {
		catalogueRepository.deleteById(catalogueId);
	}

	/**
	 * Checks for duplicate Catalogue name or code.
	 *
	 * @param catalogueDTO the catalogueDTO
	 * @param field        the field
	 * @return boolean value
	 * @throws Exception the Exception
	 */
	public boolean catalogueNameCodeCheck(CatalogueDTO catalogueDTO, String field) throws Exception {
		Catalogue catalogue = null;
		if (field.equalsIgnoreCase("code")) {
			catalogue = catalogueRepository.findByDeptIdAndCatalogueCode(catalogueDTO.getDeptId(),
					catalogueDTO.getCatalogueCode());
		} else {
			catalogue = catalogueRepository.findByDeptIdAndCatalogueName(catalogueDTO.getDeptId(),
					catalogueDTO.getCatalogueName());
		}
		// save check
		if (catalogueDTO.getId() == null) {
			if (Optional.ofNullable(catalogue).isPresent()) {
				return true;
			}
		} else { // update check
			if (Optional.ofNullable(catalogue).isPresent()) {
				if (!catalogue.getId().equals(catalogueDTO.getId())) {
					return true;
				}
			}
		}
		return false;
	}

}
