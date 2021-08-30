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

import com.dxc.eproc.catalogue.model.ClassMaster;
import com.dxc.eproc.catalogue.model.CommodityMaster;
import com.dxc.eproc.catalogue.repository.ClassRepository;
import com.dxc.eproc.catalogue.repository.CommodityRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class CommodityControllerTestIT.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CommodityControllerTestIT extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private final static Logger log = LoggerFactory.getLogger(CommodityControllerTestIT.class);

	/** The commodity. */
	private static CommodityMaster commodityMaster;

	/** The class master. */
	private static ClassMaster classMaster;

	/** The commodity repository. */
	@Autowired
	private CommodityRepository commodityRepository;

	/** The class repository. */
	@Autowired
	private ClassRepository classRepository;

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
	 * Gets the commodity.
	 *
	 * @return the commodity
	 */
	@BeforeMethod
	public void getCommodity() {
		commodityMaster = new CommodityMaster();
		commodityMaster.setCode("testCode");
		commodityMaster.setTitle("testTitle");
		classMaster = new ClassMaster();
		classMaster.setCode("testClassCode");
		classMaster.setTitle("testClassTitle");
		ClassMaster classM = classRepository.save(classMaster);
		commodityMaster.setClassMaster(classM);
	}

	/**
	 * Gets the commodity success test.
	 *
	 * @return the commodity success test
	 * @throws Exception the exception
	 */
	@Test
	public void getCommoditySuccessTest() throws Exception {
		commodityMaster = commodityRepository.save(commodityMaster);
		String uri = "/v1/api/unspsc-class/commodity/" + commodityMaster.getId();
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("getCommoditySuccessTest successful");
	}

	/**
	 * Gets the commodity invalid id test.
	 *
	 * @return the commodity invalid id test
	 * @throws Exception the exception
	 */
	@Test
	public void getCommodityInvalidIdTest() throws Exception {
		String uri = "/v1/api/unspsc-class/commodity/1003";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getCommodityInvalidIdTest successful");
	}

	/**
	 * Gets the all commodity success test.
	 *
	 * @return the all commodity success test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllCommoditySuccessTest() throws Exception {
		commodityMaster = commodityRepository.save(commodityMaster);
		String uri = "/v1/api/unspsc-class/" + commodityMaster.getClassMaster().getId() + "/commodities";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("getAllCommoditySuccessTest successful");
	}

	/**
	 * Gets the all commodity invalid id test.
	 *
	 * @return the all commodity invalid id test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllCommodityInvalidIdTest() throws Exception {
		String uri = "/v1/api/unspsc-class/103/commodities";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getAllCommodityNoRecordsTest successful");
	}

	/**
	 * Gets the all commodity no records test.
	 *
	 * @return the all commodity no records test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllCommodityNoRecordsTest() throws Exception {
		ClassMaster classM = classRepository.save(classMaster);
		String uri = "/v1/api/unspsc-class/" + classM.getId() + "/commodities";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getAllCommodityNoRecordsTest successful");
	}

}
