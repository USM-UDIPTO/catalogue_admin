package com.dxc.eproc.catalogue.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.testng.PowerMockTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.catalogue.dto.request.CatalogueItemDTO;
import com.dxc.eproc.catalogue.dto.request.CatalogueItemSearchDTO;
import com.dxc.eproc.catalogue.mapper.CatalogueItemMapperImpl;
import com.dxc.eproc.catalogue.model.CatalogueItem;
import com.dxc.eproc.catalogue.model.CategoryDetails;
import com.dxc.eproc.catalogue.repository.CatalogueItemRepository;
import com.dxc.eproc.catalogue.services.impl.CatalogueItemServiceImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class CatalogueItemServiceImplTest.
 */
public class CatalogueItemServiceTest extends PowerMockTestCase {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(CatalogueItemServiceTest.class);

	/** The catalogue item mapper. */
	@Mock
	private CatalogueItemMapperImpl catalogueItemMapper;

	/** The catalogue item repository. */
	@Mock
	private CatalogueItemRepository catalogueItemRepository;

	/** The catalogue item service. */
	@InjectMocks
	private CatalogueItemServiceImpl catalogueItemService;

	/**
	 * Inits the.
	 */
	@BeforeClass
	public void init() {
		log.info("==========================================================================");
		log.info("This is executed before once Per Test Class - CatalogueItemServiceImplTest: init");
	}

	/**
	 * Sets the up.
	 */
	@BeforeMethod
	public void setUp() {
		log.info("==========================================================================");
		log.info("This is executed before each Test - CatalogueItemServiceImplTest: setUp");

		catalogueItemService = new CatalogueItemServiceImpl(catalogueItemRepository, catalogueItemMapper);
	}

	/**
	 * Test save.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testSave() throws Exception {

		log.info("==========================================================================");
		log.info("TestSave - Start");
		CatalogueItem item = createCatalogueItem();
		CatalogueItemMapperImpl catalogueItemMapper1 = new CatalogueItemMapperImpl();
		CatalogueItemDTO catalogueItemDTO = catalogueItemMapper1.toDto(item);
		PowerMockito.when(catalogueItemRepository.save(item)).thenReturn(item);
		PowerMockito.when(catalogueItemRepository.findByItemCode(catalogueItemDTO.getItemCode())).thenReturn(null);
		PowerMockito.when(catalogueItemMapper.toEntity(catalogueItemDTO)).thenReturn(item);
		PowerMockito.when(catalogueItemMapper.toDto(item)).thenReturn(catalogueItemDTO);
		CatalogueItemDTO catalogueItemDTO1 = catalogueItemService.save(catalogueItemDTO);
		log.info("Response data:saveTest: " + catalogueItemDTO1);

		Assert.assertEquals(catalogueItemDTO1.getId(), catalogueItemDTO.getId());
		Assert.assertEquals(catalogueItemDTO1.getCatalogueCode(), catalogueItemDTO.getCatalogueCode());

		log.info("TestSave - End");
		log.info("==========================================================================");

	}

	/**
	 * Creates the catalogue item.
	 *
	 * @return the catalogue item
	 */
	private CatalogueItem createCatalogueItem() {
		CatalogueItem catalogueItem = new CatalogueItem();
		catalogueItem.setId(100L);
		catalogueItem.setCatalogueCode("01");
		catalogueItem.setCatalogueName("KPWD_CAT");
		catalogueItem.setItemCode("item01");
		catalogueItem.setItemName("ItemName");
		catalogueItem.setDeptId(100);
		catalogueItem.setDeptName("KPWD");
		Map<String, String> specifications = new HashMap<>();
		specifications.put("Color", "Red");
		specifications.put("Size", "10");
		catalogueItem.setSpecifications(specifications);

		CategoryDetails cat1 = new CategoryDetails();
		cat1.setCategoryCode("cat_code1");
		cat1.setCategoryName("cat_name1");
		CategoryDetails cat2 = new CategoryDetails();
		cat1.setCategoryCode("cat_code2");
		cat1.setCategoryName("cat_name2");
		List<CategoryDetails> categoryDetailsList = new ArrayList<>();
		categoryDetailsList.add(cat1);
		categoryDetailsList.add(cat2);
		catalogueItem.setCategories(categoryDetailsList);
		return catalogueItem;
	}

	/**
	 * Test check item code exist true.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testCheckItemCodeExist_True() throws Exception {
		log.info("==========================================================================");
		log.info("testCheckItemCodeExist_True - Start");
		CatalogueItem item = createCatalogueItem();
		CatalogueItemMapperImpl catalogueItemMapper1 = new CatalogueItemMapperImpl();
		CatalogueItemDTO catalogueItemDTO = catalogueItemMapper1.toDto(item);
		PowerMockito.when(catalogueItemRepository.findByItemCode(catalogueItemDTO.getItemCode()))
				.thenReturn(Optional.of(item));
		PowerMockito.when(catalogueItemMapper.toDto(item)).thenReturn(catalogueItemDTO);
		boolean exist = catalogueItemService.checkItemCodeExist(catalogueItemDTO.getItemCode()).isPresent();
		log.info("Is Item Exists - " + exist);
		Assert.assertEquals(exist, true);

		log.info("testCheckItemCodeExist_True - End");
		log.info("==========================================================================");
	}

	/**
	 * Test check item code exist false.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testCheckItemCodeExist_False() throws Exception {
		log.info("==========================================================================");
		log.info("testCheckItemCodeExist_False - Start");
		CatalogueItem item = createCatalogueItem();
		CatalogueItemMapperImpl catalogueItemMapper1 = new CatalogueItemMapperImpl();
		CatalogueItemDTO catalogueItemDTO = catalogueItemMapper1.toDto(item);
		PowerMockito.when(catalogueItemRepository.findByItemCode(catalogueItemDTO.getItemCode()))
				.thenReturn(Optional.empty());
		boolean exist = catalogueItemService.checkItemCodeExist(catalogueItemDTO.getItemCode()).isPresent();
		log.info("Is Item Exists for the itemcode - " + exist);
		Assert.assertEquals(exist, false);

		log.info("testCheckItemCodeExist_False - End");
		log.info("==========================================================================");
	}

	/**
	 * Test check item name exist true.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testCheckItemNameExist_True() throws Exception {
		log.info("==========================================================================");
		log.info("testCheckItemNameExist - Start");
		CatalogueItem item = createCatalogueItem();
		CatalogueItemMapperImpl catalogueItemMapper1 = new CatalogueItemMapperImpl();
		CatalogueItemDTO catalogueItemDTO = catalogueItemMapper1.toDto(item);
		PowerMockito.when(catalogueItemRepository.findByItemName(catalogueItemDTO.getItemName()))
				.thenReturn(Optional.of(item));
		PowerMockito.when(catalogueItemMapper.toDto(item)).thenReturn(catalogueItemDTO);
		boolean exist = catalogueItemService.checkItemNameExist(catalogueItemDTO.getItemName()).isPresent();
		log.info("Is Item Exists for the name - " + exist);
		Assert.assertEquals(exist, true);

		log.info("testCheckItemNameExist - End");
		log.info("==========================================================================");
	}

	/**
	 * Test check item name exist false.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testCheckItemNameExist_False() throws Exception {
		log.info("==========================================================================");
		log.info("testCheckItemNameExist - Start");
		CatalogueItem item = createCatalogueItem();
		CatalogueItemMapperImpl catalogueItemMapper1 = new CatalogueItemMapperImpl();
		CatalogueItemDTO catalogueItemDTO = catalogueItemMapper1.toDto(item);
		item.setItemCode("ItemCode_01");
		PowerMockito.when(catalogueItemRepository.findByItemName(catalogueItemDTO.getItemName()))
				.thenReturn(Optional.empty());
		boolean exist = catalogueItemService.checkItemNameExist(catalogueItemDTO.getItemName()).isPresent();
		log.info("Is Item Exists for the name - " + exist);
		Assert.assertEquals(exist, false);

		log.info("testCheckItemNameExist - End");
		log.info("==========================================================================");
	}

	/**
	 * Test get.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGet() throws Exception {
		log.info("==========================================================================");
		log.info("testGet - Start");
		CatalogueItem item = createCatalogueItem();
		CatalogueItemMapperImpl catalogueItemMapper1 = new CatalogueItemMapperImpl();
		CatalogueItemDTO catalogueItemDTO = catalogueItemMapper1.toDto(item);
		PowerMockito.when(catalogueItemRepository.findByIdAndCatalogueCode(catalogueItemDTO.getId(),
				catalogueItemDTO.getCatalogueCode())).thenReturn(Optional.of(item));
		PowerMockito.when(catalogueItemMapper.toDto(item)).thenReturn(catalogueItemDTO);
		CatalogueItemDTO catalogueItemDTO1 = catalogueItemService
				.get(catalogueItemDTO.getId(), catalogueItemDTO.getCatalogueCode()).get();

		log.info("Response data:testGet: " + catalogueItemDTO1);

		Assert.assertEquals(catalogueItemDTO1.getId(), catalogueItemDTO.getId());
		Assert.assertEquals(catalogueItemDTO1.getCatalogueCode(), catalogueItemDTO.getCatalogueCode());

		log.info("testGet - End");
		log.info("==========================================================================");
	}

	/**
	 * Test delete.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testDelete() throws Exception {
		log.info("==========================================================================");
		log.info("testDelete - Start");
		CatalogueItem item = createCatalogueItem();
		CatalogueItemMapperImpl catalogueItemMapper1 = new CatalogueItemMapperImpl();
		CatalogueItemDTO catalogueItemDTO = catalogueItemMapper1.toDto(item);
		catalogueItemService.delete(catalogueItemDTO.getId());
		log.info("testDelete - End");
		log.info("==========================================================================");
	}

	/**
	 * Test search catalogue items.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testSearchCatalogueItems() throws Exception {
		log.info("==========================================================================");
		log.info("testSearchCatalogueItems - Start");
		CatalogueItemMapperImpl catalogueItemMapper1 = new CatalogueItemMapperImpl();
		PageRequest pageable = PageRequest.of(0, 1);
		CatalogueItemSearchDTO catalogueItemSearchDTO = new CatalogueItemSearchDTO();
		CatalogueItem item1 = createCatalogueItem();
		CatalogueItem item2 = createCatalogueItem();
		item2.setId(200L);
		List<CatalogueItemDTO> catalogueItemDtoList = new ArrayList<>();
		catalogueItemDtoList.add(catalogueItemMapper1.toDto(item1));
		catalogueItemDtoList.add(catalogueItemMapper1.toDto(item2));
		Page<CatalogueItemDTO> catalogueItems = new PageImpl<CatalogueItemDTO>(catalogueItemDtoList, pageable, 2);
		PowerMockito.when(catalogueItemRepository.searchCatalogueItemQueryDSL(pageable, catalogueItemSearchDTO))
				.thenReturn(catalogueItems);
		Page<CatalogueItemDTO> catalogueItemFromService = catalogueItemService.searchCatalogueItems(pageable,
				catalogueItemSearchDTO);
		Assert.assertEquals(catalogueItemFromService.getTotalPages(), 2);
		Assert.assertEquals(catalogueItemFromService.getTotalElements(), 2);
		Assert.assertEquals(catalogueItemFromService.getNumber(), 0);
		log.info("testSearchCatalogueItems - End");
		log.info("==========================================================================");
	}

	/**
	 * Test get catalogueitems by catalogue code.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetCatalogueitemsByCatalogueCode() throws Exception {
		log.info("==========================================================================");
		log.info("testGetCatalogueitemsByCatalogueCode - Start");
		List<CatalogueItem> list = new ArrayList<>();
		PowerMockito.when(catalogueItemRepository.findByCatalogueCodeOrderByLastModifiedTsDesc(Mockito.anyString())).thenReturn(list);
		list = catalogueItemService.getCatalogueitemsByCatalogueCode(Mockito.anyString());
		Assert.assertNotNull(list);
		log.info("testGetCatalogueitemsByCatalogueCode - End");
		log.info("==========================================================================");
	}

	/**
	 * Test get item by code and catalogue code.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetItemByCodeAndCatalogueCode() throws Exception {
		log.info("==========================================================================");
		log.info("testGetItemByCodeAndCatalogueCode - Start");
		CatalogueItem catalogueItem = createCatalogueItem();
		PowerMockito
				.when(catalogueItemRepository.findByItemCodeAndCatalogueCode(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(Optional.of(catalogueItem));
		CatalogueItemMapperImpl catalogueItemMapper1 = new CatalogueItemMapperImpl();
		CatalogueItemDTO catalogueItemDTO = catalogueItemMapper1.toDto(catalogueItem);
		PowerMockito.when(catalogueItemMapper.toDto(catalogueItem)).thenReturn(catalogueItemDTO);
		Optional<CatalogueItemDTO> opt = catalogueItemService.getItemByCodeAndCatalogueCode(Mockito.anyString(),
				Mockito.anyString());
		Assert.assertNotNull(opt);
		log.info("testGetItemByCodeAndCatalogueCode - End");
		log.info("==========================================================================");
	}

	/**
	 * Test get catalogue Id and CatalogueCode.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetByIdAndCatalogueCode() throws Exception {
		log.info("==========================================================================");
		log.info("testGetByIdAndCatalogueCode - Start");
		CatalogueItem catalogueItem = createCatalogueItem();
		PowerMockito.when(catalogueItemRepository.findByIdAndCatalogueCode(Mockito.anyLong(), Mockito.anyString()))
				.thenReturn(Optional.of(catalogueItem));
		CatalogueItemMapperImpl catalogueItemMapper1 = new CatalogueItemMapperImpl();
		CatalogueItemDTO catalogueItemDTO = catalogueItemMapper1.toDto(catalogueItem);
		PowerMockito.when(catalogueItemMapper.toDto(catalogueItem)).thenReturn(catalogueItemDTO);
		Optional<CatalogueItemDTO> opt = catalogueItemService.get(Mockito.anyLong(), Mockito.anyString());
		Assert.assertNotNull(opt);
		log.info("testGetByIdAndCatalogueCode - End");
		log.info("==========================================================================");
	}
}
