package com.dxc.eproc.catalogue.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.activemq.broker.BrokerService;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.catalogue.CatalogueAdminServiceApplication;
import com.dxc.eproc.catalogue.documentstore.CatalogueDocumentStore;
import com.dxc.eproc.catalogue.dto.request.CatalogueDTO;
import com.dxc.eproc.catalogue.dto.request.CatalogueItemDTO;
import com.dxc.eproc.catalogue.dto.request.CatalogueItemSearchDTO;
import com.dxc.eproc.catalogue.dto.request.CatalogueUploadFileDTO;
import com.dxc.eproc.catalogue.dto.request.CatalogueUploadFileDetailsDTO;
import com.dxc.eproc.catalogue.dto.request.CategoryDTO;
import com.dxc.eproc.catalogue.dto.request.CategoryDetailsDTO;
import com.dxc.eproc.catalogue.dto.request.UnspscDetailsDTO;
import com.dxc.eproc.catalogue.mapper.CatalogueItemMapper;
import com.dxc.eproc.catalogue.mapper.CatalogueUploadFileDetailsMapper;
import com.dxc.eproc.catalogue.mapper.CatalogueUploadFileMapper;
import com.dxc.eproc.catalogue.model.CatalogueItem;
import com.dxc.eproc.catalogue.model.CatalogueUploadFile;
import com.dxc.eproc.catalogue.model.CatalogueUploadFileDetails;
import com.dxc.eproc.catalogue.repository.CatalogueItemRepository;
import com.dxc.eproc.catalogue.repository.CatalogueUploadFileDetailsRepository;
import com.dxc.eproc.catalogue.repository.CatalogueUploadFileRepository;
import com.dxc.eproc.catalogue.services.CatalogueUploadFileService;
import com.dxc.eproc.utils.RestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

// TODO: Auto-generated Javadoc
/**
 * The Class CatalogueItemControllerIT.
 */
@SpringBootTest(classes = CatalogueAdminServiceApplication.class)
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
public class CatalogueItemControllerIT extends AbstractTestNGSpringContextTests {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(CatalogueItemControllerIT.class);

	/** The broker service. */
	private static BrokerService brokerService;

	/** The catalogue upload file. */
	private CatalogueUploadFile catalogueUploadFile;

	/** The catalogue upload file DTO. */
	private CatalogueUploadFileDTO catalogueUploadFileDTO;

	/** The catalogue upload file details. */
	private CatalogueUploadFileDetails catalogueUploadFileDetails;

	/** The catalogue upload file details DTO. */
	private CatalogueUploadFileDetailsDTO catalogueUploadFileDetailsDTO;

	/** The catlogue item mock mvc. */
	@Autowired
	private MockMvc catlogueItemMockMvc;

	/** The catalogue item repository. */
	@Autowired
	private CatalogueItemRepository catalogueItemRepository;

	/** The Catalogue item mapper. */
	@Autowired
	private CatalogueItemMapper catalogueItemMapper;

	/** The catalogue upload file repository. */
	@Autowired
	private CatalogueUploadFileRepository catalogueUploadFileRepository;

	/** The catalogue upload file details repository. */
	@Autowired
	private CatalogueUploadFileDetailsRepository catalogueUploadFileDetailsRepository;

	/** The catalogue upload file mapper. */
	@Autowired
	private CatalogueUploadFileMapper catalogueUploadFileMapper;

	/** The catalogue upload file details mapper. */
	@Autowired
	private CatalogueUploadFileDetailsMapper catalogueUploadFileDetailsMapper;

	/** The catalogue item controller. */
	@Autowired
	private CatalogueItemController catalogueItemController;

	/** The catalogue upload file service. */
	@Autowired
	private CatalogueUploadFileService catalogueUploadFileService;

	/** The catalogue document store. */
	@Autowired
	private CatalogueDocumentStore catalogueDocumentStore;

	/** The object mapper. */
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * Inits the.
	 *
	 * @throws Exception the exception
	 */
	@BeforeClass
	public static void init() throws Exception {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");

		System.setProperty("spring.profiles.active", "test");
		brokerService = new BrokerService();

		brokerService.addConnector("amqp://localhost:5671");
		brokerService.setPersistent(false);
		brokerService.getManagementContext().setCreateConnector(false);

		brokerService.start();
		brokerService.waitUntilStarted();

	}
//	/**
//	 * Gets the proxy to broker.
//	 *
//	 * @return the proxy to broker
//	 * @throws MalformedObjectNameException the malformed object name exception
//	 * @throws JMSException                 the JMS exception
//	 */
//	protected BrokerViewMBean getProxyToBroker() throws MalformedObjectNameException, JMSException {
//		ObjectName brokerViewMBean = new ObjectName("org.apache.activemq:type=Broker,brokerName=localhost");
//		BrokerViewMBean proxy = (BrokerViewMBean) brokerService.getManagementContext().newProxyInstance(brokerViewMBean,
//				BrokerViewMBean.class, true);
//		return proxy;
//	}

//	/**
//	 * Gets the proxy to queue.
//	 *
//	 * @param name the name
//	 * @return the proxy to queue
//	 * @throws MalformedObjectNameException the malformed object name exception
//	 * @throws JMSException                 the JMS exception
//	 */
//	protected QueueViewMBean getProxyToQueue(String name) throws MalformedObjectNameException, JMSException {
//		ObjectName queueViewMBeanName = new ObjectName(
//				"org.apache.activemq:type=Broker,brokerName=localhost,destinationType=Queue,destinationName=" + name);
//		QueueViewMBean proxy = (QueueViewMBean) brokerService.getManagementContext()
//				.newProxyInstance(queueViewMBeanName, QueueViewMBean.class, true);
//		return proxy;
//	}

	/**
	 * Inits the before method.
	 */
	@BeforeMethod
	public void initBeforeMethod() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");

		catalogueUploadFile = new CatalogueUploadFile();

		catalogueUploadFile.setId(100L);
		catalogueUploadFile.setFileName("Test");
		catalogueUploadFile.setStatus("SUCCESS");
		catalogueUploadFile.setCreatedBy("system");
		catalogueUploadFile.setLastModifiedBy("system");

		catalogueUploadFileDTO = new CatalogueUploadFileDTO();
		BeanUtils.copyProperties(catalogueUploadFile, catalogueUploadFileDTO);

		catalogueUploadFileDetails = new CatalogueUploadFileDetails();

		catalogueUploadFileDetails.setCatalogueCode("Cat1");
		catalogueUploadFileDetails.setCatalogueFileId(200L);
		catalogueUploadFileDetails.setId(100L);
		catalogueUploadFileDetails.setItemCode("ItemCode1");
		catalogueUploadFileDetails.setItemName("ItemName1");
		catalogueUploadFileDetails.setStatus("FAILED");
		catalogueUploadFileDetails.setErrorReason("Error");

		catalogueUploadFileDetailsDTO = new CatalogueUploadFileDetailsDTO();
		BeanUtils.copyProperties(catalogueUploadFileDetails, catalogueUploadFileDetailsDTO);
	}

	/**
	 * Gets the catalogue.
	 *
	 * @return the catalogue
	 * @throws Exception the exception
	 */
	@BeforeClass
	public void getCatalogue() throws Exception {
		CatalogueDTO catalogueDTO = new CatalogueDTO();
		catalogueDTO.setCatalogueCode("catCode");
		catalogueDTO.setCatalogueName("testName");
		catalogueDTO.setDeptId(1);
		catalogueDTO.setDeptName("testDept");
		String uri = "/v1/api/catalogue";
		RequestBuilder req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(catalogueDTO)).contentType(MediaType.APPLICATION_JSON);
		String content = catlogueItemMockMvc.perform(req).andReturn().getResponse().getContentAsString();
		catalogueDTO = objectMapper.readValue(content, CatalogueDTO.class);

		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setCategoryCode("CAT01");
		categoryDTO.setCategoryName("testName");
		categoryDTO.setDeptId(1);
		categoryDTO.setActiveYn(true);
		categoryDTO.setDeptName("testDept");
		uri = "/v1/api/catalogue/" + catalogueDTO.getId() + "/category";
		req = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(RestUtil.convertObjectToJsonBytes(categoryDTO)).contentType(MediaType.APPLICATION_JSON);
		catlogueItemMockMvc.perform(req);
	}

	/**
	 * Creates the catalogue item DTO.
	 *
	 * @return the catalogue item DTO
	 */
	private CatalogueItemDTO createCatalogueItemDTO() {
		CatalogueItemDTO catalogueItemDTO = new CatalogueItemDTO();
		catalogueItemDTO.setId(100L);
		catalogueItemDTO.setCatalogueCode("catCode");
		catalogueItemDTO.setCatalogueName("KPWD_CAT");
		catalogueItemDTO.setItemCode("item01i");
		catalogueItemDTO.setItemName("ItemNamei");
		catalogueItemDTO.setPartNumber("partNumber");
		catalogueItemDTO.setUomId(1L);
		catalogueItemDTO.setUomName("test");
		catalogueItemDTO.setDeptId(1);
		catalogueItemDTO.setDeptName("KPWD");
		Map<String, String> specifications = new HashMap<>();
		specifications.put("Color", "Red");
		specifications.put("Size", "10");
		catalogueItemDTO.setSpecifications(specifications);

		List<CategoryDetailsDTO> categoryDetailsList = new ArrayList<>();
		categoryDetailsList.add(new CategoryDetailsDTO("CAT01", "CATNAME"));
		catalogueItemDTO.setCategories(categoryDetailsList);

		List<UnspscDetailsDTO> unspscDetailsList = new ArrayList<>();
		unspscDetailsList.add(new UnspscDetailsDTO("10000000", "test_segment", "segment"));
		unspscDetailsList.add(new UnspscDetailsDTO("10100000", "test_family", "family"));
		unspscDetailsList.add(new UnspscDetailsDTO("10101000", "test_class", "class"));
		unspscDetailsList.add(new UnspscDetailsDTO("10101010", "test_commodity", "commodity"));
		catalogueItemDTO.setUnspscDetails(unspscDetailsList);
		return catalogueItemDTO;
	}

	/**
	 * Creates the catalogue item test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	@Transactional
	public void createCatalogueItem_SuccessTest() throws Exception {
		log.info("==================================================================================");
		log.info("Test - createCatalogueItemTest Start");
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setId(null);
		catalogueItemDTO.setSpecifications(null);
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders.post("/v1/api/catalogue/{catalogueCode}/catalogue-item", "catCode")
						.contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

		log.info("==================================================================================");
		log.info("Test - createCatalogueItemTest End");
	}

	/**
	 * Creates the catalogue item success 2 test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 1)
	@Transactional
	public void createCatalogueItem_Success2Test() throws Exception {
		log.info("==================================================================================");
		log.info("Test - createCatalogueItemTest Start");
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setItemCode("itemCode1092123");
		catalogueItemDTO.setItemName("itemName1092123");
		catalogueItemDTO.setId(null);
		catalogueItemDTO.setCategories(new ArrayList<>());
		catalogueItemDTO.setUnspscDetails(new ArrayList<>());
		catalogueItemDTO.setSpecifications(null);
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders.post("/v1/api/catalogue/{catalogueCode}/catalogue-item", "catCode")
						.contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

		log.info("==================================================================================");
		log.info("Test - createCatalogueItemTest End");
	}

	/**
	 * Creates the catalogue item test id not null bad request.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 2)
	@Transactional
	public void createCatalogueItemTest_IdNotNull_BadRequest() throws Exception {
		log.info("==================================================================================");
		log.info("Test - createCatalogueItemTest_IdNotNull_BadRequest Start");
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders.post("/v1/api/catalogue/{catalogueCode}/catalogue-item", "catCode")
						.contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO)))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());

		log.info("==================================================================================");
		log.info("Test - createCatalogueItemTest_IdNotNull_BadRequest End");
	}

	/**
	 * Creates the catalogue item catalogue not found.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createCatalogueItem_catalogueNotFound() throws Exception {
		log.info("==================================================================================");
		log.info("Test - createCatalogueItemTest_IdNotNull_BadRequest Start");
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setId(null);
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders.post("/v1/api/catalogue/{catalogueCode}/catalogue-item", "zzzzz")
						.contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO)))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());

		log.info("==================================================================================");
		log.info("Test - createCatalogueItemTest_IdNotNull_BadRequest End");
	}

	/**
	 * Creates the catalogue item test item code exists bad request.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 3)
	@Transactional
	public void createCatalogueItemTest_ItemCodeExists_BadRequest() throws Exception {
		log.info("==================================================================================");
		log.info("Test - createCatalogueItemTest_IdNotNull_BadRequest Start");
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setId(null);
		catalogueItemDTO.setItemCode("codeExists");
		catalogueItemDTO.setItemName("nameExists");
		catalogueItemRepository.save(catalogueItemMapper.toEntity(catalogueItemDTO));
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders.post("/v1/api/catalogue/{catalogueCode}/catalogue-item", "catCode")
						.contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO)))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
		log.info("==================================================================================");
		log.info("Test - createCatalogueItemTest_IdNotNull_BadRequest End");
	}

	/**
	 * Creates the catalogue item test item name exists bad request.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 4)
	@Transactional
	public void createCatalogueItemTest_ItemNameExists_BadRequest() throws Exception {
		log.info("==================================================================================");
		log.info("Test - createCatalogueItemTest_IdNotNull_BadRequest Start");
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setId(null);
		catalogueItemDTO.setItemCode("codeExists1");
		catalogueItemDTO.setItemName("nameExists1");
		catalogueItemRepository.save(catalogueItemMapper.toEntity(catalogueItemDTO));
		CatalogueItemDTO catalogueItemDTO1 = createCatalogueItemDTO();
		catalogueItemDTO1.setId(null);
		catalogueItemDTO1.setItemCode("codeExists2");
		catalogueItemDTO1.setItemName("nameExists1");
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders.post("/v1/api/catalogue/{catalogueCode}/catalogue-item", "catCode")
						.contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO1)))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
		log.info("==================================================================================");
		log.info("Test - createCatalogueItemTest_IdNotNull_BadRequest End");
	}

	/**
	 * Creates the catalogue item category not found test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void createCatalogueItem_categoryNotFoundTest() throws Exception {
		log.info("==================================================================================");
		log.info("Test - createCatalogueItem_categoryNotFoundTest Start");
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setItemCode("itemCode119223");
		catalogueItemDTO.setItemName("itemName119223");
		catalogueItemDTO.setId(null);
		catalogueItemDTO.setSpecifications(null);
		List<CategoryDetailsDTO> categoryDetailsList = new ArrayList<>();
		categoryDetailsList.add(new CategoryDetailsDTO("INVALID01", "CATNAME"));
		catalogueItemDTO.setCategories(categoryDetailsList);
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders.post("/v1/api/catalogue/{catalogueCode}/catalogue-item", "catCode")
						.contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO)))
				.andExpect(MockMvcResultMatchers.status().isNotFound());

		log.info("==================================================================================");
		log.info("Test - createCatalogueItem_categoryNotFoundTest End");
	}

	/**
	 * Creates the catalogue item test incorrect data bad request.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void createCatalogueItemTest_incorrectData_BadRequest() throws Exception {
		log.info("==================================================================================");
		log.info("Test - createCatalogueItemTest_incorrectData_BadRequest Start");
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setId(null);
		catalogueItemDTO.setSpecifications(null);
		catalogueItemDTO.setUnspscDetails(Arrays.asList(new UnspscDetailsDTO()));
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders.post("/v1/api/catalogue/{catalogueCode}/catalogue-item", "catCode")
						.contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
		// segment
		List<UnspscDetailsDTO> unspscDetailsList = new ArrayList<>();
		unspscDetailsList.add(new UnspscDetailsDTO("test_segment", "10000000", "segment"));
		unspscDetailsList.add(new UnspscDetailsDTO("test_family", "10100000", "segment"));
		unspscDetailsList.add(new UnspscDetailsDTO("test_class", "10101000", "class"));
		unspscDetailsList.add(new UnspscDetailsDTO("test_commodity", "10101010", "commodity"));
		catalogueItemDTO.setUnspscDetails(unspscDetailsList);
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders.post("/v1/api/catalogue/{catalogueCode}/catalogue-item", "catCode")
						.contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
		// family
		unspscDetailsList = new ArrayList<>();
		unspscDetailsList.add(new UnspscDetailsDTO("test_segment", "10000000", "family"));
		unspscDetailsList.add(new UnspscDetailsDTO("test_family", "10100000", "segment"));
		unspscDetailsList.add(new UnspscDetailsDTO("test_class", "10101000", "family"));
		unspscDetailsList.add(new UnspscDetailsDTO("test_commodity", "10101010", "commodity"));
		catalogueItemDTO.setUnspscDetails(unspscDetailsList);
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders.post("/v1/api/catalogue/{catalogueCode}/catalogue-item", "catCode")
						.contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
		// class
		unspscDetailsList = new ArrayList<>();
		unspscDetailsList.add(new UnspscDetailsDTO("test_segment", "10000000", "segment"));
		unspscDetailsList.add(new UnspscDetailsDTO("test_family", "10100000", "family"));
		unspscDetailsList.add(new UnspscDetailsDTO("test_class", "10101000", "class"));
		unspscDetailsList.add(new UnspscDetailsDTO("test_commodity", "10101010", "class"));
		catalogueItemDTO.setUnspscDetails(unspscDetailsList);
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders.post("/v1/api/catalogue/{catalogueCode}/catalogue-item", "catCode")
						.contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
		// commodity
		unspscDetailsList = new ArrayList<>();
		unspscDetailsList.add(new UnspscDetailsDTO("test_segment", "10000000", "segment"));
		unspscDetailsList.add(new UnspscDetailsDTO("test_family", "10100000", "family"));
		unspscDetailsList.add(new UnspscDetailsDTO("test_class", "10101000", "class"));
		unspscDetailsList.add(new UnspscDetailsDTO("test_commodity", "10101010", "com"));
		catalogueItemDTO.setUnspscDetails(unspscDetailsList);
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders.post("/v1/api/catalogue/{catalogueCode}/catalogue-item", "catCode")
						.contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("==================================================================================");
		log.info("Test - createCatalogueItemTest_incorrectData_BadRequest End");
	}

	/**
	 * Update catalogue item test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 5)
	@Transactional
	public void updateCatalogueItem_SuccessTest() throws Exception {
		log.info("==================================================================================");
		log.info("Test - updateCatalogueItemTest Start");
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setId(null);
		catalogueItemDTO.setItemCode("updatedCode1");
		catalogueItemDTO.setItemName("updatedName1");
		CatalogueItem catalogueItem = catalogueItemRepository.save(catalogueItemMapper.toEntity(catalogueItemDTO));
		CatalogueItemDTO catalogueItemDTO1 = catalogueItemMapper.toDto(catalogueItem);
		catalogueItemDTO1.setSpecifications(null);
		log.info("Test - updateCatalogueItemTest catalogueItem id:" + catalogueItemDTO1.getId());
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders
						.put("/v1/api/catalogue/{catalogueCode}/catalogue-item/{id}",
								catalogueItemDTO1.getCatalogueCode(), catalogueItemDTO1.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO1)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

		log.info("==================================================================================");
		log.info("Test - updateCatalogueItemTest End");
	}

	/**
	 * Update catalogue item test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 5)
	@Transactional
	public void updateCatalogueItem_noCategoryDetailsTest() throws Exception {
		log.info("==================================================================================");
		log.info("Test - updateCatalogueItemTest Start");
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setId(null);
		catalogueItemDTO.setItemCode("updatedCode2");
		catalogueItemDTO.setItemName("updatedName2");
		catalogueItemDTO.setUnspscDetails(new ArrayList<>());
		CatalogueItem catalogueItem = catalogueItemRepository.save(catalogueItemMapper.toEntity(catalogueItemDTO));
		catalogueItemDTO = catalogueItemMapper.toDto(catalogueItem);
		catalogueItemDTO.setSpecifications(null);
		catalogueItemDTO.setCategories(null);
		log.info("Test - updateCatalogueItemTest catalogueItem id:" + catalogueItemDTO.getId());
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders
						.put("/v1/api/catalogue/{catalogueCode}/catalogue-item/{id}",
								catalogueItemDTO.getCatalogueCode(), catalogueItemDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

		log.info("==================================================================================");
		log.info("Test - updateCatalogueItem_noCategoryDetailsTest End");
	}

	/**
	 * Update catalogue item catalogue not found test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void updateCatalogueItem_catalogueNotFoundTest() throws Exception {
		log.info("==================================================================================");
		log.info("Test - updateCatalogueItem_catalogueNotFoundTest Start");
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catlogueItemMockMvc.perform(MockMvcRequestBuilders
				.put("/v1/api/catalogue/{catalogueCode}/catalogue-item/{id}", "zzzzz", catalogueItemDTO.getId())
				.contentType(MediaType.APPLICATION_JSON).content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO)))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("==================================================================================");
		log.info("Test - updateCatalogueItem_catalogueNotFoundTest End");
	}

	/**
	 * Update catalogue item test item not exists in DB.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception   the exception
	 */
	@Test(priority = 6)
	@Transactional
	public void updateCatalogueItemTest_ItemNotExistsInDB() throws IOException, Exception {
		log.info("==================================================================================");
		log.info("Test - updateCatalogueItemTest_ItemNotExistsInDB Start");
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setId(1000L);
		catalogueItemDTO.setItemCode("updatedCode");
		catalogueItemDTO.setItemName("updatedName");
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders
						.put("/v1/api/catalogue/{catalogueCode}/catalogue-item/{id}",
								catalogueItemDTO.getCatalogueCode(), catalogueItemDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO)))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());

		log.info("==================================================================================");
		log.info("Test - updateCatalogueItemTest_ItemNotExistsInDB End");
	}

	/**
	 * Update catalogue item test item code exists bad request.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception   the exception
	 */
	@Test(priority = 8)
	@Transactional
	public void updateCatalogueItemTest_ItemCodeExists_BadRequest() throws IOException, Exception {
		log.info("==================================================================================");
		log.info("Test - updateCatalogueItemTest_ItemCodeExists_BadRequest Start");
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setId(null);
		catalogueItemDTO.setItemCode("itemCodeExists");
		catalogueItemDTO.setItemName("itemNameExists");
		catalogueItemRepository.save(catalogueItemMapper.toEntity(catalogueItemDTO));

		CatalogueItemDTO catalogueItemDTO1 = createCatalogueItemDTO();
		catalogueItemDTO1.setId(null);
		catalogueItemDTO1.setItemCode("itemCodeExists1");
		catalogueItemDTO1.setItemName("itemNameExists1");
		CatalogueItem catalogueItem = catalogueItemRepository.save(catalogueItemMapper.toEntity(catalogueItemDTO1));
		CatalogueItemDTO catalogueItemDTO2 = catalogueItemMapper.toDto(catalogueItem);
		catalogueItemDTO2.setItemCode("itemCodeExists");
		catlogueItemMockMvc.perform(MockMvcRequestBuilders
				.put("/v1/api/catalogue/{catalogueCode}/catalogue-item/{id}", "catCode", catalogueItem.getId())
				.contentType(MediaType.APPLICATION_JSON).content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO2)))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());

		log.info("==================================================================================");
		log.info("Test - updateCatalogueItemTest_ItemCodeExists_BadRequest End");
	}

	/**
	 * Update catalogue item test item name exists bad request.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception   the exception
	 */
	@Test(priority = 9)
	@Transactional
	public void updateCatalogueItemTest_ItemNameExists_BadRequest() throws IOException, Exception {
		log.info("==================================================================================");
		log.info("Test - updateCatalogueItemTest_ItemNameExists_BadRequest Start");
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setId(null);
		catalogueItemDTO.setItemCode("itemCodeExists2");
		catalogueItemDTO.setItemName("itemNameExists2");
		catalogueItemRepository.save(catalogueItemMapper.toEntity(catalogueItemDTO));

		CatalogueItemDTO catalogueItemDTO1 = createCatalogueItemDTO();
		catalogueItemDTO1.setId(null);
		catalogueItemDTO1.setItemCode("itemCodeExists3");
		catalogueItemDTO1.setItemName("itemNameExists3");
		CatalogueItem catalogueItem = catalogueItemRepository.save(catalogueItemMapper.toEntity(catalogueItemDTO1));
		CatalogueItemDTO catalogueItemDTO2 = catalogueItemMapper.toDto(catalogueItem);
		catalogueItemDTO2.setItemName("itemNameExists2");
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders
						.put("/v1/api/catalogue/{catalogueCode}/catalogue-item/{id}", "catCode",
								catalogueItemDTO2.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO2)))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());

		log.info("==================================================================================");
		log.info("Test - updateCatalogueItemTest_ItemNameExists_BadRequest End");
	}

	/**
	 * Update catalogue item category not found.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void updateCatalogueItem_categoryNotFound() throws Exception {
		log.info("==================================================================================");
		log.info("Test - updateCatalogueItem_categoryNotFound Start");
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setId(null);
		catalogueItemDTO.setItemCode("updatedCode1_t");
		catalogueItemDTO.setItemName("updatedName1_t");
		CatalogueItem catalogueItem = catalogueItemRepository.save(catalogueItemMapper.toEntity(catalogueItemDTO));
		catalogueItemDTO = catalogueItemMapper.toDto(catalogueItem);
		catalogueItemDTO.setSpecifications(null);
		List<CategoryDetailsDTO> categoryDetailsList = new ArrayList<>();
		categoryDetailsList.add(new CategoryDetailsDTO("INVALID01", "CATNAME"));
		catalogueItemDTO.setCategories(categoryDetailsList);
		log.info("Test - updateCatalogueItemTest catalogueItem id:" + catalogueItemDTO.getId());
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders
						.put("/v1/api/catalogue/{catalogueCode}/catalogue-item/{id}",
								catalogueItemDTO.getCatalogueCode(), catalogueItemDTO.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO)))
				.andExpect(MockMvcResultMatchers.status().isNotFound());

		log.info("==================================================================================");
		log.info("Test - updateCatalogueItem_categoryNotFound End");
	}

	/**
	 * Update catalogue item incorrect data test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void updateCatalogueItem_incorrectDataTest() throws Exception {
		log.info("==================================================================================");
		log.info("Test - updateCatalogueItem_incorrectDataTest Start");
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setId(null);
		catalogueItemDTO.setItemCode("updatedCode23");
		catalogueItemDTO.setItemName("updatedName23");
		CatalogueItem catalogueItem = catalogueItemRepository.save(catalogueItemMapper.toEntity(catalogueItemDTO));
		CatalogueItemDTO catalogueItemDTO1 = catalogueItemMapper.toDto(catalogueItem);
		catalogueItemDTO1.setSpecifications(null);
		catalogueItemDTO1.setUnspscDetails(Arrays.asList(new UnspscDetailsDTO()));
		log.info("Test - updateCatalogueItemTest catalogueItem id:" + catalogueItemDTO1.getId());
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders
						.put("/v1/api/catalogue/{catalogueCode}/catalogue-item/{id}",
								catalogueItemDTO1.getCatalogueCode(), catalogueItemDTO1.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO1)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

		log.info("==================================================================================");
		log.info("Test - updateCatalogueItem_incorrectDataTest End");
	}

	/**
	 * Gets the catalogue item test.
	 *
	 * @return the catalogue item test
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception   the exception
	 */
	@Test(priority = 10)
	@Transactional
	public void getCatalogueItemTest() throws IOException, Exception {
		log.info("==================================================================================");
		log.info("Test - getCatalogueItemTest Start");
		CatalogueItemDTO catalogueItemDTO1 = createCatalogueItemDTO();
		catalogueItemDTO1.setId(null);
		catalogueItemDTO1.setItemCode("itemCodeExists1");
		catalogueItemDTO1.setItemName("itemNameExists1");
		CatalogueItem catalogueItem = catalogueItemRepository.save(catalogueItemMapper.toEntity(catalogueItemDTO1));
		catlogueItemMockMvc.perform(MockMvcRequestBuilders.get("/v1/api/catalogue/{catalogueCode}/catalogue-item/{id}",
				"catCode", catalogueItem.getId())).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

		log.info("==================================================================================");
		log.info("Test - getCatalogueItemTest End");
	}

	/**
	 * Gets the catalogue item test no records found.
	 *
	 * @return the catalogue item test no records found
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception   the exception
	 */
	@Test(priority = 11)
	@Transactional
	public void getCatalogueItemTest_NoRecordsFound() throws IOException, Exception {
		log.info("==================================================================================");
		log.info("Test - createCatalogueItemTest Start");
		catlogueItemMockMvc.perform(
				MockMvcRequestBuilders.get("/v1/api/catalogue/{catalogueCode}/catalogue-item/{id}", "01-100", 100))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());

		log.info("==================================================================================");
		log.info("Test - createCatalogueItemTest End");
	}

	/**
	 * Delete catalogue item test.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception   the exception
	 */
	@Test(priority = 12)
	@Transactional
	public void deleteCatalogueItemTest() throws IOException, Exception {
		log.info("==================================================================================");
		log.info("Test - deleteCatalogueItemTest Start");
		CatalogueItemDTO catalogueItemDTO1 = createCatalogueItemDTO();
		catalogueItemDTO1.setId(null);
		catalogueItemDTO1.setItemCode("deleteItemCodeExists1");
		catalogueItemDTO1.setItemName("deleteItemNameExists1");
		CatalogueItem catalogueItem = catalogueItemRepository.save(catalogueItemMapper.toEntity(catalogueItemDTO1));
		catlogueItemMockMvc.perform(MockMvcRequestBuilders
				.delete("/v1/api/catalogue/{catalogueCode}/catalogue-item/{id}", "catCode", catalogueItem.getId()))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

		log.info("==================================================================================");
		log.info("Test - deleteCatalogueItemTest End");
	}

	/**
	 * Delete catalogue item test no records found.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 13)
	@Transactional
	public void deleteCatalogueItemTest_NoRecordsFound() throws Exception {
		log.info("==================================================================================");
		log.info("Test - deleteCatalogueItemTest_NoRecordsFound Start");
		catlogueItemMockMvc.perform(
				MockMvcRequestBuilders.delete("/v1/api/catalogue/{catalogueCode}/catalogue-item/{id}", "01", 100))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());

		log.info("==================================================================================");
		log.info("Test - deleteCatalogueItemTest_NoRecordsFound End");
	}

	/**
	 * Select catalogue items success test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 14)
	@Transactional
	public void selectCatalogueItems_SuccessTest() throws Exception {
		log.info("==================================================================================");
		log.info("Test - selectCatalogueItems_SuccessTest Start");
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setId(null);
		catalogueItemDTO.setItemCode("selectItemCode");
		catalogueItemDTO.setItemName("selectItemName");

		CatalogueItem catalogueItem = catalogueItemRepository.save(catalogueItemMapper.toEntity(catalogueItemDTO));
		String uri = "/v1/api/catalogue/" + catalogueItem.getCatalogueCode() + "/catalogue-item/items";
		List<String> itemCodeList = Arrays.asList("selectItemCode");
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(itemCodeList)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		log.info("==================================================================================");
		log.info("Test - selectCatalogueItems_SuccessTest End");
	}

	/**
	 * Select catalogue items no items selected.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 15)
	@Transactional
	public void selectCatalogueItems_noItemsSelected() throws Exception {
		log.info("==================================================================================");
		log.info("Test - selectCatalogueItems_SuccessTest Start");
		String uri = "/v1/api/catalogue/01/catalogue-item/items";
		List<String> itemCodeList = new ArrayList<>();
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(itemCodeList)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("==================================================================================");
		log.info("Test - selectCatalogueItems_noItemsSelected End");
	}

	/**
	 * Select catalogue items catalogue not found.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 16)
	@Transactional
	public void selectCatalogueItems_catalogueNotFound() throws Exception {
		log.info("==================================================================================");
		log.info("Test - selectCatalogueItems_SuccessTest Start");
		String uri = "/v1/api/catalogue/01/catalogue-item/items";
		List<String> itemCodeList = Arrays.asList("selectItemCode");
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(itemCodeList)))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("==================================================================================");
		log.info("Test - selectCatalogueItems_catalogueNotFound End");
	}

	/**
	 * Select catalogue items no items found.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 17)
	@Transactional
	public void selectCatalogueItems_noItemsFound() throws Exception {
		log.info("==================================================================================");
		log.info("Test - selectCatalogueItems_SuccessTest Start");
		String uri = "/v1/api/catalogue/catCode/catalogue-item/items";
		List<String> itemCodeList = Arrays.asList("zzzzzz");
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(itemCodeList)))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("==================================================================================");
		log.info("Test - selectCatalogueItems_noItemsFound End");
	}

	/**
	 * Search catalogue items success.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 18)
	@Transactional
	public void searchCatalogueItems_success() throws Exception {
		log.info("==================================================================================");
		log.info("Test - selectCatalogueItems_SuccessTest Start");
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setId(null);
		catalogueItemDTO.setItemCode("searchItemCodev");
		catalogueItemDTO.setItemName("searchItemNamev");
		CatalogueItem catalogueItem = catalogueItemRepository.save(catalogueItemMapper.toEntity(catalogueItemDTO));
		CatalogueItemSearchDTO catalogueItemSearchDTO = new CatalogueItemSearchDTO();
		catalogueItemSearchDTO.setCatalogueCode("catCode");
		List<String> categoryCodeList = Arrays.asList("CAT01");
		catalogueItemSearchDTO.setCategoryCodeList(categoryCodeList);
		String uri = "/v1/api/catalogue/" + catalogueItem.getCatalogueCode() + "/catalogue-item/search";
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(catalogueItemSearchDTO)))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	/**
	 * Search catalogue items pagination.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void searchCatalogueItems_pagination() throws Exception {
		log.info("==================================================================================");
		log.info("Test - selectCatalogueItems_SuccessTest Start");
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setId(null);
		catalogueItemDTO.setItemCode("searchItemCodevv");
		catalogueItemDTO.setItemName("searchItemNamevv");
		CatalogueItem catalogueItem = catalogueItemRepository.save(catalogueItemMapper.toEntity(catalogueItemDTO));
		CatalogueItemSearchDTO catalogueItemSearchDTO = new CatalogueItemSearchDTO();
		catalogueItemSearchDTO.setCatalogueCode("catCode");
		List<String> categoryCodeList = Arrays.asList("CAT01");
		catalogueItemSearchDTO.setCategoryCodeList(categoryCodeList);
		String uri = "/v1/api/catalogue/" + catalogueItem.getCatalogueCode() + "/catalogue-item/search?page=10&size=1";
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(catalogueItemSearchDTO)))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	/**
	 * Search catalogue items success 2.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 19)
	@Transactional
	public void searchCatalogueItems_success2() throws Exception {
		log.info("==================================================================================");
		log.info("Test - selectCatalogueItems_SuccessTest Start");
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setId(null);
		catalogueItemDTO.setItemCode("searchItemCodevvv");
		catalogueItemDTO.setItemName("searchItemNamevvv");
		CatalogueItem catalogueItem = catalogueItemRepository.save(catalogueItemMapper.toEntity(catalogueItemDTO));
		CatalogueItemSearchDTO catalogueItemSearchDTO = new CatalogueItemSearchDTO();
		catalogueItemSearchDTO.setCatalogueCode("catCode");
		catalogueItemSearchDTO.setCatalogueItemCode(catalogueItem.getItemCode());
		catalogueItemSearchDTO.setCatalogueItemName(catalogueItem.getItemName());
		List<String> categoryCodeList = Arrays.asList("cat_code1", "zzz");
		catalogueItemSearchDTO.setCategoryCodeList(categoryCodeList);
		String uri = "/v1/api/catalogue/" + catalogueItem.getCatalogueCode() + "/catalogue-item/search";
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(catalogueItemSearchDTO)))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	/**
	 * Search catalogue items catalogue not found.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void searchCatalogueItems_noCategoriesSelected() throws Exception {
		log.info("==================================================================================");
		log.info("Test - searchCatalogueItems_noCategoriesSelected Start");
		String uri = "/v1/api/catalogue/catCode/catalogue-item/search";
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(new CatalogueItemSearchDTO())))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	/**
	 * Search catalogue items catalogue not found.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void searchCatalogueItems_catalogueNotFound() throws Exception {
		log.info("==================================================================================");
		log.info("Test - searchCatalogueItems_catalogueNotFound Start");
		String uri = "/v1/api/catalogue/zzzzxz/catalogue-item/search";
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(new CatalogueItemSearchDTO())))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	/**
	 * Upload catalogue items test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 20)
	@Transactional
	public void uploadCatalogueItemsTest() throws Exception {
		log.info("==================================================================================");
		log.info("Test - uploadCatalogueItemsTest Start");

		MockMultipartFile file = new MockMultipartFile("file", "hello.xlsx", MediaType.MULTIPART_FORM_DATA_VALUE,
				"Hello, World!".getBytes());
		Mockito.doNothing().when(catalogueDocumentStore).saveCatalogueDocument(Mockito.any());
		String uri = "/v1/api/catalogue/" + "catCode" + "/catalogue-upload";
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders.multipart(uri).file(file)
						.contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(MockMvcResultMatchers.status().isCreated());

//		QueueViewMBean queueView = getProxyToQueue("catalogue");
//		log.debug("Catalogue Count -" + queueView.getEnqueueCount());

		log.info("==================================================================================");
		log.info("Test - uploadCatalogueItemsTest End");
	}

	/**
	 * Upload catalogue items test upload file process.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 21)
	@Transactional
	public void uploadCatalogueFileProcess_SuccessTest() throws Exception {
		log.info("==================================================================================");
		log.info("Test - uploadCatalogueFileProcess_SuccessTest Start");
		CatalogueUploadFileDTO catFileDTO = new CatalogueUploadFileDTO();
		catFileDTO.setFileName("target/classes/hello.xlsx");
		catFileDTO.setStatus("IN_PROGRESS");
		CatalogueUploadFileDTO savedCatalogueUploadFileDTO = catalogueUploadFileService.save(catFileDTO);
		Path fileLocation = Paths.get("target/classes/hello.xlsx");
		byte[] data = Files.readAllBytes(fileLocation);
		Mockito.when(catalogueDocumentStore.getObject(Mockito.any(), Mockito.any())).thenReturn(data);
		String test = "catCode|" + savedCatalogueUploadFileDTO.getId() + "|hello.xlsx";

		catalogueItemController.uploadCatalogueFileProcess(test);
		log.info("==================================================================================");
		log.info("Test - uploadCatalogueFileProcess_SuccessTest End");
	}

	/**
	 * Upload catalogue items test with error message.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 22)
	@Transactional
	public void uploadCatalogueItemsTest_withErrorMessage() throws Exception {
		log.info("==================================================================================");
		log.info("Test - uploadCatalogueItemsTest_withErrorMessage Start");
		CatalogueUploadFileDTO catFileDTO = new CatalogueUploadFileDTO();
		catFileDTO.setFileName("target/classes/helloError.xlsx");
		catFileDTO.setStatus("IN_PROGRESS");
		CatalogueUploadFileDTO savedCatalogueUploadFileDTO = catalogueUploadFileService.save(catFileDTO);
		Path fileLocation = Paths.get("target/classes/helloError.xlsx");
		byte[] data = Files.readAllBytes(fileLocation);
		Mockito.when(catalogueDocumentStore.getObject(Mockito.any(), Mockito.any())).thenReturn(data);
		String test = "catCode|" + savedCatalogueUploadFileDTO.getId() + "|helloError.xlsx";
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setId(null);
		catalogueItemDTO.setItemCode("USM001");
		catalogueItemDTO.setItemName("TESTSAG001");
		catalogueItemDTO.setCategories(new ArrayList<>());
		catalogueItemDTO.setUnspscDetails(new ArrayList<>());
		catalogueItemDTO.setSpecifications(null);
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders.post("/v1/api/catalogue/{catalogueCode}/catalogue-item", "catCode")
						.contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

		catalogueItemController.uploadCatalogueFileProcess(test);
//		QueueViewMBean queueView = getProxyToQueue("catalogue");
//		log.debug("Catalogue Count -" + queueView.getEnqueueCount());
		log.info("==================================================================================");
		log.info("Test - uploadCatalogueItemsTest_withErrorMessage End");
	}

	/**
	 * Upload catalogue items test blank file.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 23)
	@Transactional
	public void uploadCatalogueItemsTest_BlankFile() throws Exception {
		log.info("==================================================================================");
		log.info("Test - uploadCatalogueItemsTest_BlankFile Start");
		CatalogueUploadFileDTO catFileDTO = new CatalogueUploadFileDTO();
		catFileDTO.setFileName("target/classes/helloBlank.xlsx");
		catFileDTO.setStatus("IN_PROGRESS");
		CatalogueUploadFileDTO savedCatalogueUploadFileDTO = catalogueUploadFileService.save(catFileDTO);
		Path fileLocation = Paths.get("target/classes/helloBlank.xlsx");
		byte[] data = Files.readAllBytes(fileLocation);
		Mockito.when(catalogueDocumentStore.getObject(Mockito.any(), Mockito.any())).thenReturn(data);
		String test = "catCode|" + savedCatalogueUploadFileDTO.getId() + "|helloBlank.xlsx";

		catalogueItemController.uploadCatalogueFileProcess(test);
		log.info("==================================================================================");
		log.info("Test - uploadCatalogueItemsTest_BlankFile End");
	}

	/**
	 * Upload catalogue items test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 24)
	@Transactional
	public void uploadCatalogueItemsTest_CatalogueNotFound() throws Exception {
		log.info("==================================================================================");
		log.info("Test - uploadCatalogueItemsTest_CatalogueNotFound Start");

		MockMultipartFile file = new MockMultipartFile("file", "hello.xlsx", MediaType.TEXT_PLAIN_VALUE,
				"Hello, World!".getBytes());
		String uri = "/v1/api/catalogue/" + "catCode-new" + "/catalogue-upload";
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders.multipart(uri).file(file)
						.contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
		log.info("==================================================================================");
		log.info("Test - uploadCatalogueItemsTest_CatalogueNotFound End");
	}

	/**
	 * Test get all catalogue upload file.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 25)
	@Transactional
	public void testGetAllCatalogueUploadFile() throws Exception {
		log.info("==================================================================================");
		log.info("Test - testGetAllCatalogueUploadFile Start");
		CatalogueUploadFileDTO catalogueUploadFileDTO1 = catalogueUploadFileDTO;
		catalogueUploadFileDTO1.setId(null);
		catalogueUploadFileRepository.save(catalogueUploadFileMapper.toEntity(catalogueUploadFileDTO1));
		String uri = "/v1/api/catalogue-upload/search-status";
		catlogueItemMockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
		log.info("==================================================================================");
		log.info("Test - testGetAllCatalogueUploadFile End");
	}

	/**
	 * Test get all catalogue upload file no records found.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 26)
	@Transactional
	public void testGetAllCatalogueUploadFile_noRecordsFound() throws Exception {
		log.info("==================================================================================");
		log.info("Test - testGetAllCatalogueUploadFile_noRecordsFound Start");
		catalogueUploadFileRepository.deleteAll();
		String uri = "/v1/api/catalogue-upload/search-status";
		catlogueItemMockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("==================================================================================");
		log.info("Test - testGetAllCatalogueUploadFile_noRecordsFound End");
	}

	/**
	 * Test get all catalogue upload file details.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 27)
	@Transactional
	public void testGetAllCatalogueUploadFileDetails() throws Exception {
		log.info("==================================================================================");
		log.info("Test - testGetAllCatalogueUploadFileDetails Start");
		CatalogueUploadFileDTO catalogueUploadFileDTO1 = catalogueUploadFileDTO;
		catalogueUploadFileDTO1.setId(null);
		CatalogueUploadFile catalogueUploadFile1 = catalogueUploadFileRepository
				.save(catalogueUploadFileMapper.toEntity(catalogueUploadFileDTO1));
		CatalogueUploadFileDetailsDTO catalogueUploadFileDetailsDTO1 = catalogueUploadFileDetailsDTO;
		catalogueUploadFileDetailsDTO1.setId(null);
		catalogueUploadFileDetailsDTO1.setCatalogueFileId(catalogueUploadFile1.getId());
		catalogueUploadFileDetailsRepository
				.save(catalogueUploadFileDetailsMapper.toEntity(catalogueUploadFileDetailsDTO1));
		String uri = "/v1/api/catalogue-upload/" + catalogueUploadFile1.getId() + "/view-details";
		catlogueItemMockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
		log.info("==================================================================================");
		log.info("Test - testGetAllCatalogueUploadFileDetails End");
	}

	/**
	 * Test get all catalogue upload file details no records found.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 28)
	@Transactional
	public void testGetAllCatalogueUploadFileDetails_noRecordsFound() throws Exception {
		log.info("==================================================================================");
		log.info("Test - testGetAllCatalogueUploadFileDetails_noRecordsFound Start");
		CatalogueUploadFileDTO catalogueUploadFileDTO1 = catalogueUploadFileDTO;
		catalogueUploadFileDTO1.setId(null);
		CatalogueUploadFile catalogueUploadFile1 = catalogueUploadFileRepository
				.save(catalogueUploadFileMapper.toEntity(catalogueUploadFileDTO1));
		catalogueUploadFileDetailsRepository.deleteAll();
		String uri = "/v1/api/catalogue-upload/" + catalogueUploadFile1.getId() + "/view-details";
		catlogueItemMockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("==================================================================================");
		log.info("Test - testGetAllCatalogueUploadFileDetails_noRecordsFound End");
	}

	/**
	 * Set Catalogue item ActiveYn test.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 29)
	@Transactional
	public void setCatalogueItemActiveYnTest_Success() throws Exception {
		log.info("==================================================================================");
		log.info("Test - setCatalogueItemActiveYnTest_Success Start");
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setId(100L);
		catalogueItemDTO.setCatalogueCode("catCode");
		catalogueItemDTO.setActiveYn(true);
		CatalogueItem catalogueItem = catalogueItemRepository.save(catalogueItemMapper.toEntity(catalogueItemDTO));
		CatalogueItemDTO catalogueItemDTO1 = catalogueItemMapper.toDto(catalogueItem);
		catalogueItemDTO1.setSpecifications(null);
		log.info("Test - setCatalogueItemActiveYnTest_Success catalogueItem id:" + catalogueItemDTO1.getId());
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders
						.put("/v1/api/catalogue/{catalogueCode}/catalogue-item/{id}/activeYn/{activeYn}",
								catalogueItemDTO1.getCatalogueCode(), catalogueItemDTO1.getId(),
								catalogueItemDTO1.getActiveYn())
						.contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO1)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

		log.info("==================================================================================");
		log.info("Test - setCatalogueItemActiveYnTest_Success End");
	}

	/**
	 * Set catalogue item test id null bad request.
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception   the exception
	 */
	@Test(priority = 30)
	@Transactional
	public void setCatalogueItemActiveYnTest_IdNull_BadRequest() throws IOException, Exception {
		log.info("==================================================================================");
		log.info("Test - setCatalogueItemActiveYnTest_IdNull_BadRequest Start");
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setId(null);
		catalogueItemDTO.setCatalogueCode("catCode");
		catalogueItemDTO.setActiveYn(true);
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders
						.put("/v1/api/catalogue/{catalogueCode}/catalogue-item/{id}/activeYn/{activeYn}", "catCode",
								100L, true)
						.contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO)))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());

		log.info("==================================================================================");
		log.info("Test - setCatalogueItemActiveYnTest_IdNull_BadRequest End");
	}

	/**
	 * Set catalogue Item activeYn catalogue not found.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception   the exception
	 */
	@Test(priority = 31)
	@Transactional
	public void setCatalogueItemActiveYnTest_CatalogueCodeNotExists() throws IOException, Exception {
		log.info("==================================================================================");
		log.info("Test - setCatalogueItemActiveYnTest_CatalogueCodeNotExists Start");
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setId(100L);
		catalogueItemDTO.setCatalogueCode("KPW");
		catalogueItemDTO.setActiveYn(true);
		catlogueItemMockMvc.perform(MockMvcRequestBuilders
				.put("/v1/api/catalogue/{catalogueCode}/catalogue-item/{id}/activeYn/{activeYn}",
						catalogueItemDTO.getCatalogueCode(), catalogueItemDTO.getId(), catalogueItemDTO.getActiveYn())
				.contentType(MediaType.APPLICATION_JSON).content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO)))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());

		log.info("==================================================================================");
		log.info("Test - setCatalogueItemActiveYnTest_CatalogueCodeNotExists End");
	}

	/**
	 * Set catalogue item id null test.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception   the exception
	 */
	@Test(priority = 32)
	@Transactional
	public void setCatalogueItemActiveYnTest_idNullTest() throws IOException, Exception {
		log.info("==================================================================================");
		log.info("Test - setCatalogueItemActiveYnTest_idNullTest Start");
		CatalogueItemDTO catalogueItemDTO = createCatalogueItemDTO();
		catalogueItemDTO.setId(null);
		catalogueItemDTO.setCatalogueCode("catCode");
		catalogueItemDTO.setActiveYn(true);
		CatalogueItem catalogueItem = catalogueItemRepository.save(catalogueItemMapper.toEntity(catalogueItemDTO));
		CatalogueItemDTO catalogueItemDTO1 = catalogueItemMapper.toDto(catalogueItem);
		log.info("Test - setCatalogueItemActiveYnTest_idNullTest catalogueItem id:" + catalogueItemDTO1.getId());
		Long catalogueItemId = catalogueItemDTO1.getId();
		catalogueItemDTO1.setId(null);
		catlogueItemMockMvc
				.perform(MockMvcRequestBuilders
						.put("/v1/api/catalogue/{catalogueCode}/catalogue-item/{id}/activeYn/{activeYn}",
								catalogueItemDTO1.getCatalogueCode(), catalogueItemId, catalogueItemDTO1.getActiveYn())
						.contentType(MediaType.APPLICATION_JSON)
						.content(RestUtil.convertObjectToJsonBytes(catalogueItemDTO1)))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

		log.info("==================================================================================");
		log.info("Test - setCatalogueItemActiveYnTest_idNullTest End");
	}

	/**
	 * Test get all Error Details from upload file.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 33)
	@Transactional
	public void testGetCatalogueFileUploadErrors() throws Exception {
		log.info("==================================================================================");
		log.info("Test - testGetCatalogueFileUploadErrors Start");
		CatalogueUploadFileDTO catalogueUploadFileDTO1 = catalogueUploadFileDTO;
		catalogueUploadFileDTO1.setId(200L);
		CatalogueUploadFile catalogueUploadFile1 = catalogueUploadFileRepository
				.save(catalogueUploadFileMapper.toEntity(catalogueUploadFileDTO1));
		CatalogueUploadFileDetailsDTO catalogueUploadFileDetailsDTO1 = catalogueUploadFileDetailsDTO;
		catalogueUploadFileDetailsDTO1.setId(null);
		catalogueUploadFileDetailsDTO1.setCatalogueFileId(catalogueUploadFile1.getId());
		catalogueUploadFileDetailsRepository
				.save(catalogueUploadFileDetailsMapper.toEntity(catalogueUploadFileDetailsDTO1));
		String uri = "/v1/api/catalogue-upload/" + catalogueUploadFile1.getId() + "/download-errors";
		catlogueItemMockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(MockMvcResultMatchers.status().isOk());
		log.info("==================================================================================");
		log.info("Test - testGetCatalogueFileUploadErrors End");
	}

	/**
	 * Test get catalogue file upload errors not present.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 34)
	@Transactional
	public void testGetCatalogueFileUploadErrors_notPresent() throws Exception {
		log.info("==================================================================================");
		log.info("Test - testGetCatalogueFileUploadErrors_notPresent Start");
		catalogueUploadFileRepository.deleteAll();
		String uri = "/v1/api/catalogue-upload/1342/download-errors";
		catlogueItemMockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		log.info("==================================================================================");
		log.info("Test - testGetCatalogueFileUploadErrors_notPresent End");
	}

	/**
	 * Test get catalogue file upload errors no data found.
	 *
	 * @throws Exception the exception
	 */
	@Test(priority = 35)
	@Transactional
	public void testGetCatalogueFileUploadErrors_NoDataFound() throws Exception {
		log.info("==================================================================================");
		log.info("Test - testGetCatalogueFileUploadErrors_NoDataFound Start");
		CatalogueUploadFileDTO catalogueUploadFileDTO1 = catalogueUploadFileDTO;
		catalogueUploadFileDTO1.setId(201L);
		CatalogueUploadFile catalogueUploadFile1 = catalogueUploadFileRepository
				.save(catalogueUploadFileMapper.toEntity(catalogueUploadFileDTO1));
		catalogueUploadFileDetailsRepository.deleteAll();
		String uri = "/v1/api/catalogue-upload/" + catalogueUploadFile1.getId() + "/download-errors";
		catlogueItemMockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
		log.info("==================================================================================");
		log.info("Test - testGetCatalogueFileUploadErrors_NoDataFound End");
	}

	/**
	 * Tear down.
	 *
	 * @throws Exception the exception
	 */
	@AfterClass
	public static void tearDown() throws Exception {
		brokerService.stop();
		brokerService.waitUntilStopped();
	}

}
