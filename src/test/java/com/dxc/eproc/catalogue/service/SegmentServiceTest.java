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

import com.dxc.eproc.catalogue.dto.request.SegmentMasterDTO;
import com.dxc.eproc.catalogue.mapper.SegmentMasterMapper;
import com.dxc.eproc.catalogue.model.FamilyMaster;
import com.dxc.eproc.catalogue.model.SegmentMaster;
import com.dxc.eproc.catalogue.repository.SegmentRepository;
import com.dxc.eproc.catalogue.services.impl.SegmentServiceImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class SegmentServiceTest.
 */
public class SegmentServiceTest extends PowerMockTestCase {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(SegmentServiceTest.class);

	/** The segment master. */
	private static SegmentMaster segmentMaster;

	/** The segment master dto. */
	private static SegmentMasterDTO segmentMasterDto;

	/** The segment service. */
	@InjectMocks
	private SegmentServiceImpl segmentService;

	/** The segment repository. */
	@Mock
	private SegmentRepository segmentRepository;

	/** The segment master mapper. */
	@Mock
	private SegmentMasterMapper segmentMasterMapper;

	/**
	 * Gets the segment master.
	 *
	 * @return the segment master
	 */
	@BeforeClass
	public void getSegmentMaster() {
		segmentMaster = new SegmentMaster();
		segmentMaster.setId(1L);
		segmentMaster.setCode("testCode");
		segmentMaster.setTitle("testtitle");
		segmentMaster.setFamilies(new ArrayList<FamilyMaster>());
		segmentMasterDto = new SegmentMasterDTO();
		BeanUtils.copyProperties(segmentMaster, segmentMasterDto);
	}

	/**
	 * Gets the segment master test.
	 *
	 * @return the segment master test
	 * @throws Exception the exception
	 */
	@Test
	public void getSegmentMasterTest() throws Exception {
		PowerMockito.when(segmentRepository.findById(1L)).thenReturn(Optional.of(segmentMaster));
		PowerMockito.when(segmentMasterMapper.toDto(segmentMaster)).thenReturn(segmentMasterDto);

		Optional<SegmentMasterDTO> fetchedSegment = segmentService.getSegmentMaster(1L);
		Assert.assertFalse(fetchedSegment.isEmpty());
		log.info("getSegmentMasterTest successful!");
	}

	/**
	 * Gets the segment master by code test.
	 *
	 * @return the segment master by code test
	 * @throws Exception the exception
	 */
	@Test
	public void getSegmentMasterByCodeTest() throws Exception {
		PowerMockito.when(segmentRepository.findByCode("a")).thenReturn(Optional.of(segmentMaster));
		PowerMockito.when(segmentMasterMapper.toDto(segmentMaster)).thenReturn(segmentMasterDto);

		Optional<SegmentMasterDTO> fetchedSegment = segmentService.getSegmentMasterByCode("a");
		Assert.assertFalse(fetchedSegment.isEmpty());
		log.info("getSegmentMasterByCodeTest successful!");
	}

	/**
	 * Gets the all segments test.
	 *
	 * @return the all segments test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllSegmentsTest() throws Exception {
		List<SegmentMaster> segmentMasterList = new ArrayList<>();
		segmentMasterList.add(segmentMaster);
		Page<SegmentMaster> page = new PageImpl<>(segmentMasterList);
		PageRequest pageRequest = PageRequest.of(0, 5);

		PowerMockito.when(segmentRepository.findAll(pageRequest)).thenReturn(page);
		PowerMockito.when(segmentMasterMapper.toDto(segmentMaster)).thenReturn(segmentMasterDto);

		Page<SegmentMasterDTO> segmentDTOPage = segmentService.getAllSegments(pageRequest);

		Assert.assertTrue(segmentDTOPage.hasContent());

		log.info("getAllSegmentMastersTest successful!");
	}

}
