/**
 * 
 */
package com.dxc.eproc.catalogue.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dxc.eproc.catalogue.model.Specification;

// TODO: Auto-generated Javadoc
/**
 * The CatalogueRepositoryTest.
 */
@SpringBootTest
@ActiveProfiles("test")
public class SpecificationRepositoryTest extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(SpecificationRepositoryTest.class);

	/** The Specification info. */
	private static Specification specification;

	/** The specification repository. */
	@Autowired
	SpecificationRepository specificationRepository;

	/**
	 * Inits the.
	 */
	@BeforeClass
	public static void init() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");
		System.setProperty("spring.profiles.active", "test");
	}

	/**
	 * Gets the specification.
	 *
	 * @return the specification
	 */
	public Specification getSpecification() {
		Specification specification = new Specification();
		specification.setSpecName("testName");
		specification.setDeptId(1);
		specification.setDeptName("testDept");
		return specification;
	}

	/**
	 * Save specification test.
	 */
	@Test
	public void saveSpecificationTest() {
		try {
			specification = getSpecification();
			specification = specificationRepository.save(specification);
			System.out.println(specification.toString());
			Assert.assertNotNull(specification.getId());
			log.info("saveCatalogueTest successful!");
		} catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("saveCatalogueTest failed!");
		}
	}

	/**
	 * Update specification test.
	 */
	@Test(dependsOnMethods = "saveSpecificationTest")
	public void updateSpecificationTest() {
		try {
			String beforeName = specification.getSpecName();
			specification = specificationRepository.findById(specification.getId()).get();
			specification.setSpecName("updatedName");
			specification = specificationRepository.save(specification);
			String afterName = specification.getSpecName();
			Assert.assertNotEquals(beforeName, afterName);
			System.out.println(specification.toString());
			log.info("updateCatalogueTest successful!");
		} catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("updateCatalogueTest failed!");
		}
	}

	/**
	 * Gets the all specifications test.
	 *
	 * @return the all specifications test
	 */
	@Test(dependsOnMethods = "saveSpecificationTest")
	public void getAllSpecificationsTest() {
		try {
			List<Specification> specifications = specificationRepository.findAll();
			System.out.println(specifications.toString());
			Assert.assertNotNull(specifications);
			log.info("getAllSpecificationsTest successful!");
		} catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("getAllSpecificationsTest failed!");
		}
	}

	/**
	 * Gets the specification by id test.
	 *
	 * @return the specification by id test
	 */
	@Test(dependsOnMethods = "saveSpecificationTest")
	public void getSpecificationByIdTest() {
		try {
			Specification foundSpecification = specificationRepository.findById(specification.getId()).get();
			System.out.println(foundSpecification.toString());
			Assert.assertEquals(specification.getSpecName(), foundSpecification.getSpecName());
			log.info("getSpecificationByIdTest successful!");
		} catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("getSpecificationByIdTest failed!");
		}
	}

	/**
	 * Delete specification test.
	 */
	@Test
	public void deleteSpecificationTest() {
		try {
			specificationRepository.deleteAll();
			List<Specification> specifications = specificationRepository.findAll();
			Assert.assertTrue(specifications.isEmpty());
			log.info("deleteSpecificationTest successful!");
		} catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("deleteSpecificationTest failed!");
		}
	}
}
