package com.dxc.eproc.catalogue.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.catalogue.dto.request.CatalogueDTO;
import com.dxc.eproc.catalogue.model.Catalogue;
import com.querydsl.core.BooleanBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Interface CatalogueService.
 */
public interface CatalogueService {

	/**
	 * saves or updates catalogue object.
	 *
	 * @param catalogue the catalogue
	 * @return the dto
	 * @throws Exception the Exception
	 */
	public CatalogueDTO saveOrUpdateCatalogue(Catalogue catalogue) throws Exception;

	/**
	 * Gets the all catalogues.
	 *
	 * @param deptId   the dept id
	 * @param activeYn the active yn
	 * @return the all catalogues
	 * @throws Exception the exception
	 */
	public List<CatalogueDTO> getAllCatalogues(Integer deptId, Boolean activeYn) throws Exception;

	/**
	 * returns list of catalogues.
	 *
	 * @param pageable the pageable
	 * @param deptId   the deptId
	 * @param active   the active
	 * @return the list
	 * @throws Exception the Exception
	 */
	public Page<CatalogueDTO> getAllCataloguesPagination(Pageable pageable, Integer deptId, Boolean active)
			throws Exception;

	/**
	 * Returns optional CatalogueDTO.
	 *
	 * @param catalogueId the catalogueId
	 * @return Optional CatalogueDTO
	 * @throws Exception the Exception
	 */
	public Optional<CatalogueDTO> getCatalogue(Long catalogueId) throws Exception;

	/**
	 * Deletes a catalogue.
	 *
	 * @param catalogueId the catalogueId
	 * @throws Exception the Exception
	 */
	public void deleteCatalogue(Long catalogueId) throws Exception;

	/**
	 * Checks duplicate catalogue name or code.
	 * 
	 * @param catalogueDTO the catalogueDTO
	 * @param codeOrName   the codeOrName
	 * @return boolean value
	 * @throws Exception the Exception
	 */
	public boolean catalogueNameCodeCheck(CatalogueDTO catalogueDTO, String codeOrName) throws Exception;

	/**
	 * Search Catalogues.
	 *
	 * @param builder  the builder
	 * @param pageable the pageable
	 * @return CatalogueDTOList
	 * @throws Exception the Exception
	 */
	public Page<CatalogueDTO> searchCatalogues(BooleanBuilder builder, Pageable pageable) throws Exception;
}
