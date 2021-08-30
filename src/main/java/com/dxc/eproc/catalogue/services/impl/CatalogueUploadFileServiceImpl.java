package com.dxc.eproc.catalogue.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.catalogue.dto.request.CatalogueUploadFileDTO;
import com.dxc.eproc.catalogue.dto.request.CatalogueUploadFileDetailsDTO;
import com.dxc.eproc.catalogue.mapper.CatalogueUploadFileDetailsMapper;
import com.dxc.eproc.catalogue.mapper.CatalogueUploadFileMapper;
import com.dxc.eproc.catalogue.model.CatalogueUploadFile;
import com.dxc.eproc.catalogue.model.CatalogueUploadFileDetails;
import com.dxc.eproc.catalogue.repository.CatalogueUploadFileDetailsRepository;
import com.dxc.eproc.catalogue.repository.CatalogueUploadFileRepository;
import com.dxc.eproc.catalogue.services.CatalogueUploadFileService;
import com.querydsl.core.BooleanBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Class CatalogueItemServiceImpl.
 */
@Service
@Transactional
public class CatalogueUploadFileServiceImpl implements CatalogueUploadFileService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(CatalogueUploadFileServiceImpl.class);

	/** The catalogue upload file repository. */
	private final CatalogueUploadFileRepository catalogueUploadFileRepository;

	/** The catalogue upload file details repository. */
	private final CatalogueUploadFileDetailsRepository catalogueUploadFileDetailsRepository;

	/** The catalogue upload file mapper. */
	private final CatalogueUploadFileMapper catalogueUploadFileMapper;

	/** The catalogue upload file details mapper. */
	private final CatalogueUploadFileDetailsMapper catalogueUploadFileDetailsMapper;

	/**
	 * Instantiates a new catalogue item service impl.
	 *
	 * @param catalogueUploadFileRepository        the catalogue upload file
	 *                                             repository
	 * @param catalogueUploadFileMapper            the catalogue upload file mapper
	 * @param catalogueUploadFileDetailsRepository the catalogue upload file details
	 *                                             repository
	 * @param catalogueUploadFileDetailsMapper     the catalogue upload file details
	 *                                             mapper
	 */
	public CatalogueUploadFileServiceImpl(CatalogueUploadFileRepository catalogueUploadFileRepository,
			CatalogueUploadFileMapper catalogueUploadFileMapper,
			CatalogueUploadFileDetailsRepository catalogueUploadFileDetailsRepository,
			CatalogueUploadFileDetailsMapper catalogueUploadFileDetailsMapper) {
		super();
		this.catalogueUploadFileRepository = catalogueUploadFileRepository;
		this.catalogueUploadFileMapper = catalogueUploadFileMapper;
		this.catalogueUploadFileDetailsRepository = catalogueUploadFileDetailsRepository;
		this.catalogueUploadFileDetailsMapper = catalogueUploadFileDetailsMapper;
	}

	/**
	 * Search catalogue upload files.
	 *
	 * @param builder  the builder
	 * @param pageable the pageable
	 * @return the page
	 * @throws Exception the exception
	 */
	@Override
	public Page<CatalogueUploadFileDTO> searchCatalogueUploadFiles(BooleanBuilder builder, Pageable pageable)
			throws Exception {
		Page<CatalogueUploadFile> catalogueUploadFileList = catalogueUploadFileRepository.findAll(builder, pageable);
		long totalRows = catalogueUploadFileList.getTotalElements();
		List<CatalogueUploadFileDTO> catalogueUploadFileDTOList = catalogueUploadFileList.stream().map(c -> {
			return catalogueUploadFileMapper.toDto(c);
		}).collect(Collectors.toList());
		return new PageImpl<CatalogueUploadFileDTO>(catalogueUploadFileDTOList, pageable, totalRows);
	}

	/**
	 * Search catalogue upload files details.
	 *
	 * @param builder  the builder
	 * @param pageable the pageable
	 * @return the page
	 * @throws Exception the exception
	 */
	@Override
	public Page<CatalogueUploadFileDetailsDTO> searchCatalogueUploadFilesDetails(BooleanBuilder builder,
			Pageable pageable) throws Exception {
		Page<CatalogueUploadFileDetails> catalogueUploadFileDetailsList = catalogueUploadFileDetailsRepository
				.findAll(builder, pageable);
		long totalRows = catalogueUploadFileDetailsList.getTotalElements();
		List<CatalogueUploadFileDetailsDTO> catalogueUploadFileDetailsDTOList = catalogueUploadFileDetailsList.stream()
				.map(c -> {
					return catalogueUploadFileDetailsMapper.toDto(c);
				}).collect(Collectors.toList());
		return new PageImpl<CatalogueUploadFileDetailsDTO>(catalogueUploadFileDetailsDTOList, pageable, totalRows);
	}

	/**
	 * Save.
	 *
	 * @param catalogueUploadFileDTO the catalogue upload file DTO
	 * @return the catalogue upload file DTO
	 */
	@Override
	public CatalogueUploadFileDTO save(CatalogueUploadFileDTO catalogueUploadFileDTO) {
		CatalogueUploadFile catalogueUploadFile = catalogueUploadFileMapper.toEntity(catalogueUploadFileDTO);
		catalogueUploadFile = catalogueUploadFileRepository.save(catalogueUploadFile);
		return catalogueUploadFileMapper.toDto(catalogueUploadFile);
	}

	/**
	 * Save.
	 *
	 * @param catalogueUploadFileDetailsDTO the catalogue upload file details DTO
	 * @return the catalogue upload file details DTO
	 */
	@Override
	public CatalogueUploadFileDetailsDTO saveCatalogueUploadFileDetails(
			CatalogueUploadFileDetailsDTO catalogueUploadFileDetailsDTO) {
		CatalogueUploadFileDetails catalogueUploadFileDetails = catalogueUploadFileDetailsMapper
				.toEntity(catalogueUploadFileDetailsDTO);
		catalogueUploadFileDetails = catalogueUploadFileDetailsRepository.save(catalogueUploadFileDetails);
		return catalogueUploadFileDetailsMapper.toDto(catalogueUploadFileDetails);
	}

	/**
	 * Load error details.
	 *
	 * @param cataloguefileId the cataloguefile id
	 * @return the list
	 */
	@Override
	public List<CatalogueUploadFileDetails> loadErrorDetails(Long cataloguefileId) {
		// TODO Auto-generated method stub
		String status = "FAILED";
		List<CatalogueUploadFileDetails> errorlist = catalogueUploadFileDetailsRepository
				.findByCatalogueFileIdAndStatus(cataloguefileId, status);
		return errorlist;
	}
}
