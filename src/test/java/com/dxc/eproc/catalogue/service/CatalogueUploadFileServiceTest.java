package com.dxc.eproc.catalogue.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.util.IOUtils;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dxc.eproc.catalogue.controller.CatalogueItemController;
import com.dxc.eproc.catalogue.documentstore.CatalogueDocumentStore;
import com.dxc.eproc.catalogue.documentstore.ReferenceTypes;
import com.dxc.eproc.catalogue.dto.request.CatalogueUploadFileDTO;
import com.dxc.eproc.catalogue.dto.request.CatalogueUploadFileDetailsDTO;
import com.dxc.eproc.catalogue.mapper.CatalogueUploadFileDetailsMapper;
import com.dxc.eproc.catalogue.mapper.CatalogueUploadFileMapper;
import com.dxc.eproc.catalogue.model.Catalogue;
import com.dxc.eproc.catalogue.model.CatalogueUploadFile;
import com.dxc.eproc.catalogue.model.CatalogueUploadFileDetails;
import com.dxc.eproc.catalogue.model.Category;
import com.dxc.eproc.catalogue.repository.CatalogueItemRepository;
import com.dxc.eproc.catalogue.repository.CatalogueRepository;
import com.dxc.eproc.catalogue.repository.CatalogueUploadFileDetailsRepository;
import com.dxc.eproc.catalogue.repository.CatalogueUploadFileRepository;
import com.dxc.eproc.catalogue.repository.CategoryRepository;
import com.dxc.eproc.catalogue.services.CatalogueItemService;
import com.dxc.eproc.catalogue.services.impl.CatalogueUploadFileServiceImpl;
import com.querydsl.core.BooleanBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Class CatalogueItemServiceImplTest.
 */
public class CatalogueUploadFileServiceTest extends PowerMockTestCase {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(CatalogueUploadFileServiceTest.class);

	/** The catalogue upload file. */
	private static CatalogueUploadFile catalogueUploadFile;

	/** The catalogue upload file DTO. */
	private static CatalogueUploadFileDTO catalogueUploadFileDTO;

	/** The catalogue upload file details. */
	private static CatalogueUploadFileDetails catalogueUploadFileDetails;

	/** The catalogue upload file details DTO. */
	private static CatalogueUploadFileDetailsDTO catalogueUploadFileDetailsDTO;

	/** The catalogue upload file repository. */
	@Mock
	private CatalogueUploadFileRepository catalogueUploadFileRepository;

	/** The catalogue upload file details repository. */
	@Mock
	private CatalogueUploadFileDetailsRepository catalogueUploadFileDetailsRepository;

	/** The catalogue upload file mapper. */
	@Mock
	private CatalogueUploadFileMapper catalogueUploadFileMapper;

	/** The catalogue upload file details mapper. */
	@Mock
	private CatalogueUploadFileDetailsMapper catalogueUploadFileDetailsMapper;

	/** The catalogue upload file service. */
	@Mock
	private CatalogueUploadFileServiceImpl catalogueUploadFileService;

	/** The catalogue upload file controller. */
	@InjectMocks
	private CatalogueItemController catalogueItemController;

	@Mock
	private CatalogueRepository catalogueRepository;

	@Mock
	private CatalogueDocumentStore catalogueDocumentStore;

	@Mock
	private CatalogueItemRepository catalogueItemRepository;

	@Mock
	private CatalogueItemService catalogueItemService;

	@Mock
	private CategoryRepository categoryRepository;

	/**
	 * Inits the.
	 */
	@BeforeClass
	public void init() {
		log.info("==========================================================================");
		log.info("This is executed before once Per Test Class - CatalogueUploadFileServiceTest: init");

		catalogueUploadFile = new CatalogueUploadFile();

		catalogueUploadFile.setId(100L);
		catalogueUploadFile.setFileName("Test");
		catalogueUploadFile.setStatus("SUCCESS");
		catalogueUploadFile.setCreatedBy("system");
		catalogueUploadFile.setLastModifiedBy("system");

		catalogueUploadFileDTO = new CatalogueUploadFileDTO();
		BeanUtils.copyProperties(catalogueUploadFile, catalogueUploadFileDTO);

		catalogueUploadFileDetails = new CatalogueUploadFileDetails();

		catalogueUploadFileDetails.setCatalogueCode("Cat1");
		catalogueUploadFileDetails.setCatalogueFileId(200L);
		catalogueUploadFileDetails.setId(100L);
		catalogueUploadFileDetails.setItemCode("ItemCode1");
		catalogueUploadFileDetails.setItemName("ItemName1");
		catalogueUploadFileDetails.setStatus("FAILED");
		catalogueUploadFileDetails.setErrorReason("Error");

		catalogueUploadFileDetailsDTO = new CatalogueUploadFileDetailsDTO();
		BeanUtils.copyProperties(catalogueUploadFileDetails, catalogueUploadFileDetailsDTO);

	}

	/**
	 * Sets the up.
	 */
	@BeforeMethod
	public void setUp() {
		log.info("==========================================================================");
		log.info("This is executed before each Test - CatalogueUploadFileServiceTest: setUp");

		catalogueUploadFileService = new CatalogueUploadFileServiceImpl(catalogueUploadFileRepository,
				catalogueUploadFileMapper, catalogueUploadFileDetailsRepository, catalogueUploadFileDetailsMapper);
	}

	/**
	 * Test search catalogue upload files.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testSearchCatalogueUploadFiles() throws Exception {
		log.info("==========================================================================");
		log.info("testSearchCatalogueUploadFiles - Start");
		List<CatalogueUploadFile> catalogueUploadFileList = new ArrayList<>();
		catalogueUploadFileList.add(catalogueUploadFile);
		Page<CatalogueUploadFile> page = new PageImpl<>(catalogueUploadFileList);
		PageRequest pageRequest = PageRequest.of(0, 5);
		BooleanBuilder builder = null;
		PowerMockito.when(catalogueUploadFileRepository.findAll(builder, pageRequest)).thenReturn(page);
		PowerMockito.when(catalogueUploadFileMapper.toDto(catalogueUploadFile)).thenReturn(catalogueUploadFileDTO);
		Page<CatalogueUploadFileDTO> catalogueUploadFileDTOPage = catalogueUploadFileService
				.searchCatalogueUploadFiles(builder, pageRequest);
		Assert.assertTrue(catalogueUploadFileDTOPage.hasContent());
		log.info("testSearchCatalogueUploadFiles successful!");
		log.info("testSearchCatalogueUploadFiles - End");
		log.info("==========================================================================");
	}

	/**
	 * Test search catalogue upload files details.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testSearchCatalogueUploadFilesDetails() throws Exception {
		log.info("==========================================================================");
		log.info("testSearchCatalogueUploadFiles - Start");
		List<CatalogueUploadFileDetails> catalogueUploadFileDetailsList = new ArrayList<>();
		catalogueUploadFileDetailsList.add(catalogueUploadFileDetails);
		Page<CatalogueUploadFileDetails> page = new PageImpl<>(catalogueUploadFileDetailsList);
		PageRequest pageRequest = PageRequest.of(0, 5);
		BooleanBuilder builder = null;
		PowerMockito.when(catalogueUploadFileDetailsRepository.findAll(builder, pageRequest)).thenReturn(page);
		PowerMockito.when(catalogueUploadFileDetailsMapper.toDto(catalogueUploadFileDetails))
				.thenReturn(catalogueUploadFileDetailsDTO);
		Page<CatalogueUploadFileDetailsDTO> catalogueUploadFileDetailsDTOPage = catalogueUploadFileService
				.searchCatalogueUploadFilesDetails(builder, pageRequest);
		Assert.assertTrue(catalogueUploadFileDetailsDTOPage.hasContent());
		log.info("testSearchCatalogueUploadFiles successful!");
		log.info("testSearchCatalogueUploadFiles - End");
		log.info("==========================================================================");
	}

	/**
	 * Test save catalogue upload file.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testSaveCatalogueUploadFile() throws Exception {

		log.info("==========================================================================");
		log.info("testSaveCatalogueUploadFile - Start");
		PowerMockito.when(catalogueUploadFileMapper.toEntity(catalogueUploadFileDTO)).thenReturn(catalogueUploadFile);
		PowerMockito.when(catalogueUploadFileRepository.save(catalogueUploadFile)).thenReturn(catalogueUploadFile);
		PowerMockito.when(catalogueUploadFileMapper.toDto(catalogueUploadFile)).thenReturn(catalogueUploadFileDTO);
		CatalogueUploadFileDTO catalogueUploadFileDTO1 = catalogueUploadFileService.save(catalogueUploadFileDTO);
		log.info("Response data:saveTest: " + catalogueUploadFileDTO1);

		Assert.assertFalse(Optional.ofNullable(catalogueUploadFileDTO1).isEmpty());

		log.info("testSaveCatalogueUploadFile - End");
		log.info("==========================================================================");

	}

	/**
	 * Test save catalogue upload file details.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testSaveCatalogueUploadFileDetails() throws Exception {

		log.info("==========================================================================");
		log.info("testSaveCatalogueUploadFileDetails - Start");
		PowerMockito.when(catalogueUploadFileDetailsMapper.toEntity(catalogueUploadFileDetailsDTO))
				.thenReturn(catalogueUploadFileDetails);
		PowerMockito.when(catalogueUploadFileDetailsRepository.save(catalogueUploadFileDetails))
				.thenReturn(catalogueUploadFileDetails);
		PowerMockito.when(catalogueUploadFileDetailsMapper.toDto(catalogueUploadFileDetails))
				.thenReturn(catalogueUploadFileDetailsDTO);
		CatalogueUploadFileDetailsDTO catalogueUploadFileDetailsDTO1 = catalogueUploadFileService
				.saveCatalogueUploadFileDetails(catalogueUploadFileDetailsDTO);
		log.info("Response data:saveTest: " + catalogueUploadFileDetailsDTO1);

		Assert.assertFalse(Optional.ofNullable(catalogueUploadFileDetailsDTO1).isEmpty());

		log.info("testSaveCatalogueUploadFileDetails - End");
		log.info("==========================================================================");

	}

	/**
	 * Test Get catalogue file upload Error details in Excel download.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetCatalogueFileUploadErrors() throws Exception {

		log.info("==========================================================================");
		log.info("testGetCatalogueFileUploadErrors - Start");

		List<CatalogueUploadFileDetails> catalogueUploadFileDetailsList = new ArrayList<>();
		PowerMockito
				.when(catalogueUploadFileDetailsRepository.findByCatalogueFileIdAndStatus(
						catalogueUploadFileDetails.getId(), catalogueUploadFileDetails.getStatus()))
				.thenReturn(catalogueUploadFileDetailsList);

		List<CatalogueUploadFileDetails> errorDetailsList = catalogueUploadFileService
				.loadErrorDetails(catalogueUploadFileDetails.getId());
		Assert.assertFalse(Optional.ofNullable(errorDetailsList).isEmpty());

		log.info("testGetCatalogueFileUploadErrors - End");
		log.info("==========================================================================");

	}

	/**
	 * Test the catalogue upload file process Success.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetUploadFileProcessSuccess() throws Exception {

		log.info("==========================================================================");
		log.info("testGetUploadFileProcessSuccess - Start");

		File file = new File("src/main/resources/hello.xlsx");
		FileInputStream input = new FileInputStream(file);
		MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain",
				IOUtils.toByteArray(input));
		Catalogue catalogue = new Catalogue();
		Category category = new Category();
		PowerMockito.when(catalogueRepository.findByCatalogueCode(Mockito.anyString())).thenReturn(catalogue);
		PowerMockito.when(catalogueUploadFileRepository.findById(Mockito.anyLong()))
				.thenReturn(Optional.ofNullable(catalogueUploadFile));
		PowerMockito.when(
				catalogueDocumentStore.getObject(ReferenceTypes.CATALOGUE_UPLOAD_FILE, catalogueUploadFile.getId()))
				.thenReturn(multipartFile.getBytes());
		PowerMockito.when(catalogueItemRepository.findByItemCode(Mockito.anyString())).thenReturn(Optional.empty());
		PowerMockito.when(categoryRepository.findByCategoryCode(Mockito.anyString())).thenReturn(Optional.of(category));
		PowerMockito.when(catalogueUploadFileDetailsRepository.save(catalogueUploadFileDetails))
				.thenReturn(catalogueUploadFileDetails);
		PowerMockito.when(catalogueUploadFileRepository.save(catalogueUploadFile)).thenReturn(catalogueUploadFile);

		catalogueItemController.uploadCatalogueFileProcess("" + catalogue.getCatalogueCode() + "|"
				+ catalogueUploadFile.getId() + "|" + catalogueUploadFile.getFileName());

		log.info("testGetUploadFileProcessSuccess - End");
		log.info("==========================================================================");
	}

	/**
	 * Test the catalogue upload file process Failure.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetUploadFileProcessFailure() throws Exception {

		log.info("==========================================================================");
		log.info("testGetUploadFileProcessFailure - Start");

		MockMultipartFile file = new MockMultipartFile("file", "hello.xlsx", MediaType.APPLICATION_OCTET_STREAM_VALUE,
				"Hello, World!".getBytes());

		Catalogue catalogue = new Catalogue();
		PowerMockito.when(catalogueRepository.findByCatalogueCode(Mockito.anyString())).thenReturn(catalogue);
		PowerMockito.when(catalogueUploadFileRepository.findById(Mockito.anyLong()))
				.thenReturn(Optional.ofNullable(catalogueUploadFile));
		PowerMockito.when(
				catalogueDocumentStore.getObject(ReferenceTypes.CATALOGUE_UPLOAD_FILE, catalogueUploadFile.getId()))
				.thenReturn(file.getBytes());

		catalogueItemController.uploadCatalogueFileProcess("" + catalogue.getCatalogueCode() + "|"
				+ catalogueUploadFile.getId() + "|" + catalogueUploadFile.getFileName());

		log.info("testGetUploadFileProcessFailure - End");
		log.info("==========================================================================");

	}
}
