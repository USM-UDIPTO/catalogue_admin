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
import com.dxc.eproc.catalogue.dto.request.SegmentMasterDTO;
import com.dxc.eproc.catalogue.services.SegmentService;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.utils.PaginationUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class SegmentController.
 */
@RestController
@Transactional
@RequestMapping("/v1/api")
public class SegmentController {

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "Segment";

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(SegmentController.class);

	/** The message source utility. */
	@Autowired
	private EprocMessageSourceComponent messageSourceUtility;

	/** The segment service. */
	@Autowired
	private SegmentService segmentService;

	/**
	 * Gets the segment.
	 *
	 * @param segmentId the segment id
	 * @return the segment
	 * @throws Exception the exception
	 */
	@GetMapping("/unspsc-segment/{id}")
	public ResponseEntity<?> getSegment(@PathVariable("id") Long segmentId) throws Exception {
		log.info("API getSegment started at" + LocalTime.now());
		Optional<SegmentMasterDTO> optionalSegmentDto = segmentService.getSegmentMaster(segmentId);
		String errorMessage = "Segment.getSegment.";
		optionalSegmentDto.orElseThrow(() -> new RecordNotFoundException(
				messageSourceUtility.getMessage(errorMessage + "invalidId"), ENTITY_NAME));
		log.info("API getSegment ended at " + LocalTime.now());
		return ResponseEntity.ok(optionalSegmentDto.get());
	}

	/**
	 * Gets the segment by code.
	 *
	 * @param segmentCode the segment code
	 * @return the segment by code
	 * @throws Exception the exception
	 */
	@GetMapping("/unspsc-segment/code/{code}")
	public ResponseEntity<?> getSegmentByCode(@PathVariable("code") String segmentCode) throws Exception {
		log.info("API getSegmentByCode started at" + LocalTime.now());
		Optional<SegmentMasterDTO> optionalSegmentDto = segmentService.getSegmentMasterByCode(segmentCode);
		String errorMessage = "Segment.getSegment.";
		optionalSegmentDto.orElseThrow(() -> new RecordNotFoundException(
				messageSourceUtility.getMessage(errorMessage + "invalidCode"), ENTITY_NAME));
		log.info("API getSegmentByCode ended at " + LocalTime.now());
		return ResponseEntity.ok(optionalSegmentDto.get());
	}

	/**
	 * Gets the all segments.
	 *
	 * @param pageable the pageable
	 * @return the all segments
	 * @throws Exception the exception
	 */
	@GetMapping("/unspsc-segments")
	public ResponseEntity<?> getAllSegments(Pageable pageable) throws Exception {
		log.info("API getAllSegments started at " + LocalTime.now());

		Page<SegmentMasterDTO> segmentMasterDTOList = segmentService.getAllSegments(pageable);

		if (segmentMasterDTOList.isEmpty()) {
			throw new RecordNotFoundException(messageSourceUtility.getMessage("Segment.getAllSegments.noRecords"),
					ENTITY_NAME);
		}

		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), segmentMasterDTOList);
		log.info("API getAllSegments ended at " + LocalTime.now());
		return ResponseEntity.ok().headers(headers).body(segmentMasterDTOList.getContent());
	}

}
