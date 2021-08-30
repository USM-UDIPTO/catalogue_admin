package com.dxc.eproc.catalogue.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.catalogue.dto.request.CatalogueDTO;
import com.dxc.eproc.catalogue.dto.request.CatalogueItemDTO;
import com.dxc.eproc.catalogue.dto.request.CategoryDTO;
import com.dxc.eproc.catalogue.dto.request.CategoryDetailsDTO;
import com.dxc.eproc.catalogue.dto.request.UnspscDetailsDTO;
import com.dxc.eproc.utils.RestUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

// TODO: Auto-generated Javadoc
/**
 * The Class CategoryControllerTestIT.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CategoryControllerTestIT extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private final static Logger log = LoggerFactory.getLogger(CategoryControllerTestIT.class);

	/** The CatalogueDTO info. */
	private static CatalogueDTO catalogueDTO;

	/** The CategoryDTO info. */
	private static CategoryDTO categoryDTO;

	/** The v. */
	private static int v = 0;

	/** The object mapper. */
	@Autowired
	private ObjectMapper objectMapper;

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
	 * Gets the catalogue.
	 *
	 * @return the catalogue
	 */
	public CatalogueDTO getCatalogue() {
		catalogueDTO = new CatalogueDTO();
		catalogueDTO.setCatalogueCode("testCoder" + v++);
		catalogueDTO.setCatalogueName("testNamer" + v);
		catalogueDTO.setDeptId(1);
		catalogueDTO.setActiveYn(true);
		catalogueDTO.setDeptName("testDept");
		return catalogueDTO;
	}

	/**
	 * Creates the catalogue item DTO.
	 *
	 * @return the catalogue item DTO
	 */
	private CatalogueItemDTO createCatalogueItemDTO() {
		CatalogueItemDTO catalogueItemDTO = new CatalogueItemDTO();
		catalogueItemDTO.setId(99L);
		catalogueItemDTO.setCatalogueCode("test");
		catalogueItemDTO.setCatalogueName("KPWD_CAT");
		catalogueItemDTO.setItemCode("item019" + v++);
		catalogueItemDTO.setItemName("ItemName9" + v);
		catalogueItemDTO.setUomId(1L);
		catalogueItemDTO.setUomName("test");
		catalogueItemDTO.setDeptId(1);
		catalogueItemDTO.setDeptName("KPWD");
		Map<String, String> specifications = new HashMap<>();
		specifications.put("Size", "10");
		catalogueItemDTO.setSpecifications(specifications);
		catalogueItemDTO.setCategories(new ArrayList<>());
		List<UnspscDetailsDTO> unspscDetailsList = new ArrayList<>();
		unspscDetailsList.add(new UnspscDetailsDTO("10000000", "test_segment", "segment"));
		unspscDetailsList.add(new UnspscDetailsDTO("10100000", "test_family", "family"));
		unspscDetailsList.add(new UnspscDetailsDTO("10101000", "test_class", "class"));
		unspscDetailsList.add(new UnspscDetailsDTO("10101010", "test_commodity", "commodity"));
		catalogueItemDTO.setUnspscDetails(unspscDetailsList);
		return catalogueItemDTO;
	}

	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	@BeforeMethod
	public CategoryDTO getCategory() {
		categoryDTO = new CategoryDTO();
		categoryDTO.setCategoryCode("testCode" + v++);
		categoryDTO.setCategoryName("testName" + v);
		categoryDTO.setDeptId(1);
		categoryDTO.setActiveYn(true);
		categoryDTO.setDeptName("testDept");
		return categoryDTO;
	}

	/**
	 * Save catalogue test.
	 *
	 * @throws Exception the exception
	 */
	@BeforeMethod
	public void saveCatalogueTest() throws Exception {
		catalogueDTO = getCatalogue();
		String uri = "/v1/api/catalogue";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		catalogueDTO = objectMapper.readValue(content, CatalogueDTO.class);
		Assert.assertFalse(Optional.ofNullable(catalogueDTO).isEmpty());
		log.info("saveCatalogueTest successful!");
	}

	/**
	 * Save category success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void saveCategorySuccessTest() throws Exception {
		String uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/category";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		CategoryDTO savedCategory = objectMapper.readValue(content, CategoryDTO.class);
		Assert.assertFalse(Optional.ofNullable(savedCategory).isEmpty());
		log.info("saveCategorySuccessTest successful!");
	}

	/**
	 * Save category null id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void saveCategoryNullIdTest() throws Exception {
		categoryDTO.setId(1L);
		String uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/category";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errorKey").value("nullId"));
		log.info("saveCatalogueNullIdTest successful");
	}

	/**
	 * Save categoryinvalid id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void saveCategoryinvalidIdTest() throws Exception {
		String uri = "/v1/api/catalogue/10001/category";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("saveCategoryinvalidIdTest successful");
	}

	/**
	 * Save category code exists test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void saveCategoryCodeExistsTest() throws Exception {
		String uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/category";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("saveCategoryCodeExistsTest successful");
	}

	/**
	 * Save category name exists test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void saveCategoryNameExistsTest() throws Exception {
		String uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/category";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		CategoryDTO categoryDTO = objectMapper.readValue(content, CategoryDTO.class);
		categoryDTO.setId(null);
		categoryDTO.setCategoryCode("NewCode1");
		req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("saveCategoryNameExistsTest successful");
	}

	/**
	 * Update category success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateCategorySuccessTest() throws Exception {
		String uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/category";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		CategoryDTO categoryDTO = objectMapper.readValue(content, CategoryDTO.class);

		uri += "/" + categoryDTO.getId();
		req = MockMvcRequestBuilders.put(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		result = mvc.perform(req).andReturn();
		content = result.getResponse().getContentAsString();
		categoryDTO = objectMapper.readValue(content, CategoryDTO.class);
		Assert.assertFalse(Optional.ofNullable(categoryDTO).isEmpty());
		log.info("updateCategorySuccessTest successful");
	}

	/**
	 * Update category invalid category id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateCategoryInvalidCategoryIdTest() throws Exception {
		String uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/category/10002";
		RequestBuilder req = MockMvcRequestBuilders.put(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("updateCategoryInvalidCategoryIdTest successful");
	}

	/**
	 * Update category invalid catalogue id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateCategoryInvalidCatalogueIdTest() throws Exception {
		String uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/category";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		CategoryDTO categoryDTO = objectMapper.readValue(content, CategoryDTO.class);

		uri = "/v1/api/catalogue/10003/category/" + categoryDTO.getId();
		req = MockMvcRequestBuilders.put(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("updateCategoryInvalidCatalogueIdTest successful");
	}

	/**
	 * Update category item linked test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateCategoryItemLinkedTest() throws Exception {
		CatalogueDTO catalogueDTO = getCatalogue();
		catalogueDTO.setCatalogueCode("testCode90009");
		catalogueDTO.setCatalogueName("cataName");
		String uri = "/v1/api/catalogue";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		catalogueDTO = objectMapper.readValue(content, CatalogueDTO.class);
		log.info(content);

		uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/category";
		categoryDTO.setCategoryCode("cat_code1_t");
		req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		result = mvc.perform(req).andReturn();
		content = result.getResponse().getContentAsString();
		log.info(content);
		CategoryDTO categoryDTO = objectMapper.readValue(content, CategoryDTO.class);

		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setId(null);
		catalogueItemDTO.setCatalogueCode("testCode90009");
		List<CategoryDetailsDTO> catDetailsList = new ArrayList<CategoryDetailsDTO>();
		catDetailsList.add(new CategoryDetailsDTO("cat_code1_t", "string"));
		catalogueItemDTO.setCategories(catDetailsList);
		mvc.perform(MockMvcRequestBuilders.post("/v1/api/catalogue/{catalogueCode}/catalogue-item", "testCode90009")
				.contentType(MediaType.APPLICATION_JSON).content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

		uri += "/" + categoryDTO.getId();
		req = MockMvcRequestBuilders.put(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("updateCategoryItemLinkedTest successful");
	}

	/**
	 * Update category item linked test success.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateCategoryItemLinkedTestSuccess() throws Exception {
		CatalogueDTO catalogueDTO = getCatalogue();
		catalogueDTO.setCatalogueCode("testCode900091");
		catalogueDTO.setCatalogueName("cataName1");
		String uri = "/v1/api/catalogue";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setId(null);
		catalogueItemDTO.setCatalogueCode("testCode900091");
		mvc.perform(MockMvcRequestBuilders.post("/v1/api/catalogue/{catalogueCode}/catalogue-item", "testCode900091")
				.contentType(MediaType.APPLICATION_JSON).content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		catalogueDTO = objectMapper.readValue(content, CatalogueDTO.class);

		uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/category";
		categoryDTO.setCategoryCode("cat_code88");
		req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		result = mvc.perform(req).andReturn();
		content = result.getResponse().getContentAsString();
		log.info(content);
		CategoryDTO categoryDTO = objectMapper.readValue(content, CategoryDTO.class);

		uri += "/" + categoryDTO.getId();
		req = MockMvcRequestBuilders.put(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("updateCategoryItemLinkedTestSuccess successful");
	}

	/**
	 * Update category name exists test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateCategoryNameExistsTest() throws Exception {
		String uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/category";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		CategoryDTO categoryDTO = objectMapper.readValue(content, CategoryDTO.class);
		Long id = categoryDTO.getId();
		categoryDTO.setId(null);
		categoryDTO.setCategoryCode("testName1001");
		categoryDTO.setCategoryName("testName1001");
		req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req);
		uri += "/" + id;
		categoryDTO.setCategoryCode("newCode1001");
		req = MockMvcRequestBuilders.put(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("updateCategoryNameExistsTest successful");
	}

	/**
	 * Update category code exists test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateCategoryCodeExistsTest() throws Exception {
		String uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/category";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		CategoryDTO categoryDTO = objectMapper.readValue(content, CategoryDTO.class);
		Long id = categoryDTO.getId();
		categoryDTO.setId(null);
		categoryDTO.setCategoryCode("testCode1002");
		categoryDTO.setCategoryName("testName1002");
		req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req);
		uri += "/" + id;
		req = MockMvcRequestBuilders.put(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("updateCategoryCodeExistsTest successful");
	}

	/**
	 * Gets the categories for catalogue success test.
	 *
	 * @return the categories for catalogue success test
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void getCategoriesForCatalogueSuccessTest() throws Exception {
		String uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/category";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);

		uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/categories";
		req = MockMvcRequestBuilders.get(uri);
		result = mvc.perform(req).andReturn();
		content = result.getResponse().getContentAsString();
		TypeReference<?> valueTypeRef = new TypeReference<List<CategoryDTO>>() {
		};
		List<CategoryDTO> categoryDTOList = (List<CategoryDTO>) objectMapper.readValue(content, valueTypeRef);
		Assert.assertFalse(categoryDTOList.isEmpty());
		log.info("getCategoriesForCatalogueSuccessTest successful");
	}

	/**
	 * Gets the categories for catalogue success test.
	 *
	 * @return the categories for catalogue success test
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void getCategoriesForCatalogue_activeYn_SuccessTest() throws Exception {

		String uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/category";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/categories?active=true";
		req = MockMvcRequestBuilders.get(uri);
		result = mvc.perform(req).andReturn();
		content = result.getResponse().getContentAsString();
		TypeReference<?> valueTypeRef = new TypeReference<List<CategoryDTO>>() {
		};
		List<CategoryDTO> categoryDTOList = (List<CategoryDTO>) objectMapper.readValue(content, valueTypeRef);
		Assert.assertFalse(categoryDTOList.isEmpty());
		log.info("getCategoriesForCatalogueSuccessTest successful");

	}

	/**
	 * Gets the categories for catalogue invalid catalogue id test.
	 *
	 * @return the categories for catalogue invalid catalogue id test
	 * @throws Exception the exception
	 */
	@Test
	public void getCategoriesForCatalogueInvalidCatalogueIdTest() throws Exception {
		String uri = "/v1/api/catalogue/10004/categories";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getCategoriesForCatalogueInvalidCatalogueIdTest successful");
	}

	/**
	 * Gets the categories for catalogue no categories found test.
	 *
	 * @return the categories for catalogue no categories found test
	 * @throws Exception the exception
	 */
	@Test
	public void getCategoriesForCatalogueNoCategoriesFoundTest() throws Exception {
		CatalogueDTO catalogueDTO = getCatalogue();
		catalogueDTO.setCatalogueCode("testCode900099");
		catalogueDTO.setCatalogueName("cataName19");
		String uri = "/v1/api/catalogue";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		catalogueDTO = objectMapper.readValue(content, CatalogueDTO.class);
		uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/categories";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getCategoriesForCatalogueNoCategoriesFoundTest successful");
	}

	/**
	 * Gets the categories by catalogue code success test.
	 *
	 * @return the categories by catalogue code success test
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void getCategoriesByCatalogueCodeSuccessTest() throws Exception {
		String uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/category";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);

		uri = "/v1/api/catalogue/code/" + catalogueDTO.getCatalogueCode() + "/categories";
		req = MockMvcRequestBuilders.get(uri);
		result = mvc.perform(req).andReturn();
		content = result.getResponse().getContentAsString();
		TypeReference<?> valueTypeRef = new TypeReference<List<CategoryDTO>>() {
		};
		List<CategoryDTO> categoryDTOList = (List<CategoryDTO>) objectMapper.readValue(content, valueTypeRef);
		Assert.assertFalse(categoryDTOList.isEmpty());
		log.info("getCategoriesByCatalogueCodeSuccessTest successful");
	}

	/**
	 * Gets the categories by catalogue code catalogue not found test.
	 *
	 * @return the categories by catalogue code catalogue not found test
	 * @throws Exception the exception
	 */
	@Test
	public void getCategoriesByCatalogueCodeCatalogueNotFoundTest() throws Exception {
		String uri = "/v1/api/catalogue/code/tt/categories";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getCategoriesByCatalogueCodeCatalogueNotFoundTest successful");
	}

	/**
	 * Gets the categories by catalogue code no categories found test.
	 *
	 * @return the categories by catalogue code no categories found test
	 * @throws Exception the exception
	 */
	@Test
	public void getCategoriesByCatalogueCodeNoCategoriesFoundTest() throws Exception {
		CatalogueDTO catalogueDTO = getCatalogue();
		catalogueDTO.setCatalogueCode("testCode900899");
		catalogueDTO.setCatalogueName("cataName87");
		String uri = "/v1/api/catalogue";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		catalogueDTO = objectMapper.readValue(content, CatalogueDTO.class);
		uri = "/v1/api/catalogue/code/" + catalogueDTO.getCatalogueCode() + "/categories";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getCategoriesByCatalogueCodeNoCategoriesFoundTest successful");
	}

	/**
	 * Search categories success test.
	 *
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void searchCategoriesSuccessTest() throws Exception {
		String uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/category";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);

		String categorySearchDTO = "{" + " \"categoryCode\": \"te\"," + " \"categoryName\": \"te\"," + " \"deptId\": 1"
				+ " } ";
		uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/category/search";
		req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON).content(categorySearchDTO)
				.contentType(MediaType.APPLICATION_JSON);
		result = mvc.perform(req).andReturn();
		content = result.getResponse().getContentAsString();
		TypeReference<?> valueTypeRef = new TypeReference<List<CategoryDTO>>() {
		};
		List<CategoryDTO> categoryDTOList = (List<CategoryDTO>) objectMapper.readValue(content, valueTypeRef);
		Assert.assertFalse(categoryDTOList.isEmpty());
		log.info("searchCategoriesSuccessTest successful");
	}

	/**
	 * Search categories invalid catalogue id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void searchCategoriesInvalidCatalogueIdTest() throws Exception {
		String categorySearchDTO = "{" + " \"categoryCode\": \"z\"," + " \"categoryName\": \"z\"," + " \"deptId\": 2"
				+ " } ";
		String uri = "/v1/api/catalogue/989/category/search";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(categorySearchDTO).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("searchCategoriesInvalidCatalogueIdTest successful");
	}

	/**
	 * Search categories no values test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void searchCategoriesNoValuesTest() throws Exception {
		String categorySearchDTO = "{" + " \"categoryCode\": \"\"," + " \"categoryName\": \"\"," + " \"deptId\": 1"
				+ " } ";
		String uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/category/search";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(categorySearchDTO).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("searchCategoriesNoValuesTest successful");
	}

	/**
	 * Search categories no records test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void searchCategoriesNoRecordsTest() throws Exception {
		String categorySearchDTO = "{" + " \"categoryCode\": \"z\"," + " \"categoryName\": \"z\"," + " \"deptId\": 3"
				+ " } ";
		String uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/category/search";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(categorySearchDTO).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("searchCategoriesNoRecordsTest successful");
	}

	/**
	 * Gets the category success test.
	 *
	 * @return the category success test
	 * @throws Exception the exception
	 */
	@Test
	public void getCategorySuccessTest() throws Exception {
		String uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/category";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		CategoryDTO categoryDTO = objectMapper.readValue(content, CategoryDTO.class);

		uri += "/" + categoryDTO.getId();
		req = MockMvcRequestBuilders.get(uri);
		result = mvc.perform(req).andReturn();
		content = result.getResponse().getContentAsString();
		categoryDTO = objectMapper.readValue(content, CategoryDTO.class);
		Assert.assertFalse(Optional.ofNullable(categoryDTO).isEmpty());
		log.info("getCategorySuccessTest successful");
	}

	/**
	 * Gets the category invalid catalogue id test.
	 *
	 * @return the category invalid catalogue id test
	 * @throws Exception the exception
	 */
	@Test
	public void getCategoryInvalidCatalogueIdTest() throws Exception {
		String uri = "/v1/api/catalogue/10005/category/10006";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getCategoryInvalidCatalogueIdTest successful");
	}

	/**
	 * Gets the category no category found test.
	 *
	 * @return the category no category found test
	 * @throws Exception the exception
	 */
	@Test
	public void getCategoryNoCategoryFoundTest() throws Exception {
		String uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/category/10007";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getCategoryNoCategoryFoundTest successful");
	}

	/**
	 * Sets the category active yn success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void setCategoryActiveYnSuccessTest() throws Exception {
		String uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/category";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		CategoryDTO categoryDTO = objectMapper.readValue(content, CategoryDTO.class);

		uri += "/" + categoryDTO.getId() + "/activeYn/1";
		mvc.perform(MockMvcRequestBuilders.put(uri)).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("setCategoryActiveYnSuccessTest successful");
	}

	/**
	 * Sets the category active yn invalid catalogue id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void setCategoryActiveYnInvalidCatalogueIdTest() throws Exception {
		String uri = "/v1/api/catalogue/-1/category/-1/activeYn/1";
		mvc.perform(MockMvcRequestBuilders.put(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("setCategoryActiveYnInvalidCatalogueIdTest successful");
	}

	/**
	 * Sets the category active yn invalid category id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void setCategoryActiveYnInvalidCategoryIdTest() throws Exception {
		String uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/category/-1/activeYn/1";
		mvc.perform(MockMvcRequestBuilders.put(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("setCategoryActiveYnInvalidCategoryIdTest successful");
	}

	/**
	 * Delete category success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteCategorySuccessTest() throws Exception {
		String uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/category";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		CategoryDTO categoryDTO = objectMapper.readValue(content, CategoryDTO.class);

		uri += "/" + categoryDTO.getId();
		mvc.perform(MockMvcRequestBuilders.delete(uri)).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("deleteCategorySuccessTest successful");
	}

	/**
	 * Delete category invalid category id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteCategoryInvalidCategoryIdTest() throws Exception {
		String uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/category/9999";
		mvc.perform(MockMvcRequestBuilders.delete(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("deleteCategoryInvalidCategoryIdTest successful");
	}

	/**
	 * Delete category invalid catalogue id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteCategoryInvalidCatalogueIdTest() throws Exception {
		String uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/category";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		CategoryDTO categoryDTO = objectMapper.readValue(content, CategoryDTO.class);
		uri = "/v1/api/catalogue/9999/category/" + categoryDTO.getId();
		mvc.perform(MockMvcRequestBuilders.delete(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("deleteCategoryInvalidCatalogueIdTest successful");
	}

	/**
	 * Delete category item linked test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteCategoryItemLinkedTest() throws Exception {
		CatalogueDTO catalogueDTO = getCatalogue();
		catalogueDTO.setCatalogueCode("testCode900009");
		catalogueDTO.setCatalogueName("cataName9");
		String uri = "/v1/api/catalogue";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		catalogueDTO = objectMapper.readValue(content, CatalogueDTO.class);

		uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/category";
		categoryDTO.setCategoryCode("cat_code0t_");
		req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		result = mvc.perform(req).andReturn();
		content = result.getResponse().getContentAsString();
		log.info(content);
		CategoryDTO categoryDTO = objectMapper.readValue(content, CategoryDTO.class);

		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setId(null);
		catalogueItemDTO.setCatalogueCode("testCode900009");
		List<CategoryDetailsDTO> catDetailsList = new ArrayList<CategoryDetailsDTO>();
		catDetailsList.add(new CategoryDetailsDTO("cat_code0t_", "string"));
		catalogueItemDTO.setCategories(catDetailsList);
		mvc.perform(MockMvcRequestBuilders.post("/v1/api/catalogue/{catalogueCode}/catalogue-item", "testCode900009")
				.contentType(MediaType.APPLICATION_JSON).content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

		uri += "/" + categoryDTO.getId();
		mvc.perform(MockMvcRequestBuilders.delete(uri)).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("deleteCategoryItemLinkedTest successful");
	}
}
