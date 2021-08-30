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

import com.dxc.eproc.catalogue.model.FamilyMaster;

@SpringBootTest
@ActiveProfiles("test")
public class FamilyRepositoryTest extends AbstractTestNGSpringContextTests {

	private static final Logger log = LoggerFactory.getLogger(FamilyRepositoryTest.class);

	@Autowired
	SegmentRepository segmentRepository;

	@Autowired
	FamilyRepository familyRepository;

	@BeforeClass
	public static void init() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");
		System.setProperty("spring.profiles.active", "test");
	}

	public FamilyMaster getFamily() {
		FamilyMaster family = new FamilyMaster();
		family.setId(null);
		family.setCode("test code"); // to be added in db
		family.setTitle("testTitle");
		return family;
	}

	@Test
	public void saveFamilyTest() {
		FamilyMaster family = getFamily();
		family = familyRepository.save(family);
		System.out.println(family.toString());
		Assert.assertNotNull(family.getId());
		log.info("saveFamilyTest successful"); // log directory
	}

	@Test
	public void updateFamilyTest() {
		FamilyMaster family = getFamily();
		family = familyRepository.save(family);
		String beforeCode = family.getCode();
		family = familyRepository.findById(family.getId()).get();
		family.setCode("updated code");
		family = familyRepository.save(family);

		String afterCode = family.getCode();
		Assert.assertNotEquals(beforeCode, afterCode);
		System.out.println(family.toString());
		log.info("update FamilyTest successful"); // log directory
	}

	@Test
	public void getFamilyTest() {
		FamilyMaster family = getFamily();
		family = familyRepository.save(family);
		FamilyMaster foundFamily = familyRepository.findById(family.getId()).get();
		System.out.println(foundFamily.toString());
		Assert.assertEquals(family.getCode(), foundFamily.getCode());
		log.info("getFamilyTest successful!");
	}

	@Test
	public void getAllFamiliesForSegmentTest() {
		FamilyMaster family = getFamily();
		family = familyRepository.save(family);
		List<FamilyMaster> families = familyRepository.findAll();
		System.out.println(family.toString());
		Assert.assertNotNull(families);
		log.info("getAllFamiliesTest successful!");
	}

	@Test
	public void deleteFamilyTest() {
		FamilyMaster family = getFamily();
		family = familyRepository.save(family);
		familyRepository.deleteAll();
		List<FamilyMaster> families = familyRepository.findAll();
		Assert.assertTrue(families.isEmpty());
		log.info("delete FamiliesTest successful!");
	}
}
