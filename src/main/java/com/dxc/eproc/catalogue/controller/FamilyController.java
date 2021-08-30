package com.dxc.eproc.catalogue.controller;

import java.time.LocalTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dxc.eproc.catalogue.config.EprocMessageSourceComponent;
import com.dxc.eproc.catalogue.dto.request.FamilyMasterDTO;
import com.dxc.eproc.catalogue.model.SegmentMaster;
import com.dxc.eproc.catalogue.repository.SegmentRepository;
import com.dxc.eproc.catalogue.services.FamilyService;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.utils.PaginationUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class FamilyController.
 */
@RestController
@RequestMapping("/v1/api")
@Transactional
public class FamilyController {

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "Family";

	/** The log. */
	// The log.
	private final Logger log = LoggerFactory.getLogger(FamilyController.class);

	/** The message source utility. */
	// The message source utility.
	@Autowired
	private EprocMessageSourceComponent messageSourceUtility;

	/** The segment repository. */
	@Autowired
	private SegmentRepository segmentRepository;

	/** The family service. */
	// The family service.
	@Autowired
	private FamilyService familyService;

	/**
	 * Gets the family.
	 *
	 * @param familyId the family id
	 * @return the family
	 * @throws Exception the exception
	 */
	@GetMapping("/unspsc-segment/family/{id}")
	public ResponseEntity<?> getFamily(@PathVariable("id") Long familyId) throws Exception {
		log.info("API getFamily started at " + LocalTime.now());
		Optional<FamilyMasterDTO> optionalFamilyDto = familyService.getFamilyMaster(familyId);
		String errorMessage = "Family.getFamily.";
		optionalFamilyDto.orElseThrow(() -> new RecordNotFoundException(
				messageSourceUtility.getMessage(errorMessage + "invalidId"), ENTITY_NAME));
		log.info("API getFamily ended at " + LocalTime.now());
		return ResponseEntity.ok(optionalFamilyDto.get());
	}

	/**
	 * Gets the family by code.
	 *
	 * @param familyCode the family code
	 * @return the family by code
	 * @throws Exception the exception
	 */
	@GetMapping("/unspsc-segment/family/code/{code}")
	public ResponseEntity<?> getFamilyByCode(@PathVariable("code") String familyCode) throws Exception {
		log.info("API getFamilyByCode started at " + LocalTime.now());
		Optional<FamilyMasterDTO> optionalFamilyDto = familyService.getFamilyMasterByCode(familyCode);
		String errorMessage = "Family.getFamily.";
		optionalFamilyDto.orElseThrow(() -> new RecordNotFoundException(
				messageSourceUtility.getMessage(errorMessage + "invalidCode"), ENTITY_NAME));
		log.info("API getFamilyByCode ended at " + LocalTime.now());
		return ResponseEntity.ok(optionalFamilyDto.get());
	}

	/**
	 * Gets the all families.
	 *
	 * @param segmentId the segment id
	 * @param pageable  the pageable
	 * @return the all families
	 * @throws Exception the exception
	 */
	@GetMapping("/unspsc-segment/{id}/families")
	public ResponseEntity<?> getAllFamilies(@PathVariable("id") Long segmentId, Pageable pageable) throws Exception {
		log.info("API getAllFamilies started at " + LocalTime.now());
		Optional<SegmentMaster> optionalSegment = segmentRepository.findById(segmentId);
		optionalSegment.orElseThrow(() -> new RecordNotFoundException(
				messageSourceUtility.getMessage("Segment.getSegment.invalidId"), ENTITY_NAME));
		Page<FamilyMasterDTO> familyMasterDTOList = familyService.getAllFamilies(pageable, optionalSegment.get());
		if (familyMasterDTOList.isEmpty()) {
			throw new RecordNotFoundException(messageSourceUtility.getMessage("Family.getAllFamilies.noRecords"),
					ENTITY_NAME);
		}
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), familyMasterDTOList);
		log.info("API getAllFamilies ended at " + LocalTime.now());
		return ResponseEntity.ok().headers(headers).body(familyMasterDTOList.getContent());
	}

}
