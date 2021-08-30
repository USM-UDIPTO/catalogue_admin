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

import com.dxc.eproc.catalogue.model.SegmentMaster;

@SpringBootTest
@ActiveProfiles("test")
public class SegmentRepositoryTest extends AbstractTestNGSpringContextTests {

	private static final Logger log = LoggerFactory.getLogger(SegmentRepositoryTest.class);

	@Autowired
	SegmentRepository segmentRepository;

	@BeforeClass
	public static void init() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");
		System.setProperty("spring.profiles.active", "test");
	}

	public SegmentMaster getSegmentMaster() {
		SegmentMaster segmentMaster = new SegmentMaster();
		segmentMaster.setCode("testcode");
		segmentMaster.setTitle("testtitle");
		return segmentMaster;
	}

	@Test
	public void saveSegmentMasterTest() {
		SegmentMaster segmentMaster = getSegmentMaster();
		segmentMaster = segmentRepository.save(segmentMaster);
		System.out.println(segmentMaster.toString());
		Assert.assertNotNull(segmentMaster.getId());
		log.info("saveSegmentMasterTest successful!");
	}

	@Test
	public void updateSegmentMasterTest() {
		SegmentMaster segmentMaster = getSegmentMaster();
		segmentMaster = segmentRepository.save(segmentMaster);
		String beforeCode = segmentMaster.getCode();
		segmentMaster = segmentRepository.findById(segmentMaster.getId()).get();
		segmentMaster.setCode("updatedCode");
		segmentMaster = segmentRepository.save(segmentMaster);
		String afterCode = segmentMaster.getCode();
		Assert.assertNotEquals(beforeCode, afterCode);
		System.out.println(segmentMaster.toString());
		log.info("updateSegmentMasterTest successful!");
	}

	@Test
	public void getAllSegmentMastersTest() {
		SegmentMaster segmentMaster = getSegmentMaster();
		segmentMaster = segmentRepository.save(segmentMaster);
		List<SegmentMaster> segmentMasters = segmentRepository.findAll();
		System.out.println(segmentMasters.toString());
		Assert.assertNotNull(segmentMasters);
		log.info("getAllSegmentMasterTest successful!");
	}

	@Test
	public void getSegmentMasterByIdTest() {
		SegmentMaster segmentMaster = getSegmentMaster();
		segmentMaster = segmentRepository.save(segmentMaster);
		SegmentMaster foundSegmentMaster = segmentRepository.findById(segmentMaster.getId()).get();
		System.out.println(foundSegmentMaster.toString());
		Assert.assertEquals(segmentMaster.getCode(), foundSegmentMaster.getCode());
		log.info("getSegmentMasterByIdTest successful!");
	}

	@Test
	public void deleteSegmentMasterTest() {
		SegmentMaster segmentMaster = getSegmentMaster();
		segmentMaster = segmentRepository.save(segmentMaster);
		segmentRepository.deleteAll();
		List<SegmentMaster> segmentMasters = segmentRepository.findAll();
		Assert.assertTrue(segmentMasters.isEmpty());
		log.info("deleteSegmentMasterTest successful!");
	}
}
