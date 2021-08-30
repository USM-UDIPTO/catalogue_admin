package com.dxc.eproc.catalogue.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.catalogue.dto.request.SpecificationDTO;
import com.dxc.eproc.catalogue.dto.request.SpecificationSearchDTO;
import com.dxc.eproc.catalogue.model.CatalogueItem;
import com.dxc.eproc.catalogue.repository.CatalogueItemRepository;
import com.dxc.eproc.utils.RestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

// TODO: Auto-generated Javadoc
/**
 * The Class SpecificationControllerTestIT.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SpecificationControllerTestIT extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private final static Logger log = LoggerFactory.getLogger(SpecificationControllerTestIT.class);

	/** The SpecificationDTO info. */
	private static SpecificationDTO specificationDTO;

	/** The catalogue item. */
	private static CatalogueItem catalogueItem;

	/** The v. */
	private static int v = 0;

	/** The catalogue item repository. */
	@Autowired
	private CatalogueItemRepository catalogueItemRepository;

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

		catalogueItem = new CatalogueItem();
		catalogueItem.setCatalogueCode("test");
		catalogueItem.setCatalogueName("KPWD_CAT");
		catalogueItem.setItemCode("item019s" + v++);
		catalogueItem.setItemName("ItemName9s" + v);
		catalogueItem.setUomName("test");
		catalogueItem.setDeptId(1);
		catalogueItem.setDeptName("KPWD");
		catalogueItem.setCategories(new ArrayList<>());
		catalogueItem.setUnspscDetails(new ArrayList<>());
		catalogueItem.setSpecifications(Collections.emptyMap());
		catalogueItem = catalogueItemRepository.save(catalogueItem);
	}

	/**
	 * Creates the specification.
	 */
	@BeforeMethod
	public static void createSpecification() {
		specificationDTO = new SpecificationDTO();
		specificationDTO.setSpecName("testName" + v++);
		specificationDTO.setActiveYn(true);
		specificationDTO.setDeptId(1);
		specificationDTO.setDeptName("testDept");
	}

	/**
	 * Creates the specification success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createSpecificationSuccessTest() throws Exception {
		String uri = "/v1/api/specification";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(specificationDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isCreated());
		log.info("createSpecificationSuccessTest successful");
	}

	/**
	 * Creates the specification null id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createSpecificationNullIdTest() throws Exception {
		String uri = "/v1/api/specification";
		specificationDTO.setId(1L);
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(specificationDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("createSpecificationNullIdTest successful");
	}

	/**
	 * Creates the specification name exists test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createSpecificationNameExistsTest() throws Exception {
		String uri = "/v1/api/specification";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(specificationDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);

		SpecificationDTO response = objectMapper.readValue(content, SpecificationDTO.class);
		response.setId(null);
		req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(response)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("createSpecificationNameExistsTest successful");
	}

	/**
	 * Update specification success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateSpecificationSuccessTest() throws Exception {
		String uri = "/v1/api/specification";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(specificationDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);

		SpecificationDTO response = objectMapper.readValue(content, SpecificationDTO.class);
		uri += "/" + response.getId();
		req = MockMvcRequestBuilders.put(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(response)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("updateSpecificationSuccessTest successful");
	}

	/**
	 * Update specification invalid id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateSpecificationInvalidIdTest() throws Exception {
		String uri = "/v1/api/specification/0";
		RequestBuilder req = MockMvcRequestBuilders.put(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(specificationDTO)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("updateSpecificationInvalidIdTest successful");
	}

	/**
	 * Update specification name exists test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateSpecificationNameExistsTest() throws Exception {
		String uri = "/v1/api/specification";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(specificationDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);

		SpecificationDTO response = objectMapper.readValue(content, SpecificationDTO.class);
		Long id = response.getId();
		response.setId(null);
		response.setSpecName("test2");
		req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(response)).contentType(MediaType.APPLICATION_JSON);
		result = mvc.perform(req).andReturn();
		content = result.getResponse().getContentAsString();
		log.info(content);
		uri += "/" + id;
		response.setSpecName("test2");
		req = MockMvcRequestBuilders.put(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(response)).contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("updateSpecificationNameExistsTest successful");
	}

	/**
	 * Gets the all specifications success test.
	 *
	 * @return the all specifications success test
	 * @throws Exception the exception
	 */
	@Test(dependsOnMethods = "createSpecificationSuccessTest")
	public void getAllSpecificationsSuccessTest() throws Exception {
		String uri = "/v1/api/specification/dept/1";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("getAllSpecificationsSuccessTest successful");
	}

	/**
	 * Gets the all specifications no records test.
	 *
	 * @return the all specifications no records test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllSpecificationsNoRecordsTest() throws Exception {
		String uri = "/v1/api/specification/dept/-1";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getAllSpecificationsNoRecordsTest successful");
	}

	/**
	 * Search specifications by dept id test.
	 *
	 * @throws Exception the exception
	 */
	@Test(dependsOnMethods = "createSpecificationSuccessTest")
	public void searchSpecificationsByDeptIdTest() throws Exception {
		String uri = "/v1/api/specification/search";
		SpecificationSearchDTO specificationSearchDTO = new SpecificationSearchDTO();
		specificationSearchDTO.setDeptId(1);
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(specificationSearchDTO))
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("searchSpecificationsByDeptIdTest successful");
	}

	/**
	 * Search specifications by dept id and spec name test.
	 *
	 * @throws Exception the exception
	 */
	@Test(dependsOnMethods = "createSpecificationSuccessTest")
	public void searchSpecificationsByDeptIdAndSpecNameTest() throws Exception {
		String uri = "/v1/api/specification/search";
		SpecificationSearchDTO specificationSearchDTO = new SpecificationSearchDTO();
		specificationSearchDTO.setDeptId(1);
		specificationSearchDTO.setSpecName("te");
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(specificationSearchDTO))
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("searchSpecificationsByDeptIdAndSpecNameTest successful");
	}

	/**
	 * Search specifications no records test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void searchSpecificationsNoRecordsTest() throws Exception {
		String uri = "/v1/api/specification/search";
		SpecificationSearchDTO specificationSearchDTO = new SpecificationSearchDTO();
		specificationSearchDTO.setDeptId(2);
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(specificationSearchDTO))
				.contentType(MediaType.APPLICATION_JSON);
		mvc.perform(req).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("searchSpecificationsNoRecordsTest successful");
	}

	/**
	 * Gets the specification for item success test.
	 *
	 * @return the specification for item success test
	 * @throws Exception the exception
	 */
	@Test
	public void getSpecificationForItemSuccessTest() throws Exception {
		Map<String, String> specifications = new HashMap<>();
		specifications.put("Color", "Red");
		catalogueItem.setSpecifications(specifications);
		catalogueItemRepository.save(catalogueItem);
		String uri = "/v1/api/specification/item/" + catalogueItem.getItemCode();
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("getSpecificationForItemSuccessTest successful");
	}

	/**
	 * Gets the specifications for item no record found test.
	 *
	 * @return the specifications for item no record found test
	 * @throws Exception the exception
	 */
	@Test
	public void getSpecificationsForItemNoRecordFoundTest() throws Exception {
		String uri = "/v1/api/specification/item/9999";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getSpecificationsForItemNoRecordFoundTest successful");
	}

	/**
	 * Gets the specification for item no specifications test.
	 *
	 * @return the specification for item no specifications test
	 * @throws Exception the exception
	 */
	@Test
	public void getSpecificationForItemNoSpecificationsTest() throws Exception {
		String uri = "/v1/api/specification/item/" + catalogueItem.getItemCode();
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getSpecificationForItemNoSpecificationsTest successful");
	}

	/**
	 * Gets the specification success test.
	 *
	 * @return the specification success test
	 * @throws Exception the exception
	 */
	@Test
	public void getSpecificationSuccessTest() throws Exception {
		String uri = "/v1/api/specification";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(specificationDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);

		SpecificationDTO response = objectMapper.readValue(content, SpecificationDTO.class);
		uri += "/" + response.getId();
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("getSpecificationSuccessTest successful");
	}

	/**
	 * Gets the specification invalid id test.
	 *
	 * @return the specification invalid id test
	 * @throws Exception the exception
	 */
	@Test
	public void getSpecificationInvalidIdTest() throws Exception {
		String uri = "/v1/api/specification/-1";
		mvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("getSpecificationInvalidIdTest successful");
	}

	/**
	 * Sets the specification active yn success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void setSpecificationActiveYnSuccessTest() throws Exception {
		String uri = "/v1/api/specification";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(specificationDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);

		SpecificationDTO response = objectMapper.readValue(content, SpecificationDTO.class);
		uri += "/" + response.getId() + "/activeYn/1";
		mvc.perform(MockMvcRequestBuilders.put(uri)).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("setSpecificationActiveYnSuccessTest successful");
	}

	/**
	 * Sets the specification active yn invalid id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void setSpecificationActiveYnInvalidIdTest() throws Exception {
		String uri = "/v1/api/specification/-1/activeYn/1";
		mvc.perform(MockMvcRequestBuilders.put(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("setSpecificationActiveYnInvalidIdTest successful");
	}

	/**
	 * Delete specification success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteSpecificationSuccessTest() throws Exception {
		String uri = "/v1/api/specification";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(specificationDTO)).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(req).andReturn();
		String content = result.getResponse().getContentAsString();
		log.info(content);

		SpecificationDTO response = objectMapper.readValue(content, SpecificationDTO.class);
		uri += "/" + response.getId();
		mvc.perform(MockMvcRequestBuilders.delete(uri)).andExpect(MockMvcResultMatchers.status().isOk());
		log.info("deleteSpecificationSuccessTest successful");
	}

	/**
	 * Delete specification invalid id test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteSpecificationInvalidIdTest() throws Exception {
		String uri = "/v1/api/specification/99999";
		mvc.perform(MockMvcRequestBuilders.delete(uri)).andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("deleteSpecificationInvalidIdTest successful");
	}
}
