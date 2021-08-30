package com.dxc.eproc.catalogue.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.testng.PowerMockTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dxc.eproc.catalogue.dto.request.CategoryDTO;
import com.dxc.eproc.catalogue.mapper.CategoryMapper;
import com.dxc.eproc.catalogue.model.Catalogue;
import com.dxc.eproc.catalogue.model.Category;
import com.dxc.eproc.catalogue.repository.CatalogueRepository;
import com.dxc.eproc.catalogue.repository.CategoryRepository;
import com.dxc.eproc.catalogue.services.impl.CategoryServiceImpl;
import com.querydsl.core.BooleanBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Class CategoryServiceTest.
 */
public class CategoryServiceTest extends PowerMockTestCase {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(CategoryServiceTest.class);

	/** The Catalogue info. */
	private static Catalogue catalogue;

	/** The Category info. */
	private static Category category;

	/** The CategoryDTO info. */
	private static CategoryDTO categoryDTO;

	/** The category service. */
	@InjectMocks
	private CategoryServiceImpl categoryService;

	/** The category mapper. */
	@Mock
	private CategoryMapper categoryMapper;

	/** The catalogue repository. */
	@Mock
	private CatalogueRepository catalogueRepository;

	/** The category repository. */
	@Mock
	private CategoryRepository categoryRepository;

	/**
	 * Gets the entity.
	 *
	 * @return the entity
	 */
	@BeforeClass
	public void getEntity() {
		catalogue = new Catalogue();
		catalogue.setId(1L);
		catalogue.setCatalogueCode("testCode");
		catalogue.setCatalogueName("testName");
		catalogue.setDeptId(1);
		catalogue.setDeptName("testDept");
		catalogue.setCategories(new ArrayList<Category>());

		category = new Category();
		category.setId(1L);
		category.setCategoryCode("testCode");
		category.setCategoryName("testName");
		category.setDeptId(1);
		category.setActiveYn(true);
		category.setDeptName("testDept");
		category.setCatalogue(catalogue);

		categoryDTO = new CategoryDTO();
		BeanUtils.copyProperties(category, categoryDTO);
	}

	/**
	 * Save or update category test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void saveOrUpdateCategoryTest() throws Exception {
		PowerMockito.when(categoryRepository.save(category)).thenReturn(category);
		PowerMockito.when(categoryMapper.toDto(category)).thenReturn(categoryDTO);

		CategoryDTO savedCategory = categoryService.saveOrUpdateCategory(category);
		Assert.assertFalse(Optional.ofNullable(savedCategory).isEmpty());
		log.info("saveCategoryTest successful!");
	}

	/**
	 * Gets the categories for catalogue test.
	 *
	 * @return the categories for catalogue test
	 * @throws Exception the exception
	 */
	@Test
	public void getCategoriesForCatalogueTest() throws Exception {
		List<Category> categoryList = new ArrayList<>();
		categoryList.add(category);
		Page<Category> page = new PageImpl<>(categoryList);
		catalogue.setCategories(categoryList);
		PageRequest pageRequest = PageRequest.of(0, 5);

		CategoryDTO categoryDTO = new CategoryDTO();
		BeanUtils.copyProperties(category, categoryDTO);
		List<CategoryDTO> categoryDTOList = new ArrayList<>();
		categoryDTOList.add(categoryDTO);

		PowerMockito.when(categoryRepository.findByCatalogueOrderByLastModifiedTsDesc(pageRequest, catalogue)).thenReturn(page);
		PowerMockito.when(categoryMapper.toDto(categoryList)).thenReturn(categoryDTOList);

		Page<CategoryDTO> categories = categoryService.getCategoriesForCatalogue(pageRequest, catalogue, null);
		Assert.assertTrue(categories.hasContent());
		log.info("getCategoriesForCatalogueTest successful!");
	}

	/**
	 * Gets the categories for catalogue test with activeYn.
	 *
	 * @return the categories for catalogue test
	 * @throws Exception the exception
	 */
	@Test
	public void getCategoriesForCatalogueActiveYnTest() throws Exception {
		List<Category> categoryList = new ArrayList<>();
		categoryList.add(category);
		Page<Category> page = new PageImpl<>(categoryList);
		catalogue.setCategories(categoryList);
		PageRequest pageRequest = PageRequest.of(0, 5);

		CategoryDTO categoryDTO = new CategoryDTO();
		BeanUtils.copyProperties(category, categoryDTO);
		List<CategoryDTO> categoryDTOList = new ArrayList<>();
		categoryDTOList.add(categoryDTO);

		PowerMockito.when(categoryRepository.findByCatalogueAndActiveYnOrderByLastModifiedTsDesc(pageRequest, catalogue, true))
				.thenReturn(page);
		PowerMockito.when(categoryMapper.toDto(categoryList)).thenReturn(categoryDTOList);

		Page<CategoryDTO> categories = categoryService.getCategoriesForCatalogue(pageRequest, catalogue, true);
		Assert.assertTrue(categories.hasContent());
		log.info("getCategoriesForCatalogueActiveYnTest successful!");
	}

	/**
	 * Search category test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void searchCategoryTest() throws Exception {
		List<Category> categoryList = new ArrayList<>();
		categoryList.add(category);
		Page<Category> page = new PageImpl<>(categoryList);
		PageRequest pageRequest = PageRequest.of(0, 5);
		BooleanBuilder builder = null;
		PowerMockito.when(categoryRepository.findAll(builder, pageRequest)).thenReturn(page);
		PowerMockito.when(categoryMapper.toDto(category)).thenReturn(categoryDTO);
		PowerMockito.when(catalogueRepository.findByDeptIdAndCatalogueCode(Mockito.anyInt(), Mockito.anyString()))
				.thenReturn(catalogue);
		Page<CategoryDTO> categoryDTOPage = categoryService.searchCategories(builder, pageRequest);
		Assert.assertTrue(categoryDTOPage.hasContent());
		log.info("searchCategoryTest successful!");
	}

	/**
	 * Gets the category test.
	 *
	 * @return the category test
	 * @throws Exception the exception
	 */
	@Test
	public void getCategoryTest() throws Exception {
		PowerMockito.when(categoryRepository.findByCatalogueAndId(catalogue, category.getId())).thenReturn(category);
		PowerMockito.when(categoryMapper.toDto(category)).thenReturn(categoryDTO);

		CategoryDTO fetchedCategory = categoryService.getCategory(catalogue, category.getId());
		Assert.assertFalse(Optional.ofNullable(fetchedCategory).isEmpty());
		log.info("getCategoryTest successful!");
	}

	/**
	 * Category name code check for save success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void categoryNameCodeCheckForSaveSuccessTest() throws Exception {
		PowerMockito.when(
				categoryRepository.findByDeptIdAndCategoryCode(categoryDTO.getDeptId(), categoryDTO.getCategoryCode()))
				.thenReturn(null);
		Assert.assertFalse(categoryService.categoryNameCodeCheck(categoryDTO, "code"));

		PowerMockito.when(
				categoryRepository.findByDeptIdAndCategoryName(categoryDTO.getDeptId(), categoryDTO.getCategoryName()))
				.thenReturn(null);
		Assert.assertFalse(categoryService.categoryNameCodeCheck(categoryDTO, "name"));
	}

	/**
	 * Category name code check for save fail test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void categoryNameCodeCheckForSaveFailTest() throws Exception {
		categoryDTO.setId(null);

		PowerMockito.when(
				categoryRepository.findByDeptIdAndCategoryCode(categoryDTO.getDeptId(), categoryDTO.getCategoryCode()))
				.thenReturn(category);
		Assert.assertTrue(categoryService.categoryNameCodeCheck(categoryDTO, "code"));

		PowerMockito.when(
				categoryRepository.findByDeptIdAndCategoryName(categoryDTO.getDeptId(), categoryDTO.getCategoryName()))
				.thenReturn(category);
		Assert.assertTrue(categoryService.categoryNameCodeCheck(categoryDTO, "name"));
	}

	/**
	 * Category name code check for update fail test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void categoryNameCodeCheckForUpdateFailTest() throws Exception {
		categoryDTO.setId(2L);

		PowerMockito.when(
				categoryRepository.findByDeptIdAndCategoryCode(categoryDTO.getDeptId(), categoryDTO.getCategoryCode()))
				.thenReturn(category);
		Assert.assertTrue(categoryService.categoryNameCodeCheck(categoryDTO, "code"));

		PowerMockito.when(
				categoryRepository.findByDeptIdAndCategoryName(categoryDTO.getDeptId(), categoryDTO.getCategoryName()))
				.thenReturn(category);
		Assert.assertTrue(categoryService.categoryNameCodeCheck(categoryDTO, "name"));
	}

	/**
	 * Delete category test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteCategoryTest() throws Exception {
		categoryService.deleteCategory(1L);
	}
}
