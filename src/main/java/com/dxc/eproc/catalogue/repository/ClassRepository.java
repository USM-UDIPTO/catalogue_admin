package com.dxc.eproc.catalogue.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.catalogue.model.ClassMaster;
import com.dxc.eproc.catalogue.model.FamilyMaster;

// TODO: Auto-generated Javadoc
/**
 * The Interface ClassRepository.
 */
@Repository
public interface ClassRepository extends JpaRepository<ClassMaster, Long>, QuerydslPredicateExecutor<ClassMaster> {

	/**
	 * Find by family master.
	 *
	 * @param pageable     the pageable
	 * @param familyMaster the family master
	 * @return the page
	 */
	public Page<ClassMaster> findByFamilyMaster(Pageable pageable, FamilyMaster familyMaster);

	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the optional
	 */
	public Optional<ClassMaster> findByCode(String code);
}
