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
import org.springframework.data.domain.Pageable;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dxc.eproc.catalogue.dto.request.SpecificationDTO;
import com.dxc.eproc.catalogue.mapper.SpecificationMapper;
import com.dxc.eproc.catalogue.model.Specification;
import com.dxc.eproc.catalogue.repository.SpecificationRepository;
import com.dxc.eproc.catalogue.services.impl.SpecificationServiceImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class SpecificationServiceTest.
 */
public class SpecificationServiceTest extends PowerMockTestCase {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(SpecificationServiceTest.class);

	/** The Specification info. */
	private static Specification specification;

	/** The SpecificationDTO info. */
	private static SpecificationDTO specificationDTO;

	/** The specification service. */
	@InjectMocks
	private SpecificationServiceImpl specificationService;

	/** The specification mapper. */
	@Mock
	private SpecificationMapper specificationMapper;

	/** The specification repository. */
	@Mock
	private SpecificationRepository specificationRepository;

	/**
	 * Gets the specification.
	 *
	 * @return the specification
	 */
	@BeforeClass
	public void getSpecification() {
		specification = new Specification();
		specification.setSpecName("testName");
		specification.setDeptId(1);
		specification.setDeptName("testDept");

		specificationDTO = new SpecificationDTO();
		BeanUtils.copyProperties(specification, specificationDTO);
	}

	/**
	 * Save or update specification test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void saveOrUpdateSpecificationTest() throws Exception {
		PowerMockito.when(specificationRepository.save(specification)).thenReturn(specification);
		PowerMockito.when(specificationMapper.toDto(specification)).thenReturn(specificationDTO);

		SpecificationDTO savedSpecification = specificationService.saveOrUpdateSpecification(specification);
		Assert.assertFalse(Optional.ofNullable(savedSpecification).isEmpty());
		log.info("saveOrUpdateSpecificationTest successful!");
	}

	/**
	 * Gets the all specifications test.
	 *
	 * @return the all specifications test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllSpecificationsTest() throws Exception {
		List<Specification> specificationList = new ArrayList<>();
		specificationList.add(specification);
		Pageable pageable = PageRequest.of(0, 5);
		Page<Specification> specificationPage = new PageImpl<Specification>(specificationList, pageable,
				specificationList.size());

		PowerMockito.when(specificationRepository.findByDeptIdOrderByLastModifiedTsDesc(Mockito.anyInt(), Mockito.any()))
				.thenReturn(specificationPage);
		PowerMockito.when(specificationMapper.toDto(specification)).thenReturn(specificationDTO);

		Page<SpecificationDTO> specificationDTOPage = specificationService.getAllSpecifications(pageable,
				specification.getDeptId());
		Assert.assertTrue(specificationDTOPage.hasContent());
		log.info("getAllSpecificationsTest successful!");
	}

	/**
	 * Search specifications test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void searchSpecificationsTest() throws Exception {
		List<Specification> specificationList = new ArrayList<>();
		specificationList.add(specification);
		Pageable pageable = PageRequest.of(0, 5);
		Page<Specification> specificationPage = new PageImpl<Specification>(specificationList, pageable,
				specificationList.size());

		PowerMockito.when(specificationRepository.findByDeptIdAndSpecNameContainingOrderByLastModifiedTsDesc(Mockito.anyInt(), Mockito.any(),
				Mockito.any())).thenReturn(specificationPage);
		PowerMockito.when(specificationMapper.toDto(specification)).thenReturn(specificationDTO);

		Page<SpecificationDTO> specificationDTOPage = specificationService.searchSpecifications(pageable,
				specification.getDeptId(), specification.getSpecName());
		Assert.assertTrue(specificationDTOPage.hasContent());
		log.info("searchSpecificationsTest successful!");
	}

	/**
	 * Gets the specification test.
	 *
	 * @return the specification test
	 * @throws Exception the exception
	 */
	@Test
	public void getSpecificationTest() throws Exception {
		PowerMockito.when(specificationRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(specification));
		PowerMockito.when(specificationMapper.toDto(specification)).thenReturn(specificationDTO);

		Optional<SpecificationDTO> fetchedSpecification = specificationService.getSpecification(1L);
		Assert.assertFalse(fetchedSpecification.isEmpty());
		log.info("getSpecificationTest successful!");
	}

	/**
	 * Specification name check for save success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void specificationNameCheckForSaveSuccessTest() throws Exception {
		PowerMockito.when(specificationRepository.findByDeptIdAndSpecName(Mockito.anyInt(), Mockito.anyString()))
				.thenReturn(null);
		Assert.assertFalse(specificationService.specificationNameCheck(specificationDTO));
	}

	/**
	 * Specification name check for save fail test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void specificationNameCheckForSaveFailTest() throws Exception {
		PowerMockito.when(specificationRepository.findByDeptIdAndSpecName(Mockito.anyInt(), Mockito.anyString()))
				.thenReturn(specification);
		Assert.assertTrue(specificationService.specificationNameCheck(specificationDTO));
	}

	/**
	 * Specification name check for update fail test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void specificationNameCheckForUpdateFailTest() throws Exception {
		specificationDTO.setId(1L);
		specification.setId(2L);

		PowerMockito.when(specificationRepository.findByDeptIdAndSpecName(Mockito.anyInt(), Mockito.anyString()))
				.thenReturn(specification);
		Assert.assertTrue(specificationService.specificationNameCheck(specificationDTO));
	}

	/**
	 * Delete specification test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteSpecificationTest() throws Exception {
		specificationService.deleteSpecification(1L);
	}
}
