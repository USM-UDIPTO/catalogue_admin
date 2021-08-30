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

import com.dxc.eproc.catalogue.model.ClassMaster;

@SpringBootTest
@ActiveProfiles("test")
public class ClassRepositoryTest extends AbstractTestNGSpringContextTests {

	private static final Logger log = LoggerFactory.getLogger(ClassRepositoryTest.class);

	@Autowired
	ClassRepository classRepository;

	@BeforeClass
	public static void init() {
		log.info("==================================================================================");
		log.info("This is executed before once Per Test Class - init");
		System.setProperty("spring.profiles.active", "test");
	}

	public ClassMaster getClassMaster() {
		ClassMaster classMaster = new ClassMaster();
		classMaster.setCode("testCode");
		classMaster.setTitle("testTitle");
		classMaster.getId();
		return classMaster;
	}

	@Test
	public void getAllClassesTest() {
		ClassMaster classMaster = getClassMaster();
		classMaster = classRepository.save(classMaster);
		List<ClassMaster> classes = classRepository.findAll();
		System.out.println(classes.toString());
		Assert.assertNotNull(classes);
		log.info("getAllClassesTest successful!");
	}

	@Test
	public void getClassMasterByIdTest() {
		ClassMaster classMaster = getClassMaster();
		classMaster = classRepository.save(classMaster);
		ClassMaster foundClassMaster = classRepository.findById(classMaster.getId()).get();
		Assert.assertEquals(classMaster.getCode(), foundClassMaster.getCode());
		log.info("getClassMasterByIdTest successful!");
	}

	@Test
	public void saveClassMasterTest() {
		ClassMaster classMaster = getClassMaster();
		classMaster = classRepository.save(classMaster);
		Assert.assertNotNull(classMaster.getId());
		log.info("saveClassMasterTest successful!");
	}

	@Test
	public void updateClassMasterTest() {
		ClassMaster classMaster = getClassMaster();
		classMaster = classRepository.save(classMaster);
		String beforeCode = classMaster.getCode();
		classMaster = classRepository.findById(classMaster.getId()).get();
		classMaster.setCode("updatedCode");
		classMaster = classRepository.save(classMaster);
		String afterCode = classMaster.getCode();
		Assert.assertNotEquals(beforeCode, afterCode);
		log.info("updateClassMasterTest successful!");
	}

	@Test
	public void deleteClassMasterTest() {
		ClassMaster classMaster = getClassMaster();
		classMaster = classRepository.save(classMaster);
		classRepository.deleteAll();
		List<ClassMaster> classes = classRepository.findAll();
		Assert.assertTrue(classes.isEmpty());
		log.info("deleteClassMasterTest successful!");
	}

}
