package com.dxc.eproc.catalogue.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.catalogue.model.SegmentMaster;

// TODO: Auto-generated Javadoc
/**
 * The Interface SegmentRepository.
 */
@Repository
public interface SegmentRepository
		extends JpaRepository<SegmentMaster, Long>, QuerydslPredicateExecutor<SegmentMaster> {

	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the optional
	 */
	public Optional<SegmentMaster> findByCode(String code);
}
