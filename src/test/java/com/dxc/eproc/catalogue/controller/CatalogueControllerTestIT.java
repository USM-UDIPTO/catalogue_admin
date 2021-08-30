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
import org.springframework.test.context.ContextConfiguration;
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

import com.dxc.eproc.catalogue.config.WireMockConfig;
import com.dxc.eproc.catalogue.dto.request.CatalogueDTO;
import com.dxc.eproc.catalogue.dto.request.CatalogueItemDTO;
import com.dxc.eproc.catalogue.dto.request.UnspscDetailsDTO;
import com.dxc.eproc.catalogue.mocks.MasterServiceMock;
import com.dxc.eproc.client.master.dto.UomDTO;
import com.dxc.eproc.utils.RestUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;

// TODO: Auto-generated Javadoc
/**
 * The Class CatalogueControllerTestIT.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ContextConfiguration(classes = { WireMockConfig.class })
public class CatalogueControllerTestIT extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private final static Logger log = LoggerFactory.getLogger(CatalogueControllerTestIT.class);

	/** The CatalogueDTO info. */
	private static CatalogueDTO catalogueDTO;

	/** The v. */
	private static int v = 0;

	/** The object mapper. */
	@Autowired
	private ObjectMapper objectMapper;

	/** The mvc. */
	@Autowired
	private MockMvc mvc;

	/** The wire mock server. */
	@Autowired
	private WireMockServer wireMockServer;

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
	@BeforeMethod
	public CatalogueDTO getCatalogue() {
		catalogueDTO = new CatalogueDTO();
		catalogueDTO.setCatalogueCode("testCode" + v++);
		catalogueDTO.setCatalogueName("testName" + v);
		catalogueDTO.setDeptId(1);
		catalogueDTO.setDeptName("testDept");
		catalogueDTO.setActiveYn(true);
		return catalogueDTO;
	}

	/**
	 * Creates the catalogue item DTO.
	 *
	 * @return the catalogue item DTO
	 */
	private CatalogueItemDTO createCatalogueItemDTO() {
		CatalogueItemDTO catalogueItemDTO = new CatalogueItemDTO();
		catalogueItemDTO.setId(100L);
		catalogueItemDTO.setCatalogueCode("testCode" + v);
		catalogueItemDTO.setCatalogueName("KPWD_CAT");
		catalogueItemDTO.setItemCode("item01" + v++);
		catalogueItemDTO.setItemName("ItemName" + v);
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
	 * Save catalogue success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void saveCatalogueSuccessTest() throws Exception {
		String uri = "/v1/api/catalogue";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);

		CatalogueDTO response = objectMapper.readValue(content, CatalogueDTO.class);
		Assert.assertFalse(Optional.ofNullable(response).isEmpty());
		log.info("saveCatalogueSuccessTest successful");
	}

	/**
	 * Save catalogue null id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void saveCatalogueNullIdTest() throws Exception {
		catalogueDTO.setId(1L);
		String uri = "/v1/api/catalogue";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errorKey").value("nullId"));
		log.info("saveCatalogueNullIdTest successful");
	}

	/**
	 * Save catalogue code exists test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void saveCatalogueCodeExistsTest() throws Exception {
		String uri = "/v1/api/catalogue";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("saveCatalogueCodeExistsTest successful");
	}

	/**
	 * Save catalogue name exists test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void saveCatalogueNameExistsTest() throws Exception {
		String uri = "/v1/api/catalogue";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		CatalogueDTO catalogueDTO = objectMapper.readValue(content, CatalogueDTO.class);
		catalogueDTO.setCatalogueCode("NewCode1");
		catalogueDTO.setId(null);
		req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("saveCatalogueNameExistsTest successful");
	}

	/**
	 * Update catalogue success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateCatalogueSuccessTest() throws Exception {
		String uri = "/v1/api/catalogue";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		CatalogueDTO catalogueDTO = objectMapper.readValue(content, CatalogueDTO.class);

		uri += "/" + catalogueDTO.getId();
		req = MockMvcRequestBuilders.put(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		result = mvc.perform(req).andReturn();
		content = result.getResponse().getContentAsString();
		catalogueDTO = objectMapper.readValue(content, CatalogueDTO.class);
		Assert.assertFalse(Optional.ofNullable(catalogueDTO).isEmpty());
		log.info("updateCatalogueSuccessTest successful");
	}

	/**
	 * Update catalogue invalid id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateCatalogueInvalidIdTest() throws Exception {
		String uri = "/v1/api/catalogue/-1";
		RequestBuilder req = MockMvcRequestBuilders.put(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("updateCatalogueInvalidIdTest successful");
	}

	/**
	 * Update catalogue item linked test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateCatalogueItemLinkedTest() throws Exception {
		catalogueDTO.setCatalogueCode("testCode10010");
		String uri = "/v1/api/catalogue";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setId(null);
		catalogueItemDTO.setCatalogueCode("testCode10010");

		UomDTO uomDTO = new UomDTO();
		uomDTO.setId(1);
		uomDTO.setName("USM");
		List<UomDTO> uomDTOList = new ArrayList<>();
		uomDTOList.add(uomDTO);
		MasterServiceMock.setUpMockGetAllActiveUomsByNameUsingGET(wireMockServer, catalogueItemDTO.getUomName(),
				uomDTOList);

		mvc.perform(MockMvcRequestBuilders.post("/v1/api/catalogue/{catalogueCode}/catalogue-item", "testCode10010")
				.contentType(MediaType.APPLICATION_JSON).content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		CatalogueDTO catalogueDTO = objectMapper.readValue(content, CatalogueDTO.class);
		uri += "/" + catalogueDTO.getId();
		req = MockMvcRequestBuilders.put(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("updateCatalogueItemLinkedTest successful");
	}

	/**
	 * Update catalogue name exists test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateCatalogueNameExistsTest() throws Exception {
		String uri = "/v1/api/catalogue";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		CatalogueDTO catalogueDTO = objectMapper.readValue(content, CatalogueDTO.class);
		catalogueDTO.setCatalogueCode("testCode1001");
		catalogueDTO.setCatalogueName("testName1001");
		Long id = catalogueDTO.getId();
		catalogueDTO.setId(null);

		req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req);
		uri = "/v1/api/catalogue/" + id;
		catalogueDTO.setCatalogueCode("newCode1001");
		req = MockMvcRequestBuilders.put(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("updateCatalogueNameExistsTest successful");
	}

	/**
	 * Update catalogue code exists test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateCatalogueCodeExistsTest() throws Exception {
		String uri = "/v1/api/catalogue";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		CatalogueDTO catalogueDTO = objectMapper.readValue(content, CatalogueDTO.class);
		catalogueDTO.setCatalogueCode("testCode1002");
		catalogueDTO.setCatalogueName("testName1002");
		Long id = catalogueDTO.getId();
		catalogueDTO.setId(null);

		req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req);
		uri = "/v1/api/catalogue/" + id;
		req = MockMvcRequestBuilders.put(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("updateCatalogueCodeExistsTest successful");
	}

	/**
	 * Get all catalogues success test.
	 *
	 * @return the all catalogues success test
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dependsOnMethods = "saveCatalogueSuccessTest")
	public void getAllCataloguesSuccessTest() throws Exception {
		String uri = "/v1/api/catalogues/dept/" + catalogueDTO.getDeptId();
		RequestBuilder req = MockMvcRequestBuilders.get(uri);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		TypeReference<?> valueTypeRef = new TypeReference<List<CatalogueDTO>>() {
		};
		List<CatalogueDTO> catalogues = (List<CatalogueDTO>) objectMapper.readValue(content, valueTypeRef);
		Assert.assertFalse(catalogues.isEmpty());
		log.info("getAllCataloguesSuccessTest successful");
	}

	/**
	 * Get all active catalogues success test.
	 *
	 * @return the all active catalogues success test
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dependsOnMethods = "saveCatalogueSuccessTest")
	public void getAllActiveCataloguesSuccessTest() throws Exception {
		String uri = "/v1/api/catalogues/dept/" + catalogueDTO.getDeptId() + "?active=true";
		RequestBuilder req = MockMvcRequestBuilders.get(uri);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		TypeReference<?> valueTypeRef = new TypeReference<List<CatalogueDTO>>() {
		};
		List<CatalogueDTO> catalogues = (List<CatalogueDTO>) objectMapper.readValue(content, valueTypeRef);
		Assert.assertFalse(catalogues.isEmpty());
		log.info("getAllActiveCataloguesSuccessTest successful");
	}

	/**
	 * Gets the all catalogues no records test.
	 *
	 * @return the all catalogues no records test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllCataloguesNoRecordsTest() throws Exception {
		String uri = "/v1/api/catalogue/dept/2";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getAllCataloguesNoRecordsTest successful");
	}

	/**
	 * Search catalogues success test.
	 *
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@Test(dependsOnMethods = "saveCatalogueSuccessTest")
	public void searchCataloguesSuccessTest() throws Exception {
		String catalogueSearchDTO = "{" + " \"catalogueCode\": \"te\"," + " \"catalogueName\": \"te\","
				+ " \"deptId\": 1" + " } ";
		String uri = "/v1/api/catalogue/search";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(catalogueSearchDTO).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		TypeReference<?> valueTypeRef = new TypeReference<List<CatalogueDTO>>() {
		};
		List<CatalogueDTO> catalogues = (List<CatalogueDTO>) objectMapper.readValue(content, valueTypeRef);
		Assert.assertFalse(catalogues.isEmpty());
		log.info("searchCataloguesSuccessTest successful");
	}

	/**
	 * Search catalogues no values test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void searchCataloguesNoValuesTest() throws Exception {
		String catalogueSearchDTO = "{" + " \"catalogueCode\": \"\"," + " \"catalogueName\": \"\"," + " \"deptId\": 1"
				+ " } ";
		String uri = "/v1/api/catalogue/search";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(catalogueSearchDTO).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("searchCataloguesNoValuesTest successful");
	}

	/**
	 * Search catalogues no records test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void searchCataloguesNoRecordsTest() throws Exception {
		String catalogueSearchDTO = "{" + " \"catalogueCode\": \"z\"," + " \"catalogueName\": \"z\"," + " \"deptId\": 2"
				+ " } ";
		String uri = "/v1/api/catalogue/search";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(catalogueSearchDTO).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("searchCataloguesNoRecordsTest successful");
	}

	/**
	 * Gets the catalogue success test.
	 *
	 * @return the catalogue success test
	 * @throws Exception the exception
	 */
	@Test
	public void getCatalogueSuccessTest() throws Exception {
		String uri = "/v1/api/catalogue";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		CatalogueDTO catalogueDTO = objectMapper.readValue(content, CatalogueDTO.class);

		uri += "/" + catalogueDTO.getId();
		req = MockMvcRequestBuilders.get(uri);
		result = mvc.perform(req).andReturn();
		content = result.getResponse().getContentAsString();
		catalogueDTO = objectMapper.readValue(content, CatalogueDTO.class);
		Assert.assertFalse(Optional.ofNullable(catalogueDTO).isEmpty());
		log.info("getCatalogueSuccessTest successful");
	}

	/**
	 * Gets the catalogue invalid id test.
	 *
	 * @return the catalogue invalid id test
	 * @throws Exception the exception
	 */
	@Test
	public void getCatalogueInvalidIdTest() throws Exception {
		String uri = "/v1/api/catalogue/1003";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getCatalogueInvalidIdTest successful");
	}

	/**
	 * Delete catalogue success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteCatalogueSuccessTest() throws Exception {
		String uri = "/v1/api/catalogue";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		CatalogueDTO catalogueDTO = objectMapper.readValue(content, CatalogueDTO.class);

		uri += "/" + catalogueDTO.getId();
		req = MockMvcRequestBuilders.delete(uri);
		result = mvc.perform(req).andReturn();
		content = result.getResponse().getContentAsString();
		catalogueDTO = objectMapper.readValue(content, CatalogueDTO.class);
		Assert.assertFalse(Optional.ofNullable(catalogueDTO).isEmpty());
		log.info("deleteCatalogueTest successful");
	}

	/**
	 * Delete catalogue invalid id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteCatalogueInvalidIdTest() throws Exception {
		String uri = "/v1/api/catalogue/1004";
		mvc.perform(MockMvcRequestBuilders.delete(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("deleteCatalogueInvalidIdTest successful");
	}

	/**
	 * Delete catalogue item linked test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteCatalogueItemLinkedTest() throws Exception {
		catalogueDTO.setCatalogueCode("testCode10009");
		String uri = "/v1/api/catalogue";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setId(null);
		catalogueItemDTO.setCatalogueCode("testCode10009");

		UomDTO uomDTO = new UomDTO();
		uomDTO.setId(1);
		uomDTO.setName("USM");
		List<UomDTO> uomDTOList = new ArrayList<>();
		uomDTOList.add(uomDTO);
		MasterServiceMock.setUpMockGetAllActiveUomsByNameUsingGET(wireMockServer, catalogueItemDTO.getUomName(),
				uomDTOList);

		mvc.perform(MockMvcRequestBuilders.post("/v1/api/catalogue/{catalogueCode}/catalogue-item", "testCode10009")
				.contentType(MediaType.APPLICATION_JSON).content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		CatalogueDTO catalogueDTO = objectMapper.readValue(content, CatalogueDTO.class);
		uri += "/" + catalogueDTO.getId();
		mvc.perform(MockMvcRequestBuilders.delete(uri)).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("deleteCatalogueItemLinkedTest successful");
	}

	/**
	 * Delete catalogue categories linked test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteCatalogueCategoriesLinkedTest() throws Exception {
		String uri = "/v1/api/catalogue";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		CatalogueDTO catalogueDTO = objectMapper.readValue(content, CatalogueDTO.class);

		String category = "{" + " \"categoryCode\": \"testCodeC\"," + " \"categoryName\": \"testNameN\","
				+ " \"deptId\": 1," + " \"deptName\":\"testDept\"" + " } ";
		uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/category";
		req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON).content(category)
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req);

		uri = "/v1/api/catalogue/" + catalogueDTO.getId();
		mvc.perform(MockMvcRequestBuilders.delete(uri)).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	/**
	 * Sets the catalogue active yn success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void setCatalogueActiveYnSuccessTest() throws Exception {
		String uri = "/v1/api/catalogue";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);

		CatalogueDTO response = objectMapper.readValue(content, CatalogueDTO.class);
		uri += "/" + response.getId() + "/activeYn/1";
		mvc.perform(MockMvcRequestBuilders.put(uri)).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("setCatalogueActiveYnSuccessTest successful");
	}

	/**
	 * Sets the catalogue active yn invalid id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void setCatalogueActiveYnInvalidIdTest() throws Exception {
		String uri = "/v1/api/catalogue/-1/activeYn/1";
		mvc.perform(MockMvcRequestBuilders.put(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("setCatalogueActiveYnInvalidIdTest successful");
	}

	/**
	 * Sets the catalogue active yn item linked test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void setCatalogueActiveYnItemLinkedTest() throws Exception {
		catalogueDTO.setCatalogueCode("testCode12909");
		String uri = "/v1/api/catalogue";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setId(null);

		UomDTO uomDTO = new UomDTO();
		uomDTO.setId(1);
		uomDTO.setName("USM");
		List<UomDTO> uomDTOList = new ArrayList<>();
		uomDTOList.add(uomDTO);
		MasterServiceMock.setUpMockGetAllActiveUomsByNameUsingGET(wireMockServer, catalogueItemDTO.getUomName(),
				uomDTOList);

		mvc.perform(MockMvcRequestBuilders.post("/v1/api/catalogue/{catalogueCode}/catalogue-item", "testCode12909")
				.contentType(MediaType.APPLICATION_JSON).content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		CatalogueDTO catalogueDTO = objectMapper.readValue(content, CatalogueDTO.class);
		uri += "/" + catalogueDTO.getId() + "/activeYn/1";
		mvc.perform(MockMvcRequestBuilders.put(uri)).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("setCatalogueActiveYnItemLinkedTest successful");
	}
}
