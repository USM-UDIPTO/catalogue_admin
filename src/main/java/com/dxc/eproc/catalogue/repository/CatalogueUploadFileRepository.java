package com.dxc.eproc.catalogue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.catalogue.model.CatalogueUploadFile;

/**
 * The Interface CatalogueItemFileRepository.
 */
@Repository
public interface CatalogueUploadFileRepository
		extends JpaRepository<CatalogueUploadFile, Long>, QuerydslPredicateExecutor<CatalogueUploadFile> {

}
