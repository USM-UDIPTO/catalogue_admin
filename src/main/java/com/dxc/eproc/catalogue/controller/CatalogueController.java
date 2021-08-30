package com.dxc.eproc.catalogue.controller;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dxc.eproc.catalogue.config.EprocMessageSourceComponent;
import com.dxc.eproc.catalogue.dto.request.CatalogueDTO;
import com.dxc.eproc.catalogue.dto.request.CatalogueSearchDTO;
import com.dxc.eproc.catalogue.mapper.CatalogueMapper;
import com.dxc.eproc.catalogue.model.Catalogue;
import com.dxc.eproc.catalogue.model.QCatalogue;
import com.dxc.eproc.catalogue.repository.CatalogueItemRepository;
import com.dxc.eproc.catalogue.repository.CatalogueRepository;
import com.dxc.eproc.catalogue.services.CatalogueService;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.utils.PaginationUtil;
import com.querydsl.core.BooleanBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Class CatalogueController.
 */
@RestController
@Transactional
@RequestMapping("/v1/api")
public class CatalogueController {

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "Catalogue";

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(CatalogueController.class);

	/** The message source utility. */
	@Autowired
	private EprocMessageSourceComponent messageSourceUtility;

	/** The catalogue service. */
	@Autowired
	private CatalogueService catalogueService;

	/** The catalogue repository. */
	@Autowired
	private CatalogueRepository catalogueRepository;

	/** The catalogue item repository. */
	@Autowired
	private CatalogueItemRepository catalogueItemRepository;

	/** The catalogue mapper. */
	@Autowired
	private CatalogueMapper catalogueMapper;

	/**
	 * System takes catalogueDTO object and creates a Category object after checking
	 * the following: Catalogue id must be blank Catalogue Code or Name should not
	 * exist.
	 *
	 * @param catalogueDTO the catalogueDTO
	 * @return catalogueDTO the catalogueDTO
	 * @throws Exception the Exception
	 */
	@PostMapping("/catalogue")
	public ResponseEntity<?> createCatalogue(@Valid @RequestBody CatalogueDTO catalogueDTO) throws Exception {
		log.info("API createCatalogue started at " + LocalTime.now());
		String errorMessage = "Catalogue.saveCatalogue.";
		if (catalogueDTO.getId() != null) {
			throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "nullId"), ENTITY_NAME,
					"nullId");
		}
		if (catalogueService.catalogueNameCodeCheck(catalogueDTO, "code")) {
			throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "codeExists"),
					ENTITY_NAME, "codeExists");
		}
		if (catalogueService.catalogueNameCodeCheck(catalogueDTO, "name")) {
			throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "nameExists"),
					ENTITY_NAME, "nameExists");
		}
		catalogueDTO = catalogueService.saveOrUpdateCatalogue(catalogueMapper.toEntity(catalogueDTO));
		log.info("API createCatalogue ended at " + LocalTime.now());
		return ResponseEntity.status(HttpStatus.CREATED).body(catalogueDTO);
	}

	/**
	 * System takes catalogueDTO object and updates an existing Category object
	 * after checking the following: Catalogue id must not be blank Catalogue Code
	 * or Name should not exist.
	 *
	 * @param catalogueDTO the catalogueDTO
	 * @param catalogueId  the catalogueId
	 * @return catalogueDTO the catalogueDTO
	 * @throws Exception the Exception
	 */
	@PutMapping("/catalogue/{id}")
	public ResponseEntity<?> updateCatalogue(@Valid @RequestBody CatalogueDTO catalogueDTO,
			@PathVariable("id") Long catalogueId) throws Exception {
		log.info("API updateCatalogue started at " + LocalTime.now());
		String errorMessage = "Catalogue.updateCatalogue.";
		Catalogue catalogue = catalogueRepository.findById(catalogueId).orElseThrow(() -> new RecordNotFoundException(
				messageSourceUtility.getMessage(errorMessage + "invalidId"), ENTITY_NAME));
		catalogueDTO.setId(catalogueId);
		if (!catalogueItemRepository.findByCatalogueCodeOrderByLastModifiedTsDesc(catalogue.getCatalogueCode())
				.isEmpty()) {
			throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "itemLinked"),
					ENTITY_NAME, "itemLinked");
		}
		if (catalogueService.catalogueNameCodeCheck(catalogueDTO, "code")) {
			throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "codeExists"),
					ENTITY_NAME, "codeExists");
		}
		if (catalogueService.catalogueNameCodeCheck(catalogueDTO, "name")) {
			throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "nameExists"),
					ENTITY_NAME, "nameExists");
		}
		BeanUtils.copyProperties(catalogueDTO, catalogue);
		catalogueDTO = catalogueService.saveOrUpdateCatalogue(catalogue);
		log.info("API updateCatalogue ended at " + LocalTime.now());
		return ResponseEntity.ok(catalogueDTO);
	}

	/**
	 * System will take deptId as parameter and returns a list of catalogues having
	 * the deptId.
	 *
	 * @param active the active
	 * @param deptId the deptId
	 * @return catalogueDTOList the catalogueDTO list
	 * @throws Exception the Exception
	 */
	@GetMapping("/catalogues/dept/{deptId}")
	public ResponseEntity<?> getAllCatalogues(Boolean active, @PathVariable("deptId") Integer deptId) throws Exception {
		log.info("API getAllCatalogues started at " + LocalTime.now());
		List<CatalogueDTO> catalogueDTOList = new ArrayList<>();
		if (Optional.ofNullable(active).isEmpty()) {
			catalogueDTOList = catalogueService.getAllCatalogues(deptId, null);
		} else {
			catalogueDTOList = catalogueService.getAllCatalogues(deptId, true);
		}
		if (catalogueDTOList.isEmpty()) {
			throw new RecordNotFoundException(messageSourceUtility.getMessage("Catalogue.getAllCatalogues.noRecords"),
					ENTITY_NAME);
		}
		log.info("API getAllCatalogues ended at " + LocalTime.now());
		return ResponseEntity.ok().body(catalogueDTOList);
	}

	/**
	 * Gets the all catalogues paged.
	 *
	 * @param pageable the pageable
	 * @param active   the active
	 * @param deptId   the dept id
	 * @return the all catalogues paged
	 * @throws Exception the exception
	 */
	@GetMapping("/catalogues/dept/{deptId}/pageable")
	public ResponseEntity<?> getAllCatalogues_Pageable(Pageable pageable, Boolean active,
			@PathVariable("deptId") Integer deptId) throws Exception {
		log.info("API getAllCatalogues started at " + LocalTime.now());
		Page<CatalogueDTO> catalogueDTOList = new PageImpl<>(new ArrayList<>());
		if (Optional.ofNullable(active).isEmpty()) {
			catalogueDTOList = catalogueService.getAllCataloguesPagination(pageable, deptId, null);
		} else {
			catalogueDTOList = catalogueService.getAllCataloguesPagination(pageable, deptId, true);
		}
		if (catalogueDTOList.isEmpty()) {
			throw new RecordNotFoundException(messageSourceUtility.getMessage("Catalogue.getAllCatalogues.noRecords"),
					ENTITY_NAME);
		}
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), catalogueDTOList);
		log.info("API getAllCatalogues ended at " + LocalTime.now());
		return ResponseEntity.ok().headers(headers).body(catalogueDTOList.getContent());
	}

	/**
	 * System takes catalogueSearchDTO as parameter and perform the following:
	 * Checks if both catalogue code and name is empty and throws Exception Else
	 * lists all Catalogues based on the parameter.
	 *
	 * @param pageable           the pageable
	 * @param catalogueSearchDTO the catalogueSearchDTO
	 * @return catalogueDTOList the catalogueDTO list
	 * @throws Exception the Exception
	 */
	@PostMapping("/catalogue/search")
	public ResponseEntity<?> searchCatalogues(Pageable pageable,
			@Valid @RequestBody CatalogueSearchDTO catalogueSearchDTO) throws Exception {
		log.info("API searchCatalogues started at " + LocalTime.now());
		String errorMessage = "Catalogue.searchCatalogue.";
		if (!StringUtils.hasText(catalogueSearchDTO.getCatalogueCode())
				&& !StringUtils.hasText(catalogueSearchDTO.getCatalogueName())) {
			throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "noValues"), ENTITY_NAME,
					"noValues");
		}
		BooleanBuilder builder = new BooleanBuilder();
		if (StringUtils.hasText(catalogueSearchDTO.getCatalogueCode())) {
			builder.and(QCatalogue.catalogue.catalogueCode.containsIgnoreCase(catalogueSearchDTO.getCatalogueCode()));
		}
		if (StringUtils.hasText(catalogueSearchDTO.getCatalogueName())) {
			builder.and(QCatalogue.catalogue.catalogueName.containsIgnoreCase(catalogueSearchDTO.getCatalogueName()));
		}
		builder.and(QCatalogue.catalogue.deptId.eq(catalogueSearchDTO.getDeptId()));
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
				Sort.by(Sort.Direction.DESC, "lastModifiedTs"));
		Page<CatalogueDTO> catalogueDTOList = catalogueService.searchCatalogues(builder, pageRequest);
		if (catalogueDTOList.isEmpty()) {
			throw new RecordNotFoundException(messageSourceUtility.getMessage(errorMessage + "noRecords"), ENTITY_NAME);
		}
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), catalogueDTOList);
		log.info("API searchCatalogues ended at " + LocalTime.now());
		return ResponseEntity.ok().headers(headers).body(catalogueDTOList.getContent());
	}

	/**
	 * System takes catalogueId as parameter and returns Catalogue object having
	 * that id.
	 *
	 * @param catalogueId the catalogueId
	 * @return catalogueDTO the catalogueDTO
	 * @throws Exception the Exception
	 */
	@GetMapping("/catalogue/{id}")
	public ResponseEntity<?> getCatalogue(@PathVariable("id") Long catalogueId) throws Exception {
		log.info("API getCatalogue started at " + LocalTime.now());
		Optional<CatalogueDTO> optionalCatalogueDTO = catalogueService.getCatalogue(catalogueId);
		String errorMessage = "Catalogue.getCatalogue.";
		optionalCatalogueDTO.orElseThrow(() -> new RecordNotFoundException(
				messageSourceUtility.getMessage(errorMessage + "invalidId"), ENTITY_NAME));
		log.info("API getCatalogue ended at " + LocalTime.now());
		return ResponseEntity.ok(optionalCatalogueDTO.get());
	}

	/**
	 * System takes catalogueId as parameter and deletes Catalogue object having
	 * that id.
	 *
	 * @param catalogueId the catalogueId
	 * @return catalogueDTO the catalogueDTO
	 * @throws Exception the Exception
	 */
	@DeleteMapping("/catalogue/{id}")
	public ResponseEntity<?> deleteCatalogue(@PathVariable("id") Long catalogueId) throws Exception {
		log.info("API deleteCatalogue started at " + LocalTime.now());
		String errorMessage = "Catalogue.deleteCatalogue.";
		Optional<Catalogue> optionalCatalogue = Optional
				.ofNullable(catalogueRepository.findById(catalogueId).orElseThrow(() -> new RecordNotFoundException(
						messageSourceUtility.getMessage(errorMessage + "invalidId"), ENTITY_NAME)));
		Catalogue catalogue = optionalCatalogue.get();
		if (!catalogueItemRepository.findByCatalogueCodeOrderByLastModifiedTsDesc(catalogue.getCatalogueCode())
				.isEmpty()) {
			throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "itemLinked"),
					ENTITY_NAME, "itemLinked");
		}
		if (!catalogue.getCategories().isEmpty()) {
			throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "categoriesLinked"),
					ENTITY_NAME, "categoriesLinked");
		}
		catalogueService.deleteCatalogue(catalogueId);
		log.info("API deleteCatalogue ended at " + LocalTime.now());
		return ResponseEntity.ok(catalogueMapper.toDto(catalogue));
	}

	/**
	 * Sets activeYn to true or false based on parameter.
	 *
	 * @param catalogueId the catalogueId
	 * @param activeYn    the activeYn
	 * @return catalogueDTO the catalogueDTO
	 * @throws Exception the Exception
	 */
	@PutMapping("/catalogue/{id}/activeYn/{activeYn}")
	public ResponseEntity<?> setCatalogueActiveYn(@PathVariable("id") Long catalogueId,
			@PathVariable("activeYn") Boolean activeYn) throws Exception {
		log.info("setCatalogueActiveYn started at " + LocalTime.now());
		String errorMessage = "Catalogue.setCatalogueActiveYn.";
		Catalogue catalogue = catalogueRepository.findById(catalogueId).orElseThrow(() -> new RecordNotFoundException(
				messageSourceUtility.getMessage(errorMessage + "invalidId"), ENTITY_NAME));
		if (!catalogueItemRepository.findByCatalogueCodeOrderByLastModifiedTsDesc(catalogue.getCatalogueCode())
				.isEmpty()) {
			throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "itemLinked"),
					ENTITY_NAME, "itemLinked");
		}
		catalogue.setActiveYn(activeYn);
		CatalogueDTO catalogueDTO = catalogueService.saveOrUpdateCatalogue(catalogue);
		log.info("setCatalogueActiveYn ended at " + LocalTime.now());
		return ResponseEntity.ok(catalogueDTO);
	}
}
