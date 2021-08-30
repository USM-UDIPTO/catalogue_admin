package com.dxc.eproc.catalogue.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.catalogue.model.ClassMaster;
import com.dxc.eproc.catalogue.model.CommodityMaster;

// TODO: Auto-generated Javadoc
/**
 * The Interface CommodityRepository.
 */
@Repository
public interface CommodityRepository
		extends JpaRepository<CommodityMaster, Long>, QuerydslPredicateExecutor<CommodityMaster> {

	/**
	 * Find by class master.
	 *
	 * @param pageable    the pageable
	 * @param classMaster the class master
	 * @return the page
	 */
	public Page<CommodityMaster> findByClassMaster(Pageable pageable, ClassMaster classMaster);

	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the optional
	 */
	public Optional<CommodityMaster> findByCode(String code);

}
