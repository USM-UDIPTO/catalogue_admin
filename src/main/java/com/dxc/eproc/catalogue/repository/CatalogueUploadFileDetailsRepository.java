package com.dxc.eproc.catalogue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.dxc.eproc.catalogue.model.CatalogueUploadFileDetails;

// TODO: Auto-generated Javadoc
/**
 * The Interface CatalogueItemFileDetailsRepository.
 */
@Repository
public interface CatalogueUploadFileDetailsRepository
		extends JpaRepository<CatalogueUploadFileDetails, Long>, QuerydslPredicateExecutor<CatalogueUploadFileDetails> {

	/**
	 * Find by catalogue file id and status.
	 *
	 * @param catalogueFileId the upload file id
	 * @param status          the status
	 * @return the List of CatalogueUploadFileDetails
	 */
	public List<CatalogueUploadFileDetails> findByCatalogueFileIdAndStatus(Long catalogueFileId, String status);

}
