package com.dxc.eproc.catalogue.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.catalogue.model.Specification;

// TODO: Auto-generated Javadoc
/**
 * The Interface SpecificationRepository.
 */
@Repository
public interface SpecificationRepository extends JpaRepository<Specification, Long> {

	/**
	 * Search Specifications.
	 *
	 * @param deptId   the deptId
	 * @param specName the specName
	 * @return the object
	 */
	public Specification findByDeptIdAndSpecName(Integer deptId, String specName);

	/**
	 * Search Specifications.
	 *
	 * @param deptId   the deptId
	 * @param pageable the pageable
	 * @return the list
	 */
	public Page<Specification> findByDeptIdOrderByLastModifiedTsDesc(Integer deptId, Pageable pageable);

	/**
	 * Search Specifications.
	 *
	 * @param deptId   the deptId
	 * @param specName the specName
	 * @param pageable the pageable
	 * @return the list
	 */
	public Page<Specification> findByDeptIdAndSpecNameContainingOrderByLastModifiedTsDesc(Integer deptId, String specName, Pageable pageable);
}
