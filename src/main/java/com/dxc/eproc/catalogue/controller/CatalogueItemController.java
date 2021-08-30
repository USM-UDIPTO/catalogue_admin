package com.dxc.eproc.catalogue.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dxc.eproc.catalogue.config.EprocMessageSourceComponent;
import com.dxc.eproc.catalogue.documentstore.CatalogueDocumentStore;
import com.dxc.eproc.catalogue.documentstore.ReferenceTypes;
import com.dxc.eproc.catalogue.dto.request.CatalogueItemDTO;
import com.dxc.eproc.catalogue.dto.request.CatalogueItemSearchDTO;
import com.dxc.eproc.catalogue.dto.request.CatalogueUploadFileDTO;
import com.dxc.eproc.catalogue.dto.request.CatalogueUploadFileDetailsDTO;
import com.dxc.eproc.catalogue.dto.request.CategoryDetailsDTO;
import com.dxc.eproc.catalogue.dto.request.UnspscDetailsDTO;
import com.dxc.eproc.catalogue.jms.JmsUtility;
import com.dxc.eproc.catalogue.mapper.CatalogueItemMapper;
import com.dxc.eproc.catalogue.model.Catalogue;
import com.dxc.eproc.catalogue.model.CatalogueItem;
import com.dxc.eproc.catalogue.model.CatalogueUploadFile;
import com.dxc.eproc.catalogue.model.CatalogueUploadFileDetails;
import com.dxc.eproc.catalogue.model.CatalogueUploadFileExcelTemplate;
import com.dxc.eproc.catalogue.model.Category;
import com.dxc.eproc.catalogue.model.CategoryDetails;
import com.dxc.eproc.catalogue.model.QCatalogueUploadFile;
import com.dxc.eproc.catalogue.model.QCatalogueUploadFileDetails;
import com.dxc.eproc.catalogue.repository.CatalogueRepository;
import com.dxc.eproc.catalogue.repository.CatalogueUploadFileDetailsRepository;
import com.dxc.eproc.catalogue.repository.CatalogueUploadFileRepository;
import com.dxc.eproc.catalogue.repository.CategoryRepository;
import com.dxc.eproc.catalogue.services.CatalogueItemService;
import com.dxc.eproc.catalogue.services.CatalogueUploadFileService;
import com.dxc.eproc.client.master.api.UomControllerApiClient;
import com.dxc.eproc.client.master.dto.UomDTO;
import com.dxc.eproc.document.space.DocumentMetaData;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.HeaderUtil;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.template.CatalogueItemUploadFileExporter;
import com.dxc.eproc.utils.PaginationUtil;
import com.querydsl.core.BooleanBuilder;

import feign.FeignException.FeignClientException;

// TODO: Auto-generated Javadoc
/**
 * The Class CatalogueItemController.
 */
@RestController
@RequestMapping("/v1/api")
@Transactional
public class CatalogueItemController {

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "CatalogueItem";

	/** The Constant ENTITY_NAME. */
	private static final String IN_PROGRESS = "IN_PROGRESS";

	/** The Constant SUCCESS. */
	private static final String SUCCESS = "SUCCESS";

	/** The Constant FAILED. */
	private static final String FAILED = "FAILED";

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(CatalogueItemController.class);

	/** The application name. */
	@Value("${eprocurement.clientApp.name}")
	private String applicationName;

	/** The message source utility. */
	@Autowired
	private EprocMessageSourceComponent messageSourceUtility;

	/** The catalogue item service. */
	@Autowired
	private CatalogueItemService catalogueItemService;

	/** The catalogue item service. */
	@Autowired
	private CatalogueUploadFileService catalogueUploadFileService;

	/** The catalogue repository. */
	@Autowired
	private CatalogueRepository catalogueRepository;

	/** The category repository. */
	@Autowired
	private CategoryRepository categoryRepository;

	/** The catalogue item mapper. */
	@Autowired
	private CatalogueItemMapper catalogueItemMapper;

	/** The jms template. */
	@Autowired
	private JmsUtility jmsUtility;

	/** The catalogue item file repository. */
	@Autowired
	private CatalogueUploadFileRepository catalogueUploadFileRepository;

	/** The catalogue item file details repository. */
	@Autowired
	private CatalogueUploadFileDetailsRepository catalogueUploadFileDetailsRepository;

	/** The catalogue document store. */
	@Autowired
	private CatalogueDocumentStore catalogueDocumentStore;

	/** The uom controller api client. */
	@Autowired
	private UomControllerApiClient uomControllerApiClient;

	/**
	 * Creates the catalogueItem.
	 *
	 * @param catalogueItemDTO the catalogue item DTO
	 * @param catalogueCode    the catalogue code
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@PostMapping("/catalogue/{catalogueCode}/catalogue-item")
	public ResponseEntity<CatalogueItemDTO> createCatalogueItem(@Valid @RequestBody CatalogueItemDTO catalogueItemDTO,
			@PathVariable("catalogueCode") String catalogueCode) throws Exception {
		log.debug("REST request to save CatalogueItem : {}", catalogueItemDTO);
		String errorMessage = "CatalogueItem.createCatalogueItem.";
		if (catalogueItemDTO.getId() != null) {
			throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "idPresent"), ENTITY_NAME,
					"idPresent");
		}
		Catalogue catalogue = catalogueRepository.findByDeptIdAndCatalogueCode(catalogueItemDTO.getDeptId(),
				catalogueCode);
		if (Optional.ofNullable(catalogue).isEmpty()) {
			throw new RecordNotFoundException(messageSourceUtility.getMessage(errorMessage + "catalogueNotFound"),
					ENTITY_NAME);
		}
		catalogueItemDTO.setCatalogueCode(catalogue.getCatalogueCode());
		catalogueItemDTO.setCatalogueName(catalogue.getCatalogueName());
		catalogueItemService.checkItemCodeExist(catalogueItemDTO.getItemCode()).ifPresent(s -> {
			throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "codeExist"), ENTITY_NAME,
					"codeExist");
		});
		catalogueItemService.checkItemNameExist(catalogueItemDTO.getItemName()).ifPresent(s -> {
			throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "nameExist"), ENTITY_NAME,
					"nameExist");
		});

		if (CollectionUtils.isEmpty(catalogueItemDTO.getCategories())) {
			catalogueItemDTO.setCategories(new ArrayList<>());
		} else {
			catalogueItemDTO.getCategories().forEach(catDetail -> {
				Category category = categoryRepository
						.findByCategoryCodeAndCatalogue(catDetail.getCategoryCode(), catalogue)
						.orElseThrow(() -> new RecordNotFoundException(
								messageSourceUtility.getMessage(errorMessage + "categoryNotFound"), ENTITY_NAME));
				catDetail.setCategoryName(category.getCategoryName());
			});
		}

		if (!CollectionUtils.isEmpty(catalogueItemDTO.getUnspscDetails())) {
			if (checkUnspscDetailsList(catalogueItemDTO.getUnspscDetails(), catalogueItemDTO)) {
				throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "incorrectData"),
						ENTITY_NAME, "incorrectData");
			}
		} else {
			catalogueItemDTO.setUnspscDetails(new ArrayList<>());
		}
		if (catalogueItemDTO.getSpecifications() == null) {
			catalogueItemDTO.setSpecifications(new HashMap<>());
		}

		try {
			ResponseEntity<List<UomDTO>> response = uomControllerApiClient
					.getAllActiveUomsByNameUsingGET(catalogueItemDTO.getUomName());
			List<UomDTO> masterUomList = response.getBody();
			catalogueItemDTO.setUomId(masterUomList.get(0).getId().longValue());
			catalogueItemDTO.setUomName(masterUomList.get(0).getName());
		} catch (FeignClientException e) {
			if (e.status() == 404) {
				throw new RecordNotFoundException(messageSourceUtility.getMessage(errorMessage + "incorrectUomName"),
						ENTITY_NAME);
			} else {
				throw new Exception(e.getMessage());
			}
		}

		catalogueItemDTO = catalogueItemService.save(catalogueItemDTO);
		if (!CollectionUtils.isEmpty(catalogueItemDTO.getUnspscDetails())) {
//			catalogueItemDTO.setUnspscCode(catalogueItemDTO.getUnspscCode() + "0"+catalogueItemDTO.getId());
			catalogueItemDTO.setUnspscItemCode(String.valueOf(
					Long.sum(Long.valueOf(catalogueItemDTO.getUnspscItemCode()) * 100, catalogueItemDTO.getId())));
		} else {
			catalogueItemDTO.setUnspscItemCode(null);
		}
		catalogueItemDTO = catalogueItemService.save(catalogueItemDTO);

		return ResponseEntity.created(new URI("/v1/public/catalogueItem/" + catalogueItemDTO.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME,
						catalogueItemDTO.getId().toString()))
				.body(catalogueItemDTO);
	}

	/**
	 * Update catalogue item.
	 *
	 * @param catalogueItemDTO the catalogue item DTO
	 * @param catalogueCode    the catalogue code
	 * @param catalogueItemId  the catalogue item id
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@PutMapping("/catalogue/{catalogueCode}/catalogue-item/{id}")
	public ResponseEntity<CatalogueItemDTO> updateCatalogueItem(
			@Valid @RequestBody final CatalogueItemDTO catalogueItemDTO,
			@PathVariable("catalogueCode") String catalogueCode, @PathVariable("id") Long catalogueItemId)
			throws Exception {

		log.debug("REST request to save CatalogueItem : {}", catalogueItemDTO);
		String errorMessage = "CatalogueItem.updateCatalogueItem.";
		Catalogue catalogue = catalogueRepository.findByDeptIdAndCatalogueCode(catalogueItemDTO.getDeptId(),
				catalogueCode);
		if (Optional.ofNullable(catalogue).isEmpty()) {
			throw new RecordNotFoundException(messageSourceUtility.getMessage(errorMessage + "catalogueNotFound"),
					ENTITY_NAME);
		}
		catalogueItemDTO.setCatalogueCode(catalogue.getCatalogueCode());
		catalogueItemDTO.setCatalogueName(catalogue.getCatalogueName());
		CatalogueItemDTO catalogueItemFromDB = catalogueItemService.get(catalogueItemId, catalogueCode)
				.orElseThrow(
						() -> new RecordNotFoundException(
								"CatalogueItem is not avaliable for - catalogueItemId: "
										+ String.valueOf(catalogueItemId) + "CatalogueCode: " + catalogueCode,
								"catalogueItem"));
		catalogueItemDTO.setId(catalogueItemFromDB.getId());

		catalogueItemService.checkItemCodeExist(catalogueItemDTO.getItemCode()).ifPresent(s -> {

			if (s.getId().equals(catalogueItemDTO.getId())) {
				return;
			} else {
				throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "codeExist"),
						ENTITY_NAME, "codeExist");
			}
		});

		catalogueItemService.checkItemNameExist(catalogueItemDTO.getItemName()).ifPresent(s -> {

			if (s.getId().equals(catalogueItemDTO.getId())) {
				return;
			} else {
				throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "nameExist"),
						ENTITY_NAME, "nameExist");
			}
		});

		if (CollectionUtils.isEmpty(catalogueItemDTO.getCategories())) {
			throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "noCategoryDetails"),
					ENTITY_NAME, "noCategoryDetails");
		} else {
			catalogueItemDTO.getCategories().forEach(catDetail -> {
				Category category = categoryRepository
						.findByCategoryCodeAndCatalogue(catDetail.getCategoryCode(), catalogue)
						.orElseThrow(() -> new RecordNotFoundException(
								messageSourceUtility.getMessage(errorMessage + "categoryNotFound"), ENTITY_NAME));
				catDetail.setCategoryName(category.getCategoryName());
			});
		}

		if (!CollectionUtils.isEmpty(catalogueItemDTO.getUnspscDetails())) {
			if (checkUnspscDetailsList(catalogueItemDTO.getUnspscDetails(), catalogueItemDTO)) {
				throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "incorrectData"),
						ENTITY_NAME, "incorrectData");
			}
			catalogueItemDTO.setUnspscItemCode(String.valueOf(
					Long.sum(Long.valueOf(catalogueItemDTO.getUnspscItemCode()) * 100, catalogueItemDTO.getId())));
		} else {
			catalogueItemDTO.setUnspscDetails(new ArrayList<>());
		}
		if (catalogueItemDTO.getSpecifications() == null) {
			catalogueItemDTO.setSpecifications(new HashMap<>());
		}

		try {
			ResponseEntity<List<UomDTO>> response = uomControllerApiClient
					.getAllActiveUomsByNameUsingGET(catalogueItemDTO.getUomName());
			List<UomDTO> masterUomList = response.getBody();
			catalogueItemDTO.setUomId(masterUomList.get(0).getId().longValue());
			catalogueItemDTO.setUomName(masterUomList.get(0).getName());
		} catch (FeignClientException e) {
			if (e.status() == 404) {
				throw new RecordNotFoundException(messageSourceUtility.getMessage(errorMessage + "incorrectUomName"),
						ENTITY_NAME);
			} else {
				throw new Exception(e.getMessage());
			}
		}

		CatalogueItemDTO catalogueItemDTO1 = catalogueItemService.save(catalogueItemDTO);

		return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME,
				catalogueItemDTO.getId().toString())).body(catalogueItemDTO1);
	}

	/**
	 * Check unspsc details list.
	 *
	 * @param unspscDetails    the unspsc details
	 * @param catalogueItemDTO the catalogue item DTO
	 * @return true, if successful
	 */
	private boolean checkUnspscDetailsList(List<UnspscDetailsDTO> unspscDetails, CatalogueItemDTO catalogueItemDTO) {
		if (unspscDetails.size() != 4) {
			return true;
		}

		if (unspscDetails.stream().map(unspscDetail -> {
			return unspscDetail.getUnspscType() != null
					&& unspscDetail.getUnspscType().equalsIgnoreCase(ReferenceTypes.SEGMENT.toString()) ? true : null;
		}).filter(Objects::nonNull).collect(Collectors.toList()).size() != 1) {
			return true;
		}

		if (unspscDetails.stream().map(unspscDetail -> {
			return unspscDetail.getUnspscType() != null
					&& unspscDetail.getUnspscType().equalsIgnoreCase(ReferenceTypes.FAMILY.toString()) ? true : null;
		}).filter(Objects::nonNull).collect(Collectors.toList()).size() != 1) {
			return true;
		}

		if (unspscDetails.stream().map(unspscDetail -> {
			return unspscDetail.getUnspscType() != null
					&& unspscDetail.getUnspscType().equalsIgnoreCase(ReferenceTypes.CLASS.toString()) ? true : null;
		}).filter(Objects::nonNull).collect(Collectors.toList()).size() != 1) {
			return true;
		}

		if (unspscDetails.stream().map(unspscDetail -> {
			if (unspscDetail.getUnspscType() != null) {
				if (unspscDetail.getUnspscType().equalsIgnoreCase(ReferenceTypes.COMMODITY.toString())) {
					catalogueItemDTO.setUnspscItemCode(unspscDetail.getUnspscCode());
					return true;
				}
			}
			return null;
		}).filter(Objects::nonNull).collect(Collectors.toList()).size() != 1) {
			return true;
		}

		return false;
	}

	/**
	 * Gets the catalogue item.
	 *
	 * @param catalogueCode   the catalogue code
	 * @param CatalogueItemId the catalogue item id
	 * @return the catalogue item
	 * @throws URISyntaxException the URI syntax exception
	 */
	@GetMapping("/catalogue/{catalogueCode}/catalogue-item/{id}")
	public ResponseEntity<CatalogueItemDTO> getCatalogueItem(@PathVariable("catalogueCode") String catalogueCode,
			@PathVariable("id") Long CatalogueItemId) throws URISyntaxException {

		log.debug("REST request to get CatalogueItem by catalogueCode %s and id %d ----------- ", catalogueCode,
				CatalogueItemId);
		String errorMessage = "CatalogueItem.getCatalogueItem.";
		CatalogueItemDTO catalogueItemDTO = catalogueItemService.get(CatalogueItemId, catalogueCode).orElseThrow(
				() -> new RecordNotFoundException(messageSourceUtility.getMessage(errorMessage + "notAvailable")
						+ CatalogueItemId + "  CatalogueCode: " + catalogueCode, ENTITY_NAME));

		return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME,
				catalogueItemDTO.getId().toString())).body(catalogueItemDTO);
	}

	/**
	 * Delete catalogue item.
	 *
	 * @param catalogueCode   the catalogue code
	 * @param CatalogueItemId the catalogue item id
	 * @return the response entity
	 * @throws URISyntaxException the URI syntax exception
	 */
	@DeleteMapping("/catalogue/{catalogueCode}/catalogue-item/{id}")
	public ResponseEntity<CatalogueItemDTO> deleteCatalogueItem(@PathVariable("catalogueCode") String catalogueCode,
			@PathVariable("id") Long CatalogueItemId) throws URISyntaxException {

		log.debug("REST request to delete CatalogueItem by catalogueCode %s and id %d ----------- ", catalogueCode,
				CatalogueItemId);
		String errorMessage = "CatalogueItem.getCatalogueItem.";
		CatalogueItemDTO catalogueItemDTO = catalogueItemService.get(CatalogueItemId, catalogueCode).orElseThrow(
				() -> new RecordNotFoundException(messageSourceUtility.getMessage(errorMessage + "notAvailable")
						+ CatalogueItemId + "  CatalogueCode: " + catalogueCode, ENTITY_NAME));

		catalogueItemService.delete(catalogueItemDTO.getId());
		return ResponseEntity.ok().headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME,
				catalogueItemDTO.getId().toString())).body(catalogueItemDTO);
	}

	/**
	 * Select catalogue items.
	 *
	 * @param catalogueCode the catalogue code
	 * @param itemCodeList  the item code list
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@PostMapping("/catalogue/{catalogueCode}/catalogue-item/items")
	public ResponseEntity<List<CatalogueItemDTO>> selectCatalogueItems(
			@PathVariable("catalogueCode") String catalogueCode, @RequestBody List<String> itemCodeList)
			throws Exception {
		String errorMessage = "CatalogueItem.selectCatalogueItems.";
		if (itemCodeList.isEmpty()) {
			throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "noItemsSelected"),
					ENTITY_NAME, "noItemsSelected");
		}
		Catalogue catalogue = catalogueRepository.findByCatalogueCode(catalogueCode);
		if (Optional.ofNullable(catalogue).isEmpty()) {
			throw new RecordNotFoundException(messageSourceUtility.getMessage(errorMessage + "catalogueNotFound"),
					ENTITY_NAME);
		}
		List<CatalogueItemDTO> itemList = new ArrayList<>();
		for (String itemCode : itemCodeList) {
			catalogueItemService.getItemByCodeAndCatalogueCode(itemCode, catalogueCode).ifPresent(itemList::add);
		}
		if (itemList.isEmpty()) {
			throw new RecordNotFoundException(messageSourceUtility.getMessage(errorMessage + "noItemsFound"),
					ENTITY_NAME);
		}
		return ResponseEntity.ok(itemList);
	}

	/**
	 * Search catalogue items.
	 *
	 * @param catalogueCode          the catalogue code
	 * @param pageIndex              the page index
	 * @param pageSize               the page size
	 * @param catalogueItemSearchDTO the catalogue item search DTO
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@PostMapping("/catalogue/{catalogueCode}/catalogue-item/search")
	public ResponseEntity<List<CatalogueItemDTO>> searchCatalogueItems(
			@PathVariable("catalogueCode") String catalogueCode,
			@RequestParam(name = "page", defaultValue = "0") int pageIndex,
			@RequestParam(name = "size", defaultValue = "5") int pageSize,
			@RequestBody CatalogueItemSearchDTO catalogueItemSearchDTO) throws Exception {
		log.debug("REST request to get a page of searchCatalogueItems");
		Page<CatalogueItemDTO> catalogueItemPagable = null;
		String errorMessage = "CatalogueItem.searchCatalogueItems.";
		Catalogue catalogue = catalogueRepository.findByCatalogueCode(catalogueCode);
		if (Optional.ofNullable(catalogue).isEmpty()) {
			throw new RecordNotFoundException(messageSourceUtility.getMessage(errorMessage + "catalogueNotFound"),
					ENTITY_NAME);
		}
		catalogueItemSearchDTO.setCatalogueCode(catalogueCode);
		if (catalogueItemSearchDTO != null && catalogueItemSearchDTO.getCatalogueItemCode() == null
				&& catalogueItemSearchDTO.getCatalogueItemName() == null) {

			List<CatalogueItem> catalogueItemList = catalogueItemService
					.getCatalogueitemsByCatalogueCode(catalogueItemSearchDTO.getCatalogueCode());

			List<CatalogueItemDTO> catalogueItemDTOList = new ArrayList<>();

			List<String> categoryList = catalogueItemSearchDTO.getCategoryCodeList();

			Set<CatalogueItem> catalogueItemListFiltered = new HashSet<CatalogueItem>();

			if (CollectionUtils.isEmpty(categoryList)) {
				throw new BadRequestAlertException(
						messageSourceUtility.getMessage(errorMessage + "noCategoriesSelected"), ENTITY_NAME,
						"noCategoriesSelected");
			}
			for (String category : categoryList) {

				catalogueItemListFiltered.addAll(catalogueItemList.stream().filter(i -> {

					for (CategoryDetails catDetails : i.getCategories()) {
						if (catDetails.getCategoryCode().equalsIgnoreCase(category)) {
							return true;
						}
					}
					return false;
				}).collect(Collectors.toCollection(ArrayList<CatalogueItem>::new)));
			}

			for (CatalogueItem item : catalogueItemListFiltered) {
				catalogueItemDTOList.add(catalogueItemMapper.toDto(item));
			}
			Collections.sort(catalogueItemDTOList, new SortByLastModifiedTs());
			PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);
			int start = (int) pageRequest.getOffset();
			int end = Math.min((start + pageRequest.getPageSize()), catalogueItemDTOList.size());
			if (end <= start) {
				catalogueItemPagable = new PageImpl<>(new ArrayList<>());
			} else {
				catalogueItemPagable = new PageImpl<>(catalogueItemDTOList.subList(start, end), pageRequest,
						catalogueItemDTOList.size());
			}
		} else {

			catalogueItemPagable = catalogueItemService.searchCatalogueItems(PageRequest.of(pageIndex, pageSize),
					catalogueItemSearchDTO);
		}

		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), catalogueItemPagable);

		return ResponseEntity.ok().headers(headers).body(catalogueItemPagable.getContent());
	}

	/**
	 * uploadCatalogueItems.
	 *
	 * @param catalogueCode the catalogue code
	 * @param file          the file
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@PostMapping(value = "/catalogue/{catalogueCode}/catalogue-upload", consumes = {
			MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<CatalogueUploadFileDTO> uploadCatalogueItems(
			@PathVariable("catalogueCode") String catalogueCode, @RequestParam("file") MultipartFile file)
			throws Exception {

		log.debug("Inside uploadCatalogueItemsJMS Method ----------- ");
		String errorMessage = "CatalogueItem.selectCatalogueItems.";
		Catalogue catalogue = catalogueRepository.findByCatalogueCode(catalogueCode);

		if (Optional.ofNullable(catalogue).isEmpty()) {
			throw new RecordNotFoundException(messageSourceUtility.getMessage(errorMessage + "catalogueNotFound"),
					ENTITY_NAME);
		}

		CatalogueUploadFileDTO catFileDTO = new CatalogueUploadFileDTO();
		catFileDTO.setFileName(file.getOriginalFilename());
		catFileDTO.setStatus(IN_PROGRESS);
		CatalogueUploadFileDTO savedCatalogueUploadFileDTO = catalogueUploadFileService.save(catFileDTO);

		Path path = new File(file.getOriginalFilename()).toPath();
		String mimeType = Files.probeContentType(path);
		DocumentMetaData documentMetaData = new DocumentMetaData();
		documentMetaData.setFileName(file.getOriginalFilename());
		documentMetaData.setFileData(file.getInputStream());
		documentMetaData.setMimeType(mimeType);
		documentMetaData.setReferenceId(savedCatalogueUploadFileDTO.getId());
		documentMetaData.setReferenceType(ReferenceTypes.CATALOGUE_UPLOAD_FILE.toString());
		catalogueDocumentStore.saveCatalogueDocument(documentMetaData);

		jmsUtility.sendMessage("" + catalogue.getCatalogueCode() + "|" + savedCatalogueUploadFileDTO.getId() + "|"
				+ savedCatalogueUploadFileDTO.getFileName());

		return ResponseEntity.created(new URI("/catalogue/catalogue-upload" + savedCatalogueUploadFileDTO.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME,
						savedCatalogueUploadFileDTO.getId().toString()))
				.body(savedCatalogueUploadFileDTO);
	}

	/**
	 * Gets the all catalogue upload file.
	 *
	 * @param pageIndex the page index
	 * @param pageSize  the page size
	 * @return the all catalogue upload file
	 * @throws Exception the exception
	 */
	@GetMapping("/catalogue-upload/search-status")
	public ResponseEntity<List<CatalogueUploadFileDTO>> getAllCatalogueUploadFile(
			@RequestParam(name = "page", defaultValue = "0") int pageIndex,
			@RequestParam(name = "size", defaultValue = "5") int pageSize) throws Exception {
		log.debug("REST request to get a page of catalogueUploadfile status search");
		String errorMessage = "CatalogueItem.getAllCatalogueUploadFile.";
		BooleanBuilder builder = new BooleanBuilder();
		// createdBy we need take from logged in user
		String createdBy = "system";
		builder.and(QCatalogueUploadFile.catalogueUploadFile.createdBy.containsIgnoreCase(createdBy));
		PageRequest pageRequest = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "lastModifiedTs"));
		Page<CatalogueUploadFileDTO> catalogueUploadFileDTOList = catalogueUploadFileService
				.searchCatalogueUploadFiles(builder, pageRequest);

		// String errorMessage = "CatalogueItem.selectCatalogueItems.";
		if (catalogueUploadFileDTOList.isEmpty()) {
			throw new RecordNotFoundException(messageSourceUtility.getMessage(errorMessage + "noRecordsFound"),
					ENTITY_NAME);
		}
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
				ServletUriComponentsBuilder.fromCurrentRequest(), catalogueUploadFileDTOList);
		log.info("API searchCatalogues ended at " + LocalTime.now());
		return ResponseEntity.ok().headers(headers).body(catalogueUploadFileDTOList.getContent());
	}

	/**
	 * Sets the catalogue item active yn.
	 *
	 * @param catalogueCode   the catalogue code
	 * @param catalogueItemId the catalogue item id
	 * @param activeYn        the active yn
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@PutMapping("/catalogue/{catalogueCode}/catalogue-item/{id}/activeYn/{activeYn}")
	public ResponseEntity<?> setCatalogueItemActiveYn(@PathVariable("catalogueCode") @NotNull String catalogueCode,
			@PathVariable("id") @NotNull Long catalogueItemId, @PathVariable("activeYn") Boolean activeYn)
			throws Exception {
		log.info("setCatalogueItemActiveYn started at " + LocalTime.now());
		String errorMessageCatalogue = "Catalogue.getCatalogue.";
		Catalogue catalogue1 = catalogueRepository.findByCatalogueCode(catalogueCode);
		if (Optional.ofNullable(catalogue1).isEmpty()) {
			throw new RecordNotFoundException(messageSourceUtility.getMessage(errorMessageCatalogue + "invalidId"),
					ENTITY_NAME);
		}

		String errorMessage = "CatalogueItem.getCatalogueItem.";
		CatalogueItemDTO catalogueItem = catalogueItemService.get(catalogueItemId, catalogueCode)
				.orElseThrow(
						() -> new RecordNotFoundException(
								messageSourceUtility.getMessage(errorMessage + "notAvailable")
										+ String.valueOf(catalogueItemId) + " CatalogueCode: " + catalogueCode,
								"catalogueItem"));
		catalogueItem.setActiveYn(activeYn);
		CatalogueItemDTO catalogueItemDTO = catalogueItemService.save(catalogueItem);
		log.info("setCatalogueItemActiveYn ended at " + LocalTime.now());
		return ResponseEntity.ok(catalogueItemDTO);
	}

	/**
	 * Gets the all catalogue upload file details.
	 *
	 * @param pageIndex             the page index
	 * @param pageSize              the page size
	 * @param catalogueUploadFileId the catalogue upload file id
	 * @return the all catalogue upload file details
	 * @throws Exception the exception
	 */
	@GetMapping("/catalogue-upload/{catalogue_upload-file-id}/view-details")
	public ResponseEntity<List<CatalogueUploadFileDetailsDTO>> getAllCatalogueUploadFileDetails(
			@RequestParam(name = "page", defaultValue = "0") int pageIndex,
			@RequestParam(name = "size", defaultValue = "5") int pageSize,
			@PathVariable("catalogue_upload-file-id") Long catalogueUploadFileId) throws Exception {
		log.debug("REST request to get a page of catalogueUploadfile status search :" + catalogueUploadFileId);
		String errorMessage = "CatalogueItem.getAllCatalogueUploadFileDetails.";
		BooleanBuilder builder = new BooleanBuilder();
		// createdBy we need take from logged in user
		builder.and(QCatalogueUploadFileDetails.catalogueUploadFileDetails.catalogueFileId.eq(catalogueUploadFileId));
		PageRequest pageRequest = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "lastModifiedTs"));
		Page<CatalogueUploadFileDetailsDTO> catalogueUploadFileDetailsDTOList = catalogueUploadFileService
				.searchCatalogueUploadFilesDetails(builder, pageRequest);
		if (catalogueUploadFileDetailsDTOList.isEmpty()) {
			throw new RecordNotFoundException(messageSourceUtility.getMessage(errorMessage + "noRecordsFound"),
					ENTITY_NAME);
		}
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
				ServletUriComponentsBuilder.fromCurrentRequest(), catalogueUploadFileDetailsDTOList);
		log.info("API searchCatalogues ended at " + LocalTime.now());
		return ResponseEntity.ok().headers(headers).body(catalogueUploadFileDetailsDTOList.getContent());
	}

	/**
	 * Upload catalogue file process.
	 *
	 * @param message the message
	 * @throws Exception the exception
	 */
	public void uploadCatalogueFileProcess(String message) throws Exception {
		String catalogueCode = message.split("\\|")[0];
		Long catUploadFileId = Long.valueOf(message.split("\\|")[1]);
		String fileName = message.split("\\|")[2];

		try {

			log.debug("File processing started----------- " + message);

			Catalogue catalogue = catalogueRepository.findByCatalogueCode(catalogueCode);
			CatalogueUploadFile catFile = catalogueUploadFileRepository.findById(catUploadFileId).get();
			log.debug("Getting Catalogue Upload file -----------" + catFile.getFileName());
			byte[] fileData = catalogueDocumentStore.getObject(ReferenceTypes.CATALOGUE_UPLOAD_FILE, catFile.getId());
			// FileInputStream file = new FileInputStream(new FileInputStream(fileData));
			InputStream is = new ByteArrayInputStream(fileData);
			DataFormatter formatter = new DataFormatter();
			XSSFWorkbook myWorkBook = null;
			myWorkBook = new XSSFWorkbook(is);
			XSSFSheet currentSheet = myWorkBook.getSheetAt(0);
			log.debug("Sheet Count ---------> " + currentSheet);
			if (!emptyRowCheck(currentSheet.getRow(0))) {
				for (Row row : currentSheet) {
					if (row.getRowNum() == 0) {
						continue;
					}
					if (!emptyRowCheck(row)) {
						String errorMessage = "";
						String itemCode = row.getCell(1).getStringCellValue();
						String itemName = row.getCell(2).getStringCellValue();
						String unit = formatter.formatCellValue(row.getCell(3));
						Long uomId = null;
						String categoryCode = formatter.formatCellValue(row.getCell(4));

						if (itemCode != null && !itemCode.isBlank()) {
							if (catalogueItemService.checkItemCodeExist(itemCode).isPresent()) {
								// errorMessage +=
								// messageSourceUtility.getMessage("CatalogueItem.uploadCatalogueFileProcess."+"itemCodeExists");
								errorMessage += "Item Code Already Exists | ";

							}
							if (itemCode.length() > 25) {
								// errorMessage +=
								// messageSourceUtility.getMessage("CatalogueItem.uploadCatalogueFileProcess."+"itemCodeLength");
								errorMessage += "Item Code cannot be greater than 25 characters | ";
							}
							Pattern pattern = Pattern.compile("[^a-zA-Z0-9 _[-()/]]");
							if (pattern.matcher(itemCode).find()) {
								// errorMessage +=
								// messageSourceUtility.getMessage("CatalogueItem.uploadCatalogueFileProcess."+"itemCodePattern");
								errorMessage += "Item Code cannot have special characters or spaces | ";
							}
						} else {
							// errorMessage +=
							// messageSourceUtility.getMessage("CatalogueItem.uploadCatalogueFileProcess."+"itemCodeBlank");
							errorMessage += "ItemCode field is Blank | ";
						}

						if (itemName != null && !itemName.isBlank()) {
							if (catalogueItemService.checkItemNameExist(itemName).isPresent()) {
								// errorMessage +=
								// messageSourceUtility.getMessage("CatalogueItem.uploadCatalogueFileProcess."+"itemNameExists");
								errorMessage += "Item Name Already Exists | ";
							}
						} else {
							// errorMessage +=
							// messageSourceUtility.getMessage("CatalogueItem.uploadCatalogueFileProcess."+"itemNameBlank");
							errorMessage += "ItemName field is Blank | ";
						}

						if (!StringUtils.hasText(unit)) {
							// errorMessage +=
							// messageSourceUtility.getMessage("CatalogueItem.uploadCatalogueFileProcess."+"uomBlank");
							errorMessage += "Uom is blank | ";
						} else {
							try {
								ResponseEntity<List<UomDTO>> response = uomControllerApiClient
										.getAllActiveUomsByNameUsingGET(unit);
								List<UomDTO> masterUomList = response.getBody();
								uomId = masterUomList.get(0).getId().longValue();
								unit = masterUomList.get(0).getName();
							} catch (FeignClientException e) {
								if (e.status() == 404) {
									errorMessage += "Incorrect Uom Name | ";
								} else {
									errorMessage += "Error fetching Uom | ";
								}
							}
						}

						List<CategoryDetailsDTO> categoryDetailsList = new ArrayList<>();
						if (StringUtils.hasText(categoryCode)) {
							Optional<Category> categoryOptional = categoryRepository
									.findByCategoryCodeAndCatalogue(categoryCode, catalogue);
							categoryOptional.ifPresent(category -> {
								CategoryDetailsDTO catDetailsDTO = new CategoryDetailsDTO();
								catDetailsDTO.setCategoryCode(category.getCategoryCode());
								catDetailsDTO.setCategoryName(category.getCategoryName());
								categoryDetailsList.add(catDetailsDTO);
							});
							if (categoryOptional.isEmpty()) {
								errorMessage += "Category not found for the given code or Catalogue | ";
							}
						} else {
							errorMessage += "Category Code is not present | ";
						}

						if (!StringUtils.hasText(errorMessage)) {

							CatalogueItemDTO catalogItemDTO = new CatalogueItemDTO();
							catalogItemDTO.setCatalogueCode(catalogue.getCatalogueCode());
							catalogItemDTO.setCatalogueName(catalogue.getCatalogueName());
							catalogItemDTO.setItemCode(itemCode);
							catalogItemDTO.setItemName(itemName);
							catalogItemDTO.setUomName(unit);
							catalogItemDTO.setUomId(uomId);
							catalogItemDTO.setSpecifications(new HashMap<>());
							catalogItemDTO.setDeptId(catalogue.getDeptId());
							catalogItemDTO.setDeptName(catalogue.getDeptName());
							catalogItemDTO.setCategories(categoryDetailsList);
							catalogItemDTO.setUnspscDetails(new ArrayList<>());
							catalogItemDTO.setActiveYn(true);
							catalogItemDTO = catalogueItemService.save(catalogItemDTO);
							// generate item code

							CatalogueUploadFileDetailsDTO catItemDetailsDTO = new CatalogueUploadFileDetailsDTO();
							catItemDetailsDTO.setCatalogueFileId(catFile.getId());
							catItemDetailsDTO.setCatalogueCode(catalogue.getCatalogueCode());
							catItemDetailsDTO.setItemCode(itemCode);
							catItemDetailsDTO.setItemName(itemName);
							catItemDetailsDTO.setUom(unit);
							catItemDetailsDTO.setCategoryCode(categoryCode);
							catItemDetailsDTO.setStatus(SUCCESS);
							catItemDetailsDTO.setErrorReason("");

							catalogueUploadFileService.saveCatalogueUploadFileDetails(catItemDetailsDTO);

						} else {

							if (catFile != null && !catFile.getStatus().equals(FAILED)) {

								catFile.setFileName(fileName);
								catFile.setStatus(FAILED);
								catFile = catalogueUploadFileRepository.save(catFile);

							}

							CatalogueUploadFileDetails catItemDetails = new CatalogueUploadFileDetails();
							catItemDetails.setCatalogueFileId(catFile.getId());
							catItemDetails.setCatalogueCode(catalogue.getCatalogueCode());
							catItemDetails.setItemCode(itemCode);
							catItemDetails.setItemName(itemName);
							catItemDetails.setUom(unit);
							catItemDetails.setCategoryCode(categoryCode);
							catItemDetails.setStatus(FAILED);
							catItemDetails.setErrorReason(errorMessage.substring(0, errorMessage.length() - 3));

							catalogueUploadFileDetailsRepository.save(catItemDetails);
						}
					}
				}
				catFile.setFileName(fileName);
				catFile.setStatus(SUCCESS);
				catFile = catalogueUploadFileRepository.save(catFile);
			} else {
				catFile.setStatus(FAILED);
				throw new BadRequestAlertException("File cannot be Empty", fileName, ENTITY_NAME);
			}
		} catch (Exception e) {
			CatalogueUploadFile catFile = catalogueUploadFileRepository.findById(catUploadFileId).get();
			catFile.setFileName(fileName);
			catFile.setStatus(FAILED);
			catFile = catalogueUploadFileRepository.save(catFile);
		}
	}

	/**
	 * Empty row check.
	 *
	 * @param row the row
	 * @return true, if successful
	 */
	private boolean emptyRowCheck(Row row) {
		List<Boolean> flag = new ArrayList<Boolean>();
		if (row == null) {
			return true;
		}
//		if (row.getLastCellNum() <= 0) {
//			return true;
//		}
		if (row.getLastCellNum() >= 2) {
			row.forEach(cell -> {
				if (cell != null && !cell.toString().trim().equalsIgnoreCase("")) {
					flag.add(false);
				}
			});

			if (!(flag.size() > 0)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the Catalogue FileUpload Errors.
	 *
	 * @param catalogueUploadFileId the catalogue upload file id
	 * @return failed_catalogue_items.xlsx
	 * @throws IOException the IO exception
	 */
	@GetMapping("/catalogue-upload/{id}/download-errors")
	public ResponseEntity<?> getCatalogueFileUploadErrors(@PathVariable("id") @NotNull Long catalogueUploadFileId)
			throws IOException {

		log.info("API getCatalogueFileUploadErrors started at " + LocalTime.now());
		List<CatalogueUploadFileExcelTemplate> catalogueItemFileExcelTemplate = new ArrayList<>();
		String errorMessage = "CatalogueItem.getCatalogueFileUploadErrors.";
		Optional<CatalogueUploadFile> optionalUploadFileId = catalogueUploadFileRepository
				.findById(catalogueUploadFileId);
		if (!optionalUploadFileId.isPresent()) {
			throw new RecordNotFoundException(messageSourceUtility.getMessage(errorMessage + "notPresent"),
					String.valueOf(catalogueUploadFileId));
		}
		List<CatalogueUploadFileDetails> loadErrorDetails = catalogueUploadFileService
				.loadErrorDetails(optionalUploadFileId.get().getId());
		if (loadErrorDetails.isEmpty()) {
			throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "noDataFound"),
					ENTITY_NAME, ENTITY_NAME);
		}
		AtomicInteger counter = new AtomicInteger(0);
		catalogueItemFileExcelTemplate = loadErrorDetails.stream().map(item -> {

			CatalogueUploadFileExcelTemplate catFileTemplate = new CatalogueUploadFileExcelTemplate();
			catFileTemplate.setsNo(Long.valueOf(counter.incrementAndGet()));
			catFileTemplate.setItemCode(item.getItemCode());
			catFileTemplate.setItemName(item.getItemName());
			catFileTemplate.setUnit(item.getUom());
			catFileTemplate.setCategoryCode(item.getCategoryCode());
			catFileTemplate.setStatus(item.getStatus());
			catFileTemplate.setErrorReason(item.getErrorReason());
			return catFileTemplate;
		}).collect(Collectors.toList());

		ByteArrayInputStream stream = CatalogueItemUploadFileExporter.toExcelFile(catalogueItemFileExcelTemplate);
		log.info("API getCatalogueFileUploadErrors ended at " + LocalTime.now());
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
				.header("Content-Disposition", "attachment; filename=failed_catalogue_items.xlsx")
				.body(new InputStreamResource(stream));
	}

	/** The class SortByLastModifiedTs. */
	class SortByLastModifiedTs implements Comparator<CatalogueItemDTO> {

		/**
		 * Compare.
		 *
		 * @param o1 the o 1
		 * @param o2 the o 2
		 * @return the int
		 */
		@Override
		public int compare(CatalogueItemDTO o1, CatalogueItemDTO o2) {
			if (o2.getLastModifiedTs().compareTo(o1.getLastModifiedTs()) == 0) {
				return o2.getId().compareTo(o1.getId());
			}
			return o2.getLastModifiedTs().compareTo(o1.getLastModifiedTs());
		}
	}

}
