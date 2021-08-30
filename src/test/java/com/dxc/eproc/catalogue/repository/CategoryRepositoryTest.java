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
import com.dxc.eproc.catalogue.model.Category;

// TODO: Auto-generated Javadoc
/**
 * The CatalogueRepositoryTest.
 */
@SpringBootTest
@ActiveProfiles("test")
public class CategoryRepositoryTest extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(CategoryRepositoryTest.class);

	/** The catalogue info. */
	private static Catalogue catalogue;

	/** The Category info. */
	private static Category category;

	/** The catalogue repository. */
	@Autowired
	CatalogueRepository catalogueRepository;

	/** The category repository. */
	@Autowired
	CategoryRepository categoryRepository;

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
		catalogue.setCatalogueCode("testCatalogueCode");
		catalogue.setCatalogueName("testCatalogueName");
		catalogue.setDeptId(1);
		catalogue.setDeptName("testDept");
		catalogue.setCreatedBy("test");
		catalogue.setCreatedTs(new Date());
		return catalogue;
	}

	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	public Category getCategory() {
		Category category = new Category();
		category.setCategoryCode("testCategoryCode");
		category.setCategoryName("testCategoryName");
		category.setDeptId(1);
		category.setDeptName("testDept");
		category.setCreatedBy("test");
		category.setCreatedTs(new Date());
		return category;
	}

	/**
	 * Save catalogue test.
	 */
	@Test
	public void saveCatalogueTest() {
		try {
			catalogue = getCatalogue();
			catalogue = catalogueRepository.save(catalogue);
			System.out.println(catalogue.toString());
			log.info("saveCatalogueTest successful!");
		} catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("saveCatalogueTest failed!");
		}
	}

	/**
	 * Save category test.
	 */
	@Test(dependsOnMethods = "saveCatalogueTest")
	public void saveCategoryTest() {
		try {
			category = getCategory();
			category.setCatalogue(catalogue);
			category = categoryRepository.save(category);
			System.out.println(category.toString());
			Assert.assertNotNull(category.getId());
			log.info("saveCategoryTest successful!");
		} catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("saveCategoryTest failed!");
		}
	}

	/**
	 * Update category test.
	 */
	@Test(dependsOnMethods = "saveCategoryTest")
	public void updateCategoryTest() {
		try {
			String beforeCode = category.getCategoryCode();
			category = categoryRepository.findById(category.getId()).get();
			category.setCategoryCode("updatedCode");
			category = categoryRepository.save(category);
			String afterCode = category.getCategoryCode();
			Assert.assertNotEquals(beforeCode, afterCode);
			System.out.println(category.toString());
			log.info("updateCategoryTest successful!");
		} catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("updateCategoryTest failed!");
		}
	}

	/**
	 * Gets the categories for catalogue test.
	 *
	 * @return the categories for catalogue test
	 */
	@Test(dependsOnMethods = "saveCategoryTest")
	public void getCategoriesForCatalogueTest() {
		try {
			catalogue = catalogueRepository.findById(catalogue.getId()).get();
			List<Category> categories = catalogue.getCategories();
			System.out.println(categories.toString());
			Assert.assertFalse(categories.isEmpty());
			log.info("getCategoriesForCatalogueTest successful!");
		} catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("getCategoriesForCatalogueTest failed!");
		}
	}

	/**
	 * Gets the category test.
	 *
	 * @return the category test
	 */
	@Test(dependsOnMethods = "saveCategoryTest")
	public void getCategoryTest() {
		try {
			Category foundCategory = categoryRepository.findById(category.getId()).get();
			System.out.println(foundCategory.toString());
			Assert.assertEquals(category.getCategoryCode(), foundCategory.getCategoryCode());
			log.info("getCategoryTest successful!");
		} catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("getCategoryTest failed!");
		}
	}
}
