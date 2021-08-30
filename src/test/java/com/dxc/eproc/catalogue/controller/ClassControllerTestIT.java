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
import com.dxc.eproc.catalogue.model.FamilyMaster;
import com.dxc.eproc.catalogue.repository.ClassRepository;
import com.dxc.eproc.catalogue.repository.FamilyRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class ClassControllerTestIT.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClassControllerTestIT extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private final static Logger log = LoggerFactory.getLogger(ClassControllerTestIT.class);

	/** The class master. */
	private static ClassMaster classMaster;

	/** The family master. */
	private static FamilyMaster familyMaster;

	/** The class repository. */
	@Autowired
	private ClassRepository classRepository;

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
	}

	/**
	 * Gets the class master.
	 *
	 * @return the class master
	 */
	@BeforeMethod
	public void getClassMaster() {
		familyMaster = new FamilyMaster();
		familyMaster.setCode("testFamilyCode");
		familyMaster.setTitle("testFamilyTitle");
		familyRepository.save(familyMaster);
		classMaster = new ClassMaster();
		classMaster.setCode("testCode");
		classMaster.setTitle("testTitle");
		classMaster.setFamilyMaster(familyMaster);
	}

	/**
	 * Gets the class master success test.
	 *
	 * @return the class master success test
	 * @throws Exception the exception
	 */
	@Test
	public void getClassMasterSuccessTest() throws Exception {
		classMaster = classRepository.save(classMaster);
		String uri = "/v1/api/unspsc-family/class/" + classMaster.getId();
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("getClassMasterSuccessTest successful");
	}

	/**
	 * Gets the class master invalid id test.
	 *
	 * @return the class master invalid id test
	 * @throws Exception the exception
	 */
	@Test
	public void getClassMasterInvalidIdTest() throws Exception {
		String uri = "/v1/api/unspsc-family/class/1003";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getClassMasterInvalidIdTest successful");
	}

	/**
	 * Gets the all classes success test.
	 *
	 * @return the all classes success test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllClassesSuccessTest() throws Exception {
		classMaster = classRepository.save(classMaster);
		String uri = "/v1/api//unspsc-family/" + classMaster.getFamilyMaster().getId() + "/classes";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("getAllClassesDTOSuccessTest successful");
	}

	/**
	 * Gets the all classes invalid id test.
	 *
	 * @return the all classes invalid id test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllClassesInvalidIdTest() throws Exception {
		String uri = "/v1/api/unspsc-family/102/classes";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getAllClassesInvalidIdTest successful");
	}

	/**
	 * Gets the all classes no records test.
	 *
	 * @return the all classes no records test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllClassesNoRecordsTest() throws Exception {
		FamilyMaster family = familyRepository.save(familyMaster);
		String uri = "/v1/api/unspsc-family/" + family.getId() + "/classes";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getAllClassesNoRecordsTest successful");
	}
}