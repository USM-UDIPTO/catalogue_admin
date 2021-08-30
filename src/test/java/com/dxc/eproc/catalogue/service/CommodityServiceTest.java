package com.dxc.eproc.catalogue.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
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

import com.dxc.eproc.catalogue.dto.request.CommodityMasterDTO;
import com.dxc.eproc.catalogue.mapper.CommodityMasterMapper;
import com.dxc.eproc.catalogue.model.ClassMaster;
import com.dxc.eproc.catalogue.model.CommodityMaster;
import com.dxc.eproc.catalogue.repository.CommodityRepository;
import com.dxc.eproc.catalogue.services.impl.CommodityServiceImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class CommodityServiceTest.
 */
public class CommodityServiceTest extends PowerMockTestCase {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(CatalogueServiceTest.class);

	/** The commodity. */
	private static CommodityMaster commodity;

	/** The commodity DTO. */
	private static CommodityMasterDTO commodityDTO;

	/** The commodity service. */
	@InjectMocks
	private CommodityServiceImpl commodityService;

	/** The commodity mapper. */
	@Mock
	private CommodityMasterMapper commodityMapper;

	/** The commodity repository. */
	@Mock
	private CommodityRepository commodityRepository;

	/** The class master. */
	private ClassMaster classMaster;

	/**
	 * Gets the commodity.
	 *
	 * @return the commodity
	 */
	@BeforeClass
	public void getCommodity() {
		commodity = new CommodityMaster();
		classMaster = new ClassMaster();
		commodity.setId(1L);
		commodity.setCode("testCode");
		commodity.setTitle("testName");
		classMaster.setId(1L);
		commodity.setClassMaster(classMaster);
		commodityDTO = new CommodityMasterDTO();
		BeanUtils.copyProperties(commodity, commodityDTO);
	}

	/**
	 * Gets the commodity sucess test.
	 *
	 * @return the commodity sucess test
	 * @throws Exception the exception
	 */
	@Test
	public void getCommodityMasterTest() throws Exception {
		PowerMockito.when(commodityRepository.findById(1L)).thenReturn(Optional.of(commodity));
		PowerMockito.when(commodityMapper.toDto(commodity)).thenReturn(commodityDTO);
		Optional<CommodityMasterDTO> fetchedCommodity = commodityService.getCommodityMaster(1L);
		Assert.assertFalse(fetchedCommodity.isEmpty());
		log.info("getCommodityMasterTest successful!");
	}

	/**
	 * Gets the commodity master by code test.
	 *
	 * @return the commodity master by code test
	 * @throws Exception the exception
	 */
	@Test
	public void getCommodityMasterByCodeTest() throws Exception {
		PowerMockito.when(commodityRepository.findByCode("")).thenReturn(Optional.of(commodity));
		PowerMockito.when(commodityMapper.toDto(commodity)).thenReturn(commodityDTO);
		Optional<CommodityMasterDTO> fetchedCommodity = commodityService.getCommodityMasterByCode("");
		Assert.assertFalse(fetchedCommodity.isEmpty());
		log.info("getCommodityMasterByCodeTest successful!");
	}

	/**
	 * Gets the all commodities test.
	 *
	 * @return the all commodities test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllCommoditiesTest() throws Exception {
		List<CommodityMaster> commodityList = new ArrayList<>();
		commodityList.add(commodity);
		Page<CommodityMaster> page = new PageImpl<>(commodityList);
		PageRequest pageRequest = PageRequest.of(0, 5);
		PowerMockito.when(commodityRepository.findByClassMaster(pageRequest, classMaster)).thenReturn(page);
		PowerMockito.when(commodityMapper.toDto(commodity)).thenReturn(commodityDTO);
		Page<CommodityMasterDTO> commodityDTOPage = commodityService.getAllCommodities(pageRequest, classMaster);
		Assert.assertTrue(commodityDTOPage.hasContent());
		log.info("getAllCommodityTest successful!");
	}
}
