package com.dxc.eproc.catalogue.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.catalogue.dto.request.CatalogueItemDTO;
import com.dxc.eproc.catalogue.dto.request.CatalogueItemSearchDTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface CustomCatalogueItemSearchRepository.
 */
public interface CustomCatalogueItemSearchRepository {

	/**
	 * Search catalogue item query DSL.
	 *
	 * @param pageable               the pageable
	 * @param CatalogueItemSearchDto the catalogue item search dto
	 * @return the page
	 * @throws Exception the exception
	 */
	public Page<CatalogueItemDTO> searchCatalogueItemQueryDSL(Pageable pageable,
			CatalogueItemSearchDTO CatalogueItemSearchDto) throws Exception;

}
