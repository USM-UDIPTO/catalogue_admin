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

import com.dxc.eproc.catalogue.model.CommodityMaster;

@SpringBootTest
@ActiveProfiles("test")
public class CommidityRepositorytest extends AbstractTestNGSpringContextTests {
	private static final Logger log = LoggerFactory.getLogger(CatalogueRepositoryTest.class);

	@Autowired
	CommodityRepository commodityRepository;

	@BeforeClass
	public static void init() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");
		System.setProperty("spring.profiles.active", "test");
	}

	public CommodityMaster getCommodity() {
		CommodityMaster Commodity = new CommodityMaster();
		Commodity.setId(null);
		Commodity.setTitle("testTitle");
		Commodity.setCode("99");
		return Commodity;
	}

	@Test
	public void saveCommidityTest() {
		CommodityMaster commodity = getCommodity();
		commodity = commodityRepository.save(commodity);
		System.out.println(commodity.toString());
		Assert.assertNotNull(commodity.getId());
		log.info("saveCommodityTest successful!");
	}

	@Test
	public void updateCommodityTest() {
		CommodityMaster commodity = getCommodity();
		commodity = commodityRepository.save(commodity);
		String beforeCode = commodity.getCode();
		commodity = commodityRepository.findById(commodity.getId()).get();
		commodity.setCode("updatedCode");
		commodity = commodityRepository.save(commodity);
		String afterCode = commodity.getCode();
		Assert.assertNotEquals(beforeCode, afterCode);
		System.out.println(commodity.toString());
		log.info("updateCommodityTest successful!");
	}

	@Test
	public void getAllCommodityTest() {
		CommodityMaster commodity = getCommodity();
		commodity = commodityRepository.save(commodity);
		List<CommodityMaster> commodities = commodityRepository.findAll();
		System.out.println(commodity.toString());
		Assert.assertNotNull(commodities);
		log.info("getAllcommodityTest successful!");
	}

	@Test
	public void getCommodityByIdTest() {
		CommodityMaster commodity = getCommodity();
		commodity = commodityRepository.save(commodity);
		CommodityMaster foundCommodity = commodityRepository.findById(commodity.getId()).get();
		System.out.println(foundCommodity.toString());
		Assert.assertEquals(commodity.getCode(), foundCommodity.getCode());
		log.info("getCommidityByIdTest successful!");
	}

	@Test

	public void deleteCommidityTest() {
		CommodityMaster commodity = getCommodity();
		commodity = commodityRepository.save(commodity);
		commodityRepository.deleteAll();
		List<CommodityMaster> commodities = commodityRepository.findAll();
		Assert.assertTrue(commodities.isEmpty());
		log.info("deleteCommidityTest successful!");
	}

}
