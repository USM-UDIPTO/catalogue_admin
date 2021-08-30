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
import com.dxc.eproc.catalogue.dto.request.ClassMasterDTO;
import com.dxc.eproc.catalogue.model.FamilyMaster;
import com.dxc.eproc.catalogue.repository.FamilyRepository;
import com.dxc.eproc.catalogue.services.ClassService;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.utils.PaginationUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class ClassController.
 */
@RestController
@RequestMapping("/v1/api")
@Transactional
public class ClassController {

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "ClassMaster";

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(ClassController.class);

	/** The message source utility. */
	@Autowired
	private EprocMessageSourceComponent messageSourceUtility;

	/** The classService. */
	@Autowired
	private ClassService classService;

	/** The family repository. */
	@Autowired
	private FamilyRepository familyRepository;

	/**
	 * Gets the class.
	 *
	 * @param classId the class id
	 * @return the class
	 * @throws Exception the exception
	 */
	@GetMapping("/unspsc-family/class/{id}")
	public ResponseEntity<?> getClass(@PathVariable("id") Long classId) throws Exception {
		log.info("API getClass started at " + LocalTime.now());
		Optional<ClassMasterDTO> optionalClassDTO = classService.getClassMaster(classId);
		String errorMessage = "Class.getClass.";
		optionalClassDTO.orElseThrow(() -> new RecordNotFoundException(
				messageSourceUtility.getMessage(errorMessage + "invalidId"), ENTITY_NAME));
		log.info("API getClass ended at " + LocalTime.now());
		return ResponseEntity.ok(optionalClassDTO.get());
	}

	/**
	 * Gets the class.
	 *
	 * @param classCode the class code
	 * @return the class
	 * @throws Exception the exception
	 */
	@GetMapping("/unspsc-family/class/code/{code}")
	public ResponseEntity<?> getClassByCode(@PathVariable("code") String classCode) throws Exception {
		log.info("API getClassByCode started at " + LocalTime.now());
		Optional<ClassMasterDTO> optionalClassDTO = classService.getClassMasterByCode(classCode);
		String errorMessage = "Class.getClass.";
		optionalClassDTO.orElseThrow(() -> new RecordNotFoundException(
				messageSourceUtility.getMessage(errorMessage + "invalidCode"), ENTITY_NAME));
		log.info("API getClassByCode ended at " + LocalTime.now());
		return ResponseEntity.ok(optionalClassDTO.get());
	}

	/**
	 * Gets the all classes.
	 *
	 * @param pageable the pageable
	 * @param familyId the family id
	 * @return the all classes
	 * @throws Exception the exception
	 */
	@GetMapping("/unspsc-family/{id}/classes")
	public ResponseEntity<?> getAllClasses(Pageable pageable, @PathVariable("id") Long familyId) throws Exception {
		log.info("API getAllClasses started at " + LocalTime.now());
		Optional<FamilyMaster> optionalFamily = familyRepository.findById(familyId);
		optionalFamily.orElseThrow(() -> new RecordNotFoundException(
				messageSourceUtility.getMessage("Family.getFamily.invalidId"), ENTITY_NAME));
		Page<ClassMasterDTO> classMasterDTOList = classService.getAllClasses(pageable, optionalFamily.get());
		if (classMasterDTOList.isEmpty()) {
			throw new RecordNotFoundException(messageSourceUtility.getMessage("Class.getAllClasses.noRecords"),
					ENTITY_NAME);
		}
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), classMasterDTOList);
		log.info("API getAllClasses ended at " + LocalTime.now());
		return ResponseEntity.ok().headers(headers).body(classMasterDTOList.getContent());
	}

}
