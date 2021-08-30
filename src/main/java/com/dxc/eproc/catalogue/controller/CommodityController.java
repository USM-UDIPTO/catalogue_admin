package com.dxc.eproc.catalogue.controller;

import java.time.LocalTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dxc.eproc.catalogue.config.EprocMessageSourceComponent;
import com.dxc.eproc.catalogue.dto.request.CommodityMasterDTO;
import com.dxc.eproc.catalogue.model.ClassMaster;
import com.dxc.eproc.catalogue.repository.ClassRepository;
import com.dxc.eproc.catalogue.services.CommodityService;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.utils.PaginationUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class CommodityController.
 */
@RestController
@Transactional
@RequestMapping("/v1/api")
public class CommodityController {

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "CommodityMaster";

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(CommodityController.class);

	/** The message source utility. */
	@Autowired
	private EprocMessageSourceComponent messageSourceUtility;

	/** The commodityService. */
	@Autowired
	private CommodityService commodityService;

	/** The class repository. */
	@Autowired
	private ClassRepository classRepository;

	/**
	 * Gets the commodity.
	 *
	 * @param commodityId the commodity id
	 * @return the commodity
	 * @throws Exception the exception
	 */
	@GetMapping("/unspsc-class/commodity/{id}")
	public ResponseEntity<?> getCommodity(@PathVariable("id") Long commodityId) throws Exception {
		log.info("API getCommodity started at " + LocalTime.now());
		Optional<CommodityMasterDTO> optionalCommodityDTO = commodityService.getCommodityMaster(commodityId);
		String errorMessage = "Commodity.getCommodity.";
		optionalCommodityDTO.orElseThrow(() -> new RecordNotFoundException(
				messageSourceUtility.getMessage(errorMessage + "invalidId"), ENTITY_NAME));
		log.info("API getCommodity ended at " + LocalTime.now());
		return ResponseEntity.ok(optionalCommodityDTO.get());
	}

	/**
	 * Gets the commodity.
	 *
	 * @param commodityCode the commodity code
	 * @return the commodity
	 * @throws Exception the exception
	 */
	@GetMapping("/unspsc-class/commodity/code/{code}")
	public ResponseEntity<?> getCommodityByCode(@PathVariable("code") String commodityCode) throws Exception {
		log.info("API getCommodityByCode started at " + LocalTime.now());
		Optional<CommodityMasterDTO> optionalCommodityDTO = commodityService.getCommodityMasterByCode(commodityCode);
		String errorMessage = "Commodity.getCommodity.";
		optionalCommodityDTO.orElseThrow(() -> new RecordNotFoundException(
				messageSourceUtility.getMessage(errorMessage + "invalidCode"), ENTITY_NAME));
		log.info("API getCommodityByCode ended at " + LocalTime.now());
		return ResponseEntity.ok(optionalCommodityDTO.get());
	}

	/**
	 * Gets the all commodities.
	 *
	 * @param pageable the pageable
	 * @param classId  the class id
	 * @return the all commodities
	 * @throws Exception the exception
	 */
	@GetMapping("/unspsc-class/{id}/commodities")
	public ResponseEntity<?> getAllCommodities(Pageable pageable, @PathVariable("id") Long classId) throws Exception {
		log.info("API getAllCommodities started at " + LocalTime.now());
		Optional<ClassMaster> optionalClass = classRepository.findById(classId);
		optionalClass.orElseThrow(() -> new RecordNotFoundException(
				messageSourceUtility.getMessage("Class.getClass.invalidId"), ENTITY_NAME));
		Page<CommodityMasterDTO> commodityMasterDTOList = commodityService.getAllCommodities(pageable,
				optionalClass.get());
		if (commodityMasterDTOList.isEmpty()) {
			throw new RecordNotFoundException(messageSourceUtility.getMessage("Commodity.getAllCommodities.noRecords"),
					ENTITY_NAME);
		}
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
				ServletUriComponentsBuilder.fromCurrentRequest(), commodityMasterDTOList);
		log.info("API getAllCommodities ended at " + LocalTime.now());
		return ResponseEntity.ok().headers(headers).body(commodityMasterDTOList.getContent());
	}
}
