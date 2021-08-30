package com.dxc.eproc.catalogue.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

import com.dxc.eproc.catalogue.dto.request.ClassMasterDTO;
import com.dxc.eproc.catalogue.mapper.ClassMasterMapper;
import com.dxc.eproc.catalogue.model.ClassMaster;
import com.dxc.eproc.catalogue.model.FamilyMaster;
import com.dxc.eproc.catalogue.repository.ClassRepository;
import com.dxc.eproc.catalogue.services.impl.ClassServiceImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class ClassServiceTest.
 */
public class ClassServiceTest extends PowerMockTestCase {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(ClassServiceTest.class);

	/** The class master. */
	private static ClassMaster classMaster;

	/** The class master DTO. */
	private static ClassMasterDTO classMasterDTO;

	/** The class service. */
	@InjectMocks
	private ClassServiceImpl classService;

	/** The class master mapper. */
	@Mock
	private ClassMasterMapper classMasterMapper;

	/** The class repository. */
	@Mock
	private ClassRepository classRepository;

	/** The family master. */
	private FamilyMaster familyMaster;

	/**
	 * Gets the entity.
	 *
	 * @return the entity
	 */
	@BeforeClass
	public void getEntity() {
		familyMaster = new FamilyMaster();
		familyMaster.setId(1L);
		familyMaster.setCode("testcode");
		familyMaster.setTitle("testTitle");

		classMaster = new ClassMaster();
		classMaster.setId(1L);
		classMaster.setCode("testCode");
		classMaster.setTitle("testTitle");
		classMaster.setFamilyMaster(familyMaster);
		classMasterDTO = new ClassMasterDTO();
		BeanUtils.copyProperties(classMaster, classMasterDTO);
	}

	/**
	 * Gets the class master test.
	 *
	 * @return the class master test
	 * @throws Exception the exception
	 */
	@Test
	public void getClassMasterTest() throws Exception {
		PowerMockito.when(classRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(classMaster));
		PowerMockito.when(classMasterMapper.toDto(classMaster)).thenReturn(classMasterDTO);

		Optional<ClassMasterDTO> fetchedClassMaster = classService.getClassMaster(1L);
		Assert.assertFalse(fetchedClassMaster.isEmpty());
		log.info("getClassMasterTest successful!");
	}

	/**
	 * Gets the class master by code test.
	 *
	 * @return the class master by code test
	 * @throws Exception the exception
	 */
	@Test
	public void getClassMasterByCodeTest() throws Exception {
		PowerMockito.when(classRepository.findByCode("")).thenReturn(Optional.of(classMaster));
		PowerMockito.when(classMasterMapper.toDto(classMaster)).thenReturn(classMasterDTO);

		Optional<ClassMasterDTO> fetchedClassMaster = classService.getClassMasterByCode("");
		Assert.assertFalse(fetchedClassMaster.isEmpty());
		log.info("getClassMasterByCodeTest successful!");
	}

	/**
	 * Gets the all classes test.
	 *
	 * @return the all classes test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllClassesTest() throws Exception {
		List<ClassMaster> classMasterList = new ArrayList<>();
		classMasterList.add(classMaster);
		Page<ClassMaster> page = new PageImpl<>(classMasterList);
		PageRequest pageRequest = PageRequest.of(0, 5);
		PowerMockito.when(classRepository.findByFamilyMaster(pageRequest, familyMaster)).thenReturn(page);
		PowerMockito.when(classMasterMapper.toDto(classMaster)).thenReturn(classMasterDTO);
		Page<ClassMasterDTO> classMasterDTOPage = classService.getAllClasses(pageRequest, familyMaster);
		Assert.assertTrue(classMasterDTOPage.hasContent());
		log.info("getAllClassesTest successful!");
	}
}
