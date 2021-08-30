/**
 * 
 */
package com.dxc.eproc.catalogue.repository;

import java.util.Date;
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

import com.dxc.eproc.catalogue.model.Catalogue;

// TODO: Auto-generated Javadoc
/**
 * The CatalogueRepositoryTest.
 */
@SpringBootTest
@ActiveProfiles("test")
public class CatalogueRepositoryTest extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(CatalogueRepositoryTest.class);

	/** The catalogue repository. */
	@Autowired
	CatalogueRepository catalogueRepository;

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
	 * Gets the catalogue.
	 *
	 * @return the catalogue
	 */
	public Catalogue getCatalogue() {
		Catalogue catalogue = new Catalogue();
		catalogue.setCatalogueCode("testCode");
		catalogue.setCatalogueName("testName");
		catalogue.setDeptId(1);
		catalogue.setDeptName("testDept");
		catalogue.setCreatedBy("test");
		catalogue.setCreatedTs(new Date());
		return catalogue;
	}

	/**
	 * Save catalogue test.
	 */
	@Test
	public void saveCatalogueTest() {
		try {
			Catalogue catalogue = getCatalogue();
			catalogue = catalogueRepository.save(catalogue);
			System.out.println(catalogue.toString());
			Assert.assertNotNull(catalogue.getId());
			log.info("saveCatalogueTest successful!");
		} catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("saveCatalogueTest failed!");
		}
	}

	/**
	 * Update catalogue test.
	 */
	@Test
	public void updateCatalogueTest() {
		try {
			Catalogue catalogue = getCatalogue();
			catalogue = catalogueRepository.save(catalogue);
			String beforeCode = catalogue.getCatalogueCode();
			catalogue = catalogueRepository.findById(catalogue.getId()).get();
			catalogue.setCatalogueCode("updatedCode");
			catalogue = catalogueRepository.save(catalogue);
			String afterCode = catalogue.getCatalogueCode();
			Assert.assertNotEquals(beforeCode, afterCode);
			System.out.println(catalogue.toString());
			log.info("updateCatalogueTest successful!");
		} catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("updateCatalogueTest failed!");
		}
	}

	/**
	 * Gets the all catalogues test.
	 *
	 * @return the all catalogues test
	 */
	@Test
	public void getAllCataloguesTest() {
		try {
			Catalogue catalogue = getCatalogue();
			catalogue = catalogueRepository.save(catalogue);
			List<Catalogue> catalogues = catalogueRepository.findAll();
			System.out.println(catalogues.toString());
			Assert.assertNotNull(catalogues);
			log.info("getAllCataloguesTest successful!");
		} catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("getAllCataloguesTest failed!");
		}
	}

	/**
	 * Gets the catalogue by id test.
	 *
	 * @return the catalogue by id test
	 */
	@Test
	public void getCatalogueByIdTest() {
		try {
			Catalogue catalogue = getCatalogue();
			catalogue = catalogueRepository.save(catalogue);
			Catalogue foundCatalogue = catalogueRepository.findById(catalogue.getId()).get();
			System.out.println(foundCatalogue.toString());
			Assert.assertEquals(catalogue.getCatalogueCode(), foundCatalogue.getCatalogueCode());
			log.info("getCatalogueByIdTest successful!");
		} catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("getCatalogueByIdTest failed!");
		}
	}

	/**
	 * Delete catalogue test.
	 */
	@Test
	public void deleteCatalogueTest() {
		try {
			Catalogue catalogue = getCatalogue();
			catalogue = catalogueRepository.save(catalogue);
			catalogueRepository.deleteAll();
			List<Catalogue> catalogues = catalogueRepository.findAll();
			Assert.assertTrue(catalogues.isEmpty());
			log.info("deleteCatalogueTest successful!");
		} catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("deleteCatalogueTest failed!");
		}
	}
}
