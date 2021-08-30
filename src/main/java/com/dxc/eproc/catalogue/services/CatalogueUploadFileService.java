package com.dxc.eproc.catalogue.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.catalogue.dto.request.CatalogueUploadFileDTO;
import com.dxc.eproc.catalogue.dto.request.CatalogueUploadFileDetailsDTO;
import com.dxc.eproc.catalogue.model.CatalogueUploadFileDetails;
import com.querydsl.core.BooleanBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Interface CatalogueUploadFileService.
 */
public interface CatalogueUploadFileService {

	/**
	 * Search catalogue upload files.
	 *
	 * @param builder  the builder
	 * @param pageable the pageable
	 * @return the page
	 * @throws Exception the exception
	 */
	public Page<CatalogueUploadFileDTO> searchCatalogueUploadFiles(BooleanBuilder builder, Pageable pageable)
			throws Exception;

	/**
	 * Search catalogue upload files details.
	 *
	 * @param builder  the builder
	 * @param pageable the pageable
	 * @return the page
	 * @throws Exception the exception
	 */
	public Page<CatalogueUploadFileDetailsDTO> searchCatalogueUploadFilesDetails(BooleanBuilder builder,
			Pageable pageable) throws Exception;

	/**
	 * Save.
	 *
	 * @param catalogueUploadFileDTO the catalogue upload file DTO
	 * @return the catalogue upload file DTO
	 */
	public CatalogueUploadFileDTO save(CatalogueUploadFileDTO catalogueUploadFileDTO);

	/**
	 * Save.
	 *
	 * @param catalogueUploadFileDetailsDTO the catalogue upload file details DTO
	 * @return the catalogue upload file details DTO
	 */
	public CatalogueUploadFileDetailsDTO saveCatalogueUploadFileDetails(
			CatalogueUploadFileDetailsDTO catalogueUploadFileDetailsDTO);

	/**
	 * Gets the Error Details for uploaded file.
	 *
	 * @param cataloguefileId the cataloguefile id
	 * @return the list
	 */
	public List<CatalogueUploadFileDetails> loadErrorDetails(Long cataloguefileId);

}
