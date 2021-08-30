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

import com.dxc.eproc.catalogue.dto.request.FamilyMasterDTO;
import com.dxc.eproc.catalogue.mapper.FamilyMasterMapper;
import com.dxc.eproc.catalogue.model.FamilyMaster;
import com.dxc.eproc.catalogue.model.SegmentMaster;
import com.dxc.eproc.catalogue.repository.FamilyRepository;
import com.dxc.eproc.catalogue.repository.SegmentRepository;
import com.dxc.eproc.catalogue.services.impl.FamilyServiceImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class FamilyServiceTest.
 */
public class FamilyServiceTest extends PowerMockTestCase {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(FamilyServiceTest.class);

	/** The segment. */
	private static SegmentMaster segment;

	/** The family. */
	private static FamilyMaster family;

	/** The family DTO. */
	private static FamilyMasterDTO familyDTO;

	/** The family service. */
	@InjectMocks
	private FamilyServiceImpl familyService;

	/** The family mapper. */
	@Mock
	private FamilyMasterMapper familyMapper;

	/** The family repository. */
	@Mock
	private FamilyRepository familyRepository;

	/** The segment repository. */
	@Mock
	private SegmentRepository segmentRepository;

	/**
	 * Gets the entity.
	 *
	 * @return the entity
	 */
	@BeforeClass
	public void getEntity() {
		segment = new SegmentMaster();
		segment.setId(1L);
		segment.setCode("testcode");
		segment.setTitle("testTitle");
		segment.setFamilies(new ArrayList<FamilyMaster>());

		family = new FamilyMaster();
		family.setId(1L);
		family.setCode("testCode");
		family.setTitle("testname");
		family.setSegmentMaster(segment);
		familyDTO = new FamilyMasterDTO();
		BeanUtils.copyProperties(family, familyDTO);
	}

	/**
	 * Gets the family test.
	 *
	 * @return the family test
	 * @throws Exception the exception
	 */
	@Test
	public void getFamilyMasterTest() throws Exception {
		PowerMockito.when(familyRepository.findById(family.getId())).thenReturn(Optional.of(family));
		PowerMockito.when(familyMapper.toDto(family)).thenReturn(familyDTO);

		Optional<FamilyMasterDTO> fetchedFamily = familyService.getFamilyMaster(family.getId());
		Assert.assertFalse(Optional.ofNullable(fetchedFamily).isEmpty());
		log.info("getFamilyMasterTest successful!");
	}

	/**
	 * Gets the family master by code test.
	 *
	 * @return the family master by code test
	 * @throws Exception the exception
	 */
	@Test
	public void getFamilyMasterByCodeTest() throws Exception {
		PowerMockito.when(familyRepository.findByCode("")).thenReturn(Optional.of(family));
		PowerMockito.when(familyMapper.toDto(family)).thenReturn(familyDTO);

		Optional<FamilyMasterDTO> fetchedFamily = familyService.getFamilyMasterByCode("");
		Assert.assertFalse(Optional.ofNullable(fetchedFamily).isEmpty());
		log.info("getFamilyMasterByCodeTest successful!");
	}
	/**
	 * Gets the families for segment test.
	 *
	 * @return the families for segment test
	 * @throws Exception the exception
	 */
	@Test
	public void getFamiliesForSegmentTest() throws Exception {
		List<FamilyMaster> familyList = new ArrayList<>();
		familyList.add(family);
		Page<FamilyMaster> page = new PageImpl<>(familyList);
		PageRequest pageRequest = PageRequest.of(0, 5);

		PowerMockito.when(familyRepository.findBysegmentMaster(pageRequest, segment)).thenReturn(page);
		PowerMockito.when(familyMapper.toDto(family)).thenReturn(familyDTO);

		Page<FamilyMasterDTO> families = familyService.getAllFamilies(pageRequest, segment);

		Assert.assertTrue(families.hasContent());
		log.info("getFamiliesForSegmentTest successful!");
	}
}
