package com.dxc.eproc.catalogue.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.catalogue.dto.request.CatalogueItemDTO;
import com.dxc.eproc.catalogue.dto.request.CatalogueItemSearchDTO;
import com.dxc.eproc.catalogue.mapper.CatalogueItemMapper;
import com.dxc.eproc.catalogue.model.CatalogueItem;
import com.dxc.eproc.catalogue.repository.CatalogueItemRepository;
import com.dxc.eproc.catalogue.services.CatalogueItemService;

// TODO: Auto-generated Javadoc
/**
 * The Class CatalogueItemServiceImpl.
 */
@Service
@Transactional
public class CatalogueItemServiceImpl implements CatalogueItemService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(CatalogueItemServiceImpl.class);

	/** The catalogue item repo. */
	private final CatalogueItemRepository catalogueItemRepo;

	/** The catalogue item mapper. */
	private final CatalogueItemMapper catalogueItemMapper;

	/**
	 * Instantiates a new catalogue item service impl.
	 *
	 * @param catalogueItemRepo   the catalogue item repo
	 * @param catalogueItemMapper the catalogue item mapper
	 */
	public CatalogueItemServiceImpl(CatalogueItemRepository catalogueItemRepo,
			CatalogueItemMapper catalogueItemMapper) {
		super();
		this.catalogueItemRepo = catalogueItemRepo;
		this.catalogueItemMapper = catalogueItemMapper;
	}

	/**
	 * Save.
	 *
	 * @param catalogueItemDTO the catalogue item DTO
	 * @return the catalogue item DTO
	 */
	@Override
	public CatalogueItemDTO save(CatalogueItemDTO catalogueItemDTO) {

		log.debug("Service: Request to save CatalogueItem : {}", catalogueItemDTO);
		CatalogueItem item = catalogueItemMapper.toEntity(catalogueItemDTO);
		item = catalogueItemRepo.save(item);
		log.debug("Service: Saved CatalogueItem : {}", item);
		return catalogueItemMapper.toDto(item);
	}

	/**
	 * Check item code exist.
	 *
	 * @param catalogueItemCode the catalogue item code
	 * @return the optional
	 */
	@Override
	public Optional<CatalogueItemDTO> checkItemCodeExist(String catalogueItemCode) {

		log.debug("Service: Request to check itemcode exists : {}", catalogueItemCode);
		return catalogueItemRepo.findByItemCode(catalogueItemCode).map(catalogueItemMapper::toDto);
	}

	/**
	 * Check item name exist.
	 *
	 * @param catalogueItemName the catalogue item name
	 * @return the optional
	 */
	@Override
	public Optional<CatalogueItemDTO> checkItemNameExist(String catalogueItemName) {

		log.debug("Service: Request to check itemname exists : {}", catalogueItemName);
		return catalogueItemRepo.findByItemName(catalogueItemName).map(catalogueItemMapper::toDto);
	}

	/**
	 * Gets the.
	 *
	 * @param id            the id
	 * @param catalogueCode the catalogue code
	 * @return the optional
	 */
	@Override
	public Optional<CatalogueItemDTO> get(Long id, String catalogueCode) {
		log.debug("Service: Request to get CatalogueItem by ID: %d and %s", id, catalogueCode);
		return catalogueItemRepo.findByIdAndCatalogueCode(id, catalogueCode).map(catalogueItemMapper::toDto);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@Override
	public void delete(Long id) {
		log.debug("Service: Request to delete CatalogueItem %d ", id);
		catalogueItemRepo.deleteById(id);
		return;
	}

	/**
	 * Search catalogue items.
	 *
	 * @param pageable               the pageable
	 * @param catalogueItemSearchDTO the catalogue item search DTO
	 * @return the page
	 * @throws Exception the exception
	 */
	@Override
	public Page<CatalogueItemDTO> searchCatalogueItems(PageRequest pageable,
			CatalogueItemSearchDTO catalogueItemSearchDTO) throws Exception {

		return catalogueItemRepo.searchCatalogueItemQueryDSL(pageable, catalogueItemSearchDTO);

	}

	/**
	 * Gets the catalogueitems by catalogue code.
	 *
	 * @param catalogueCode the catalogue code
	 * @return the catalogueitems by catalogue code
	 * @throws Exception the exception
	 */
	@Override
	public List<CatalogueItem> getCatalogueitemsByCatalogueCode(String catalogueCode) throws Exception {

		return catalogueItemRepo.findByCatalogueCodeOrderByLastModifiedTsDesc(catalogueCode);

	}

	/**
	 * Gets the item by code and catalogue code.
	 *
	 * @param itemCode      the item code
	 * @param catalogueCode the catalogue code
	 * @return the item by code and catalogue code
	 */
	@Override
	public Optional<CatalogueItemDTO> getItemByCodeAndCatalogueCode(String itemCode, String catalogueCode) {

		return catalogueItemRepo.findByItemCodeAndCatalogueCode(itemCode, catalogueCode)
				.map(catalogueItemMapper::toDto);
	}

}
