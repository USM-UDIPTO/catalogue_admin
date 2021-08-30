package com.dxc.eproc.catalogue.controller;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
import com.dxc.eproc.catalogue.dto.request.SpecificationDTO;
import com.dxc.eproc.catalogue.dto.request.SpecificationDetailsDTO;
import com.dxc.eproc.catalogue.dto.request.SpecificationItemDTO;
import com.dxc.eproc.catalogue.dto.request.SpecificationSearchDTO;
import com.dxc.eproc.catalogue.mapper.SpecificationMapper;
import com.dxc.eproc.catalogue.model.CatalogueItem;
import com.dxc.eproc.catalogue.model.Specification;
import com.dxc.eproc.catalogue.repository.CatalogueItemRepository;
import com.dxc.eproc.catalogue.repository.SpecificationRepository;
import com.dxc.eproc.catalogue.services.SpecificationService;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.utils.PaginationUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class SpecificationController.
 */
@RestController
@Transactional
@RequestMapping("/v1/api")
public class SpecificationController {

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "Specification";

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(SpecificationController.class);

	/** The message source utility. */
	@Autowired
	private EprocMessageSourceComponent messageSourceUtility;

	/** The specification service. */
	@Autowired
	private SpecificationService specificationService;

	/** The specification repository. */
	@Autowired
	private SpecificationRepository specificationRepository;

	/** The catalogue item repository. */
	@Autowired
	private CatalogueItemRepository catalogueItemRepository;

	/** The specification mapper. */
	@Autowired
	private SpecificationMapper specificationMapper;

	/**
	 * System will take SpecificationDTO as parameter and perform the following:
	 * Specification id must be null else throws Exception Checks if Specification
	 * Name is unique in the department, else throws Exception If no Exception
	 * occurs, system create a new specfication.
	 *
	 * @param specificationDTO the SpecificationDTO
	 * @return specificationDTO the specificationDTO
	 * @throws Exception the Exception
	 */
	@PostMapping("/specification")
	public ResponseEntity<?> createSpecification(@Valid @RequestBody SpecificationDTO specificationDTO)
			throws Exception {
		log.info("API createSpecification started at " + LocalTime.now());
		String errorMessage = "Specification.saveSpecification.";
		if (specificationDTO.getId() != null) {
			throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "nullId"), ENTITY_NAME,
					"nullId");
		}
		if (specificationService.specificationNameCheck(specificationDTO)) {
			throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "nameExists"),
					ENTITY_NAME, "nameExists");
		}
		SpecificationDTO specification = specificationService
				.saveOrUpdateSpecification(specificationMapper.toEntity(specificationDTO));
		log.info("API createSpecification ended at " + LocalTime.now());
		return ResponseEntity.status(HttpStatus.CREATED).body(specification);
	}

	/**
	 * System will take SpecificationDTO and specId as parameters and perform the
	 * following: Checks if any Specification exists based on specId, else throws
	 * Exception Checks if Specification Name is unique in the department, else
	 * throws Exception If no Exception occurs, system create a new specfication.
	 *
	 * @param specificationDTO the specificationDTO
	 * @param specId           the specId
	 * @return specificationDTO the specificationDTO
	 * @throws Exception the Exception
	 */
	@PutMapping("/specification/{id}")
	public ResponseEntity<?> updateSpecification(@Valid @RequestBody SpecificationDTO specificationDTO,
			@PathVariable("id") @NotNull Long specId) throws Exception {
		log.info("API updateSpecification started at " + LocalTime.now());
		String errorMessage = "Specification.updateSpecification.";
		Specification specification = specificationRepository.findById(specId).orElseThrow(
				() -> new RecordNotFoundException(messageSourceUtility.getMessage(errorMessage + "invalidId"),
						ENTITY_NAME));
		specificationDTO.setId(specId);
		if (specificationService.specificationNameCheck(specificationDTO)) {
			throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "nameExists"),
					ENTITY_NAME, "nameExists");
		}
		BeanUtils.copyProperties(specificationDTO, specification);
		specificationDTO = specificationService.saveOrUpdateSpecification(specification);
		log.info("API updateSpecification ended at " + LocalTime.now());
		return ResponseEntity.ok(specificationDTO);
	}

	/**
	 * Returns a list of specifications having the deptId else throws Exception.
	 *
	 * @param pageable the pageable
	 * @param deptId   the deptId
	 * @return specificationDTOList the specificationDTOList
	 * @throws Exception the Exception
	 */
	@GetMapping("/specification/dept/{deptId}")
	public ResponseEntity<?> getAllSpecifications(Pageable pageable, @PathVariable("deptId") @NotNull Integer deptId)
			throws Exception {
		log.info("API getAllSpecificationsForDepartment started at " + LocalTime.now());
		Page<SpecificationDTO> specificationDTOList = specificationService
				.getAllSpecifications(pageable, deptId);
		if (specificationDTOList.isEmpty()) {
			throw new RecordNotFoundException("No records found!!", ENTITY_NAME);
		}
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), specificationDTOList);
		log.info("API getAllSpecifications ended at " + LocalTime.now());
		return ResponseEntity.ok().headers(headers).body(specificationDTOList.getContent());
	}

	/**
	 * Returns a list of specifications having the deptId and specName else throws
	 * Exception.
	 *
	 * @param pageable               the pageable
	 * @param specificationSearchDTO the specificationSearchDTO
	 * @return specificationDTOList the specificationDTOList
	 * @throws Exception the Exception
	 */
	@PostMapping("/specification/search")
	public ResponseEntity<?> searchSpecifications(Pageable pageable,
			@Valid @RequestBody SpecificationSearchDTO specificationSearchDTO) throws Exception {
		log.info("API searchSpecifications started at " + LocalTime.now());
		PageRequest page = PageRequest.of(pageable.getPageNumber(), 5);
		Page<SpecificationDTO> specificationDTOList = null;
		if (StringUtils.isBlank(specificationSearchDTO.getSpecName())) {
			specificationDTOList = specificationService.getAllSpecifications(page, specificationSearchDTO.getDeptId());
		} else {
			specificationDTOList = specificationService.searchSpecifications(page, specificationSearchDTO.getDeptId(),
					specificationSearchDTO.getSpecName());
		}
		if (specificationDTOList.isEmpty()) {
			throw new RecordNotFoundException("No records found!!", ENTITY_NAME);
		}
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), specificationDTOList);
		log.info("API searchSpecifications ended at " + LocalTime.now());
		return ResponseEntity.ok().headers(headers).body(specificationDTOList.getContent());
	}

	/**
	 * Gets the specifications for item.
	 *
	 * @param pageable the pageable
	 * @param itemCode the item code
	 * @return the specifications for item
	 * @throws Exception the exception
	 */
	@GetMapping("/specification/item/{code}")
	public ResponseEntity<?> getSpecificationsForItem(Pageable pageable, @PathVariable("code") String itemCode)
			throws Exception {
		log.info("API getSpecificationsForItem started at " + LocalTime.now());
		SpecificationItemDTO specificationItemDTO = new SpecificationItemDTO();
		CatalogueItem catalogueItem = catalogueItemRepository.findByItemCode(itemCode)
				.orElseThrow(() -> new RecordNotFoundException(
						messageSourceUtility.getMessage("CatalogueItem.getCatalogueItem.notAvailable"), ENTITY_NAME));
		specificationItemDTO.setItemCode(catalogueItem.getItemCode());
		specificationItemDTO.setItemName(catalogueItem.getItemName());
		Map<String, String> specifications = catalogueItem.getSpecifications();
		if (specifications.isEmpty()) {
			throw new RecordNotFoundException(
					messageSourceUtility.getMessage("Specification.getSpecificationsForItem.noSpecifications"), ENTITY_NAME);
		}
		List<SpecificationDetailsDTO> specDetails = specifications.entrySet().stream().map(s -> {
			SpecificationDetailsDTO specDetail = new SpecificationDetailsDTO();
			specDetail.setSpecName(s.getKey());
			specDetail.setSpecValue(s.getValue());
			return specDetail;
		}).collect(Collectors.toList());
		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), specDetails.size());
		specificationItemDTO.setSpecDetails(specDetails.subList(start, end));
		return ResponseEntity.ok(specificationItemDTO);
	}

	/**
	 * Returns SpecificationDTO based on specificationId.
	 *
	 * @param specificationId the specificationId
	 * @return specificationDTO the specificationDTO
	 * @throws Exception the Exception
	 */
	@GetMapping("/specification/{id}")
	public ResponseEntity<?> getSpecification(@PathVariable("id") @NotNull Long specificationId) throws Exception {
		log.info("getSpecification started at " + LocalTime.now());
		Optional<SpecificationDTO> optionalSpecificationDTO = specificationService.getSpecification(specificationId);
		optionalSpecificationDTO.orElseThrow(() -> new RecordNotFoundException(
				messageSourceUtility.getMessage("Specification.getSpecification.invalidId"), ENTITY_NAME));
		log.info("getSpecification ended at" + LocalTime.now());
		return ResponseEntity.ok(optionalSpecificationDTO.get());
	}

	/**
	 * Sets activeYn to true or false based on parameter.
	 *
	 * @param specificationId the specificationId
	 * @param activeYn        the activeYn
	 * @return specificationDTO the specificationDTO
	 * @throws Exception the Exception
	 */
	@PutMapping("/specification/{id}/activeYn/{activeYn}")
	public ResponseEntity<?> setSpecificationActiveYn(@PathVariable("id") @NotNull Long specificationId,
			@PathVariable("activeYn") Boolean activeYn) throws Exception {
		log.info("setSpecificationActiveYn started at " + LocalTime.now());
		String errorMessage = "Specification.setSpecificationActiveYn.";
		Specification specification = specificationRepository.findById(specificationId).orElseThrow(
				() -> new RecordNotFoundException(messageSourceUtility.getMessage(errorMessage + "invalidId"),
						ENTITY_NAME));
		specification.setActiveYn(activeYn);
		SpecificationDTO specificationDTO = specificationService.saveOrUpdateSpecification(specification);
		log.info("setSpecificationActiveYn ended at " + LocalTime.now());
		return ResponseEntity.ok(specificationDTO);
	}

	/**
	 * System deletes a Specification based on specificationId.
	 *
	 * @param specificationId the specificationId
	 * @return specificationDTO the specificationDTO
	 * @throws Exception the Exception
	 */
	@DeleteMapping("/specification/{id}")
	public ResponseEntity<?> deleteSpecification(@PathVariable("id") @NotNull Long specificationId) throws Exception {
		log.info("deleteSpecification started at " + LocalTime.now());
		String errorMessage = "Specification.deleteSpecification.";
		Specification specification = specificationRepository.findById(specificationId).orElseThrow(
				() -> new RecordNotFoundException(messageSourceUtility.getMessage(errorMessage + "invalidId"),
						ENTITY_NAME));
		specificationService.deleteSpecification(specificationId);
		log.info("deleteSpecification ended at " + LocalTime.now());
		return ResponseEntity.ok(specificationMapper.toDto(specification));
	}
}