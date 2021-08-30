package com.dxc.eproc.catalogue.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.catalogue.model.CatalogueItem;

// TODO: Auto-generated Javadoc
/**
 * The Interface CatalogueItemRepository.
 */
@Repository
public interface CatalogueItemRepository
		extends JpaRepository<CatalogueItem, Long>, CustomCatalogueItemSearchRepository {

	/**
	 * Find by id and catalogue code.
	 *
	 * @param id            the id
	 * @param catalogueCode the catalogue code
	 * @return the optional
	 */
	public Optional<CatalogueItem> findByIdAndCatalogueCode(Long id, String catalogueCode);

	/**
	 * Find by item code.
	 *
	 * @param itemCode the item code
	 * @return the optional
	 */
	public Optional<CatalogueItem> findByItemCode(String itemCode);

	/**
	 * Find by item name.
	 *
	 * @param itemName the item name
	 * @return the optional
	 */
	public Optional<CatalogueItem> findByItemName(String itemName);

	/**
	 * Returns a list based on parameters.
	 *
	 * @param catalogueCode the catalogueCode
	 * @return the list
	 */
	public List<CatalogueItem> findByCatalogueCodeOrderByLastModifiedTsDesc(String catalogueCode);

	/**
	 * Find by item code and catalogue code.
	 *
	 * @param itemCode      the item code
	 * @param catalogueCode the catalogue code
	 * @return the optional
	 */
	public Optional<CatalogueItem> findByItemCodeAndCatalogueCode(String itemCode, String catalogueCode);
}
