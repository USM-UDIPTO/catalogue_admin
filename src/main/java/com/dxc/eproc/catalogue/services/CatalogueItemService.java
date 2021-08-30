package com.dxc.eproc.catalogue.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.dxc.eproc.catalogue.dto.request.CatalogueItemDTO;
import com.dxc.eproc.catalogue.dto.request.CatalogueItemSearchDTO;
import com.dxc.eproc.catalogue.model.CatalogueItem;

// TODO: Auto-generated Javadoc
/**
 * The Interface CatalogueItemService.
 */
public interface CatalogueItemService {

	/**
	 * Save.
	 *
	 * @param catalogueItemDTO the catalogue item DTO
	 * @return the catalogue item DTO
	 */
	public CatalogueItemDTO save(CatalogueItemDTO catalogueItemDTO);

	/**
	 * Gets the.
	 *
	 * @param id            the id
	 * @param catalogueCode the catalogue code
	 * @return the optional
	 */
	public Optional<CatalogueItemDTO> get(Long id, String catalogueCode);

	/**
	 * Search catalogue items.
	 *
	 * @param of                     the of
	 * @param catalogueItemSearchDTO the catalogueItem search dto
	 * @return the page
	 * @throws Exception the exception
	 */
	public Page<CatalogueItemDTO> searchCatalogueItems(PageRequest of, CatalogueItemSearchDTO catalogueItemSearchDTO)
			throws Exception;

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	public void delete(Long id);

	/**
	 * Check item code exist.
	 *
	 * @param catalogueItemDTO the catalogue item DTO
	 * @return the optional
	 */
	public Optional<CatalogueItemDTO> checkItemCodeExist(String catalogueItemDTO);

	/**
	 * Check item name exist.
	 *
	 * @param catalogueItemDTO the catalogue item DTO
	 * @return the optional
	 */
	public Optional<CatalogueItemDTO> checkItemNameExist(String catalogueItemDTO);

	/**
	 * Gets the catalogueitems by catalogue code.
	 *
	 * @param catalogueCode the catalogue code
	 * @return the catalogueitems by catalogue code
	 * @throws Exception the Exception
	 */
	public List<CatalogueItem> getCatalogueitemsByCatalogueCode(String catalogueCode) throws Exception;

	/**
	 * Gets the item by code and catalogue code.
	 *
	 * @param itemCode      the item code
	 * @param catalogueCode the catalogue code
	 * @return the item by code and catalogue code
	 */
	public Optional<CatalogueItemDTO> getItemByCodeAndCatalogueCode(String itemCode, String catalogueCode);

}
