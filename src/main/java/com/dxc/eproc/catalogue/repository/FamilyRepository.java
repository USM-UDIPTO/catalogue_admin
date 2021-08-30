package com.dxc.eproc.catalogue.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.catalogue.model.FamilyMaster;
import com.dxc.eproc.catalogue.model.SegmentMaster;

// TODO: Auto-generated Javadoc
/**
 * The Interface FamilyRepository.
 */
@Repository
public interface FamilyRepository extends JpaRepository<FamilyMaster, Long>, QuerydslPredicateExecutor<FamilyMaster> {

	/**
	 * Find bysegment master.
	 *
	 * @param pageable      the pageable
	 * @param segmentMaster the segment master
	 * @return the page
	 */
	public Page<FamilyMaster> findBysegmentMaster(Pageable pageable, SegmentMaster segmentMaster);

	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the optional
	 */
	public Optional<FamilyMaster> findByCode(String code);
}
