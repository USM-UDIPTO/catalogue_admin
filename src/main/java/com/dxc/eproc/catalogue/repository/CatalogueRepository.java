package com.dxc.eproc.catalogue.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.catalogue.model.Catalogue;

// TODO: Auto-generated Javadoc
/**
 * Spring Data repository for the Catalogue entity.
 */
@Repository
public interface CatalogueRepository extends JpaRepository<Catalogue, Long>, QuerydslPredicateExecutor<Catalogue> {

	/**
	 * Search Catalogue by deptId and CatalogueCode.
	 *
	 * @param deptId        the deptId
	 * @param catalogueCode the catalogueCode
	 * @return the object
	 */
	public Catalogue findByDeptIdAndCatalogueCode(Integer deptId, String catalogueCode);

	/**
	 * Search Catalogue by deptId and CatalogueName.
	 *
	 * @param deptId        the deptId
	 * @param catalogueName the catalogueName
	 * @return the object
	 */
	public Catalogue findByDeptIdAndCatalogueName(Integer deptId, String catalogueName);

	/**
	 * Search Catalogue by deptId.
	 *
	 * @param pageable the pageable
	 * @param deptId   the deptId
	 * @return the list
	 */
	public Page<Catalogue> findByDeptIdOrderByLastModifiedTsDesc(Pageable pageable, Integer deptId);

	/**
	 * Find by dept id order by last modified ts desc.
	 *
	 * @param deptId the dept id
	 * @return the list
	 */
	public List<Catalogue> findByDeptIdOrderByLastModifiedTsDesc(Integer deptId);

	/**
	 * Find by dept id and active yn order by catalogue name.
	 *
	 * @param pageable the pageable
	 * @param deptId   the dept id
	 * @param activeYn the active yn
	 * @return the page
	 */
	public Page<Catalogue> findByDeptIdAndActiveYnOrderByLastModifiedTsDesc(Pageable pageable, Integer deptId,
			Boolean activeYn);

	/**
	 * Find by dept id and active yn order by last modified ts desc.
	 *
	 * @param deptId the dept id
	 * @param activeYn the active yn
	 * @return the list
	 */
	public List<Catalogue> findByDeptIdAndActiveYnOrderByLastModifiedTsDesc(Integer deptId, Boolean activeYn);

	/**
	 * Find by catalogue code.
	 *
	 * @param catalogueCode the catalogue code
	 * @return the catalogue
	 */
	public Catalogue findByCatalogueCode(String catalogueCode);
}
