package com.dxc.eproc.catalogue.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dxc.eproc.catalogue.model.FamilyMaster;
import com.dxc.eproc.catalogue.model.SegmentMaster;
import com.dxc.eproc.catalogue.repository.FamilyRepository;
import com.dxc.eproc.catalogue.repository.SegmentRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class SegmentControllerTestIT.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SegmentControllerTestIT extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private final static Logger log = LoggerFactory.getLogger(SegmentControllerTestIT.class);

	/** The segment master. */
	private static SegmentMaster segmentMaster;

	/** The segment repository. */
	@Autowired
	private SegmentRepository segmentRepository;

	/** The family repository. */
	@Autowired
	private FamilyRepository familyRepository;

	/** The mvc. */
	@Autowired
	private MockMvc mvc;

	/**
	 * Inits the.
	 */
	@BeforeClass
	public void init() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");

		System.setProperty("spring.profiles.active", "test");
		segmentMaster = new SegmentMaster();
		segmentMaster.setCode("testCode");
		segmentMaster.setTitle("testTitle");
	}

//	/**
//	 * Gets the segment master.
//	 *
//	 * @return the segment master
//	 */
//	@BeforeMethod
//	public void getSegmentMaster() {
//		segmentMaster = new SegmentMaster();
//		segmentMaster.setCode("testCode");
//		segmentMaster.setTitle("testTitle");
//	}

	/**
	 * Gets the segment success test.
	 *
	 * @return the segment success test
	 * @throws Exception the exception
	 */
	@Test
	public void getSegmentSuccessTest() throws Exception {
		segmentMaster.setId(100L);
		SegmentMaster segment = segmentRepository.save(segmentMaster);
		String uri = "/v1/api/unspsc-segment/" + segment.getId();
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("getSegmentSuccessTest successful");
	}

	/**
	 * Gets the segment master invalid id test.
	 *
	 * @return the segment master invalid id test
	 * @throws Exception the exception
	 */
	@Test
	public void getSegmentMasterInvalidIdTest() throws Exception {
		String uri = "/v1/api/unspsc-segment/103";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getSegmentMasterInvalidIdTest successful");
	}

	/**
	 * Gets the segment by code success test.
	 *
	 * @return the segment by code success test
	 * @throws Exception the exception
	 */
	@Test
	public void getSegmentByCodeSuccessTest() throws Exception {
		segmentMaster.setCode("seccess");
		SegmentMaster segment = segmentRepository.save(segmentMaster);
		String uri = "/v1/api/unspsc-segment/code/" + segment.getCode();
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("getSegmentByCodeSuccessTest successful");
	}

	/**
	 * Gets the segment by code invalid code test.
	 *
	 * @return the segment by code invalid code test
	 * @throws Exception the exception
	 */
	@Test
	public void getSegmentByCodeInvalidCodeTest() throws Exception {
		String uri = "/v1/api/unspsc-segment/code/test1";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getSegmentByCodeInvalidCodeTest successful");
	}

	/**
	 * Gets the all segment master success test.
	 *
	 * @return the all segment master success test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllSegmentMasterSuccessTest() throws Exception {
		segmentRepository.save(segmentMaster);
		String uri = "/v1/api/unspsc-segments";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("getAllSegmentMasterSuccessTest successful");
	}

	/**
	 * Gets the all segment master no records test.
	 *
	 * @return the all segment master no records test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllSegmentMasterNoRecordsTest() throws Exception {
		List<FamilyMaster> familyList = familyRepository.findAll().stream().map(family -> {
			family.setSegmentMaster(null);
			return family;
		}).collect(Collectors.toList());
		familyRepository.saveAll(familyList);
		segmentRepository.deleteAll();
		String uri = "/v1/api/unspsc-segments";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getAllSegmentMasterNoRecordsTest successful");
	}

}
