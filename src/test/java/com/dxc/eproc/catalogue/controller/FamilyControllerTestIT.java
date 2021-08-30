package com.dxc.eproc.catalogue.controller;

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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.catalogue.model.FamilyMaster;
import com.dxc.eproc.catalogue.model.SegmentMaster;
import com.dxc.eproc.catalogue.repository.FamilyRepository;
import com.dxc.eproc.catalogue.repository.SegmentRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class FamilyControllerTestIT.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FamilyControllerTestIT extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private final static Logger log = LoggerFactory.getLogger(FamilyControllerTestIT.class);

	/** The family master. */
	private static FamilyMaster familyMaster;

	/** The segment master. */
	private static SegmentMaster segmentMaster;

	/** The family repository. */
	@Autowired
	private FamilyRepository familyRepository;

	/** The segment repository. */
	@Autowired
	private SegmentRepository segmentRepository;

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
	}

	/**
	 * Gets the family master.
	 *
	 * @return the family master
	 */
	@BeforeMethod
	public void getFamilyMaster() {
		familyMaster = new FamilyMaster();
		familyMaster.setCode("testCode");
		familyMaster.setTitle("testTitle");
		segmentMaster = new SegmentMaster();
		segmentMaster.setCode("testSegmentCode");
		segmentMaster.setTitle("testSegmentTitle");
		SegmentMaster segment = segmentRepository.save(segmentMaster);
		familyMaster.setSegmentMaster(segment);
	}

	/**
	 * Gets the family success test.
	 *
	 * @return the family success test
	 * @throws Exception the exception
	 */
	@Test
	public void getFamilySuccessTest() throws Exception {
		familyMaster = familyRepository.save(familyMaster);
		String uri = "/v1/api/unspsc-segment/family/" + familyMaster.getId();
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("getFamilySuccessTest successful");
	}

	/**
	 * Gets the family invalid id test.
	 *
	 * @return the family invalid id test
	 * @throws Exception the exception
	 */
	@Test
	public void getFamilyInvalidIdTest() throws Exception {
		String uri = "/v1/api/unspsc-segment/family/104";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getFamilyInvalidIdTest successful");
	}

	/**
	 * Gets the family by code success test.
	 *
	 * @return the family by code success test
	 * @throws Exception the exception
	 */
	@Test
	public void getFamilyByCodeSuccessTest() throws Exception {
		familyMaster.setCode("testCodenew");
		familyMaster = familyRepository.save(familyMaster);
		String uri = "/v1/api/unspsc-segment/family/code/" + familyMaster.getCode();
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("getFamilyByCodeSuccessTest successful");
	}

	/**
	 * Gets the family by code invalid id test.
	 *
	 * @return the family by code invalid id test
	 * @throws Exception the exception
	 */
	@Test
	public void getFamilyByCodeInvalidIdTest() throws Exception {
		String uri = "/v1/api/unspsc-segment/family/code/code1";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getFamilyByCodeInvalidIdTest successful");
	}

	/**
	 * Gets the all families success test.
	 *
	 * @return the all families success test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllFamiliesSuccessTest() throws Exception {
		familyMaster = familyRepository.save(familyMaster);
		String uri = "/v1/api/unspsc-segment/" + familyMaster.getSegmentMaster().getId() + "/families";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("getAllFamiliesSuccessTest successful");
	}

	/**
	 * Gets the families invalid segment id test.
	 *
	 * @return the families invalid segment id test
	 * @throws Exception the exception
	 */
	@Test
	public void getFamiliesInvalidSegmentIdTest() throws Exception {
		String uri = "/v1/api/unspsc-segment/103/families";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getFamiliesInvalidSegmentIdTest successful");
	}

	/**
	 * Gets the all families no record test.
	 *
	 * @return the all families no record test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllFamiliesNoRecordTest() throws Exception {
		SegmentMaster segment = segmentRepository.save(segmentMaster);
		String uri = "/v1/api/unspsc-segment/" + segment.getId() + "/families";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getAllFamiliesNoRecordTest successful");
	}
}
