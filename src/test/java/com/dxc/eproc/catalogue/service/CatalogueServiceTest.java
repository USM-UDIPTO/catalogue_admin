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

import com.dxc.eproc.catalogue.dto.request.CatalogueDTO;
import com.dxc.eproc.catalogue.mapper.CatalogueMapper;
import com.dxc.eproc.catalogue.model.Catalogue;
import com.dxc.eproc.catalogue.model.Category;
import com.dxc.eproc.catalogue.repository.CatalogueRepository;
import com.dxc.eproc.catalogue.services.impl.CatalogueServiceImpl;
import com.querydsl.core.BooleanBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Class CatalogueServiceTest.
 */
public class CatalogueServiceTest extends PowerMockTestCase {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(CatalogueServiceTest.class);

	/** The Catalogue Info. */
	private static Catalogue catalogue;

	/** The CatalogueDTO Info. */
	private static CatalogueDTO catalogueDTO;

	/** The catalogue service. */
	@InjectMocks
	private CatalogueServiceImpl catalogueService;

	/** The catalogue mapper. */
	@Mock
	private CatalogueMapper catalogueMapper;

	/** The catalogue repository. */
	@Mock
	private CatalogueRepository catalogueRepository;

	/**
	 * Gets the catalogue.
	 *
	 * @return the catalogue
	 */
	@BeforeClass
	public void getCatalogue() {
		catalogue = new Catalogue();
		catalogue.setId(1L);
		catalogue.setCatalogueCode("testCode");
		catalogue.setCatalogueName("testName");
		catalogue.setDeptId(1);
		catalogue.setDeptName("testDept");

		catalogue.setCategories(new ArrayList<Category>());

		catalogueDTO = new CatalogueDTO();
		BeanUtils.copyProperties(catalogue, catalogueDTO);
	}

	/**
	 * Save or update catalogue test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void saveOrUpdateCatalogueTest() throws Exception {
		PowerMockito.when(catalogueRepository.save(catalogue)).thenReturn(catalogue);
		PowerMockito.when(catalogueMapper.toDto(catalogue)).thenReturn(catalogueDTO);

		CatalogueDTO savedCatalogue = catalogueService.saveOrUpdateCatalogue(catalogue);
		Assert.assertFalse(Optional.ofNullable(savedCatalogue).isEmpty());
		log.info("saveOrUpdateCatalogueTest successful!");
	}

	/**
	 * Gets the all catalogues test.
	 *
	 * @return the all catalogues test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllCataloguesTest() throws Exception {
		List<Catalogue> catalogueList = new ArrayList<>();
		catalogueList.add(catalogue);
		Page<Catalogue> page = new PageImpl<>(catalogueList);
		PageRequest pageRequest = PageRequest.of(0, 5);
		PowerMockito.when(catalogueRepository.findByDeptIdOrderByLastModifiedTsDesc(pageRequest, 1)).thenReturn(page);
		PowerMockito.when(catalogueMapper.toDto(catalogue)).thenReturn(catalogueDTO);

		Page<CatalogueDTO> catalogueDTOPage = catalogueService.getAllCataloguesPagination(pageRequest, catalogue.getDeptId(),
				null);
		Assert.assertTrue(catalogueDTOPage.hasContent());
		log.info("getAllCataloguesTest successful!");
	}

	/**
	 * Gets all active catalogues test.
	 *
	 * @return the all active catalogues test
	 * @throws Exception the exception
	 */
	@Test
	public void getAllActiveCataloguesTest() throws Exception {
		List<Catalogue> catalogueList = new ArrayList<>();
		catalogueList.add(catalogue);
		Page<Catalogue> page = new PageImpl<>(catalogueList);
		PageRequest pageRequest = PageRequest.of(0, 5);
		PowerMockito.when(catalogueRepository.findByDeptIdAndActiveYnOrderByLastModifiedTsDesc(pageRequest, 1, true))
				.thenReturn(page);
		PowerMockito.when(catalogueMapper.toDto(catalogue)).thenReturn(catalogueDTO);

		Page<CatalogueDTO> catalogueDTOPage = catalogueService.getAllCataloguesPagination(pageRequest, catalogue.getDeptId(),
				true);
		Assert.assertTrue(catalogueDTOPage.hasContent());
		log.info("getAllCataloguesTest successful!");
	}

	/**
	 * Search catalogues test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void searchCataloguesTest() throws Exception {
		List<Catalogue> catalogueList = new ArrayList<>();
		catalogueList.add(catalogue);
		Page<Catalogue> page = new PageImpl<>(catalogueList);
		PageRequest pageRequest = PageRequest.of(0, 5);
		BooleanBuilder builder = null;
		PowerMockito.when(catalogueRepository.findAll(builder, pageRequest)).thenReturn(page);
		PowerMockito.when(catalogueMapper.toDto(catalogue)).thenReturn(catalogueDTO);
		Page<CatalogueDTO> catalogueDTOPage = catalogueService.searchCatalogues(builder, pageRequest);
		Assert.assertTrue(catalogueDTOPage.hasContent());
		log.info("searchCataloguesTest successful!");
	}

	/**
	 * Gets the catalogue test.
	 *
	 * @return the catalogue test
	 * @throws Exception the exception
	 */
	@Test
	public void getCatalogueTest() throws Exception {
		PowerMockito.when(catalogueRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(catalogue));
		PowerMockito.when(catalogueMapper.toDto(catalogue)).thenReturn(catalogueDTO);

		Optional<CatalogueDTO> fetchedCatalogue = catalogueService.getCatalogue(1L);
		Assert.assertFalse(fetchedCatalogue.isEmpty());
		log.info("getCatalogueTest successful!");
	}

	/**
	 * Delete catalogue test.
	 */
	@Test
	public void deleteCatalogueTest() {
		catalogueService.deleteCatalogue(catalogue.getId());
	}

	/**
	 * Catalogue name code check for save success test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void catalogueNameCodeCheckForSaveSuccessTest() throws Exception {
		PowerMockito.when(catalogueRepository.findByDeptIdAndCatalogueCode(catalogueDTO.getDeptId(),
				catalogueDTO.getCatalogueCode())).thenReturn(null);
		Assert.assertFalse(catalogueService.catalogueNameCodeCheck(catalogueDTO, "code"));

		PowerMockito.when(catalogueRepository.findByDeptIdAndCatalogueName(catalogueDTO.getDeptId(),
				catalogueDTO.getCatalogueName())).thenReturn(null);
		Assert.assertFalse(catalogueService.catalogueNameCodeCheck(catalogueDTO, "name"));
	}

	/**
	 * Catalogue name code check for save fail test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void catalogueNameCodeCheckForSaveFailTest() throws Exception {
		CatalogueDTO catalogueDTO = new CatalogueDTO();
		BeanUtils.copyProperties(catalogue, catalogueDTO);
		catalogueDTO.setId(null);

		PowerMockito.when(catalogueRepository.findByDeptIdAndCatalogueCode(catalogueDTO.getDeptId(),
				catalogueDTO.getCatalogueCode())).thenReturn(catalogue);
		Assert.assertTrue(catalogueService.catalogueNameCodeCheck(catalogueDTO, "code"));

		PowerMockito.when(catalogueRepository.findByDeptIdAndCatalogueName(catalogueDTO.getDeptId(),
				catalogueDTO.getCatalogueName())).thenReturn(catalogue);
		Assert.assertTrue(catalogueService.catalogueNameCodeCheck(catalogueDTO, "name"));
	}

	/**
	 * Catalogue name code check for update fail test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void catalogueNameCodeCheckForUpdateFailTest() throws Exception {
		CatalogueDTO catalogueDTO = new CatalogueDTO();
		BeanUtils.copyProperties(catalogue, catalogueDTO);
		catalogue.setId(2L);

		PowerMockito.when(catalogueRepository.findByDeptIdAndCatalogueCode(catalogueDTO.getDeptId(),
				catalogueDTO.getCatalogueCode())).thenReturn(catalogue);
		Assert.assertTrue(catalogueService.catalogueNameCodeCheck(catalogueDTO, "code"));

		PowerMockito.when(catalogueRepository.findByDeptIdAndCatalogueName(catalogueDTO.getDeptId(),
				catalogueDTO.getCatalogueName())).thenReturn(catalogue);
		Assert.assertTrue(catalogueService.catalogueNameCodeCheck(catalogueDTO, "name"));
	}
}
