package com.dxc.eproc.catalogue.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.catalogue.model.Catalogue;
import com.dxc.eproc.catalogue.model.Category;

// TODO: Auto-generated Javadoc
/**
 * The Interface CategoryRepository.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, QuerydslPredicateExecutor<Category> {

	/**
	 * Search Category by deptId and CategoryCode.
	 *
	 * @param deptId       the deptId
	 * @param categoryCode the categoryCode
	 * @return the object
	 */
	public Category findByDeptIdAndCategoryCode(Integer deptId, String categoryCode);

	/**
	 * Search Category by deptId and CategoryName.
	 *
	 * @param deptId       the deptId
	 * @param categoryName the categoryName
	 * @return the object
	 */
	public Category findByDeptIdAndCategoryName(Integer deptId, String categoryName);

	/**
	 * Search Categories by Catalogue.
	 *
	 * @param pageable  the pageable
	 * @param catalogue the catalogue
	 * @return the list
	 */
	public Page<Category> findByCatalogueOrderByLastModifiedTsDesc(Pageable pageable, Catalogue catalogue);

	/**
	 * Search Category by Catalogue and Id.
	 *
	 * @param catalogue the catalogue
	 * @param id        the id
	 * @return the object
	 */
	public Category findByCatalogueAndId(Catalogue catalogue, Long id);

	/**
	 * Search Category by catagoryCode .
	 *
	 * @param categoryCode the category code
	 * @return the Category
	 */
	public Optional<Category> findByCategoryCode(String categoryCode);

	/**
	 * Search Category by catalogue and Boolean activeYn .
	 *
	 * @param pageable  the pageable
	 * @param catalogue the catalogue
	 * @param active    the activeYn
	 * @return the Category
	 */
	public Page<Category> findByCatalogueAndActiveYnOrderByLastModifiedTsDesc(Pageable pageable, Catalogue catalogue,
			Boolean active);

	/**
	 * Find by category code and catalogue.
	 *
	 * @param categoryCode the category code
	 * @param catalogue    the catalogue
	 * @return the optional
	 */
	public Optional<Category> findByCategoryCodeAndCatalogue(String categoryCode, Catalogue catalogue);
}
