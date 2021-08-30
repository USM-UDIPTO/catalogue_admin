package com.dxc.eproc.catalogue.controller;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dxc.eproc.catalogue.config.EprocMessageSourceComponent;
import com.dxc.eproc.catalogue.dto.request.CategoryDTO;
import com.dxc.eproc.catalogue.dto.request.CategorySearchDTO;
import com.dxc.eproc.catalogue.mapper.CategoryMapper;
import com.dxc.eproc.catalogue.model.Catalogue;
import com.dxc.eproc.catalogue.model.CatalogueItem;
import com.dxc.eproc.catalogue.model.Category;
import com.dxc.eproc.catalogue.model.CategoryDetails;
import com.dxc.eproc.catalogue.model.QCategory;
import com.dxc.eproc.catalogue.repository.CatalogueItemRepository;
import com.dxc.eproc.catalogue.repository.CatalogueRepository;
import com.dxc.eproc.catalogue.repository.CategoryRepository;
import com.dxc.eproc.catalogue.services.CategoryService;
import com.dxc.eproc.exceptionhandling.BadRequestAlertException;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.dxc.eproc.utils.PaginationUtil;
import com.querydsl.core.BooleanBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Class CategoryController.
 */
@RestController
@RequestMapping("/v1/api/catalogue")
@Transactional
public class CategoryController {

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "Category";

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(CategoryController.class);

	/** The message source utility. */
	@Autowired
	private EprocMessageSourceComponent messageSourceUtility;

	/** The category repository. */
	@Autowired
	private CategoryRepository categoryRepository;

	/** The catalogue repository. */
	@Autowired
	private CatalogueRepository catalogueRepository;

	/** The catalogue item repository. */
	@Autowired
	private CatalogueItemRepository catalogueItemRepository;

	/** The category mapper. */
	@Autowired
	private CategoryMapper categoryMapper;

	/** The category service. */
	@Autowired
	private CategoryService categoryService;

	/**
	 * System takes categoryDTO and catalogueId as parameter and performs the
	 * following: Checks if categoryId is null, if not throws exception Checks if
	 * any catalogue is present having the catalogueId, if not throws Exception
	 * Checks if category name and code are unique, if not throws Exception If no
	 * exception occurs, then system creates a category object.
	 *
	 * @param categoryDTO the categoryDTO
	 * @param catalogueId the catalogueId
	 * @return categoryDTO the categoryDTO
	 * @throws Exception the Exception
	 */
	@PostMapping("/{id}/category")
	public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO,
			@PathVariable("id") @NotNull Long catalogueId) throws Exception {
		log.info("API createCategory started at " + LocalTime.now());
		String errorMessage = "Category.saveCategory.";
		if (categoryDTO.getId() != null) {
			throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "nullId"), ENTITY_NAME,
					"nullId");
		}
		Optional<Catalogue> catalogue = Optional.ofNullable(catalogueRepository.findById(catalogueId).orElseThrow(
				() -> new RecordNotFoundException(messageSourceUtility.getMessage(errorMessage + "invalidId"),
						ENTITY_NAME)));
		categoryDTO.setDeptId(catalogue.get().getDeptId());
		categoryDTO.setDeptName(catalogue.get().getDeptName());
		Category category = categoryMapper.toEntity(categoryDTO);
		category.setCatalogue(catalogue.get());
		if (categoryService.categoryNameCodeCheck(categoryDTO, "code")) {
			throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "codeExists"),
					ENTITY_NAME, "codeExists");
		}
		if (categoryService.categoryNameCodeCheck(categoryDTO, "name")) {
			throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "nameExists"),
					ENTITY_NAME, "nameExists");
		}
		categoryDTO = categoryService.saveOrUpdateCategory(category);
		categoryDTO.setCatalogueId(catalogue.get().getId());
		categoryDTO.setCatalogueName(catalogue.get().getCatalogueName());
		log.info("API createCategory ended at " + LocalTime.now());
		return ResponseEntity.status(HttpStatus.CREATED).body(categoryDTO);
	}

	/**
	 * System takes categoryDTO, catalogueId and categoryId as parameter and
	 * performs the following: Checks if any category is present having the
	 * categoryId, if not throws exception Checks if any catalogue is present having
	 * the catalogueId, if not throws Exception Checks if category name and code are
	 * unique, if not throws Exception If no exception occurs, then system updates
	 * the category object.
	 *
	 * @param categoryDTO the categoryDTO
	 * @param catalogueId the catalogueId
	 * @param categoryId  the categoryId
	 * @return categoryDTO the categoryDTO
	 * @throws Exception the Exception
	 */
	@PutMapping("/{id}/category/{categoryId}")
	public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
			@PathVariable("id") @NotNull Long catalogueId, @PathVariable("categoryId") @NotNull Long categoryId)
			throws Exception {
		log.info("API updateCategory started at " + LocalTime.now());
		String errorMessage = "Category.updateCategory.";
		Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RecordNotFoundException(
				messageSourceUtility.getMessage(errorMessage + "invalidCategoryId"), ENTITY_NAME));
		categoryDTO.setId(categoryId);
		Optional<Catalogue> catalogue = catalogueRepository.findById(catalogueId);
		if (!catalogue.isPresent()) {
			throw new RecordNotFoundException(messageSourceUtility.getMessage(errorMessage + "invalidCatalogueId"),
					ENTITY_NAME);
		}
		if (categoryItemLinked(category, catalogue.get().getCatalogueCode())) {
			throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "itemLinked"),
					ENTITY_NAME, "itemLinked");
		}
		categoryDTO.setDeptId(catalogue.get().getDeptId());
		categoryDTO.setDeptName(catalogue.get().getDeptName());
		category.setCatalogue(catalogue.get());
		if (categoryService.categoryNameCodeCheck(categoryDTO, "code")) {
			throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "codeExists"),
					ENTITY_NAME, "codeExists");
		}
		if (categoryService.categoryNameCodeCheck(categoryDTO, "name")) {
			throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "nameExists"),
					ENTITY_NAME, "nameExists");
		}
		BeanUtils.copyProperties(categoryDTO, category);
		categoryDTO = categoryService.saveOrUpdateCategory(category);
		categoryDTO.setCatalogueId(catalogue.get().getId());
		categoryDTO.setCatalogueName(catalogue.get().getCatalogueName());
		log.info("API updateCategory ended at " + LocalTime.now());
		return ResponseEntity.ok(categoryDTO);
	}

	/**
	 * Checks if any item is mapped to the category.
	 *
	 * @param category      the category
	 * @param catalogueCode the catalogueCode
	 * @return boolean value
	 */
	private boolean categoryItemLinked(Category category, String catalogueCode) {
		List<CatalogueItem> catalogueItems = catalogueItemRepository
				.findByCatalogueCodeOrderByLastModifiedTsDesc(catalogueCode);
		Set<CatalogueItem> catalogueItemSet = new HashSet<>();
		catalogueItemSet.addAll(catalogueItems.stream().filter(i -> {
			for (CategoryDetails catDetails : i.getCategories()) {
				if (catDetails.getCategoryCode().equalsIgnoreCase(category.getCategoryCode())) {
					return true;
				}
			}
			return false;
		}).collect(Collectors.toList()));
		if (!catalogueItemSet.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * System takes catalogueId as parameter and performs the following: Checks if
	 * any catalogue is present having the catalogueId, if not throws exception
	 * Checks if any category is present referencing the catalogue, if not throws
	 * exception Else returns a list of categoryDTO.
	 *
	 * @param pageable    the pageable
	 * @param catalogueId the catalogueId
	 * @param active      the activeYn
	 * @return CategoryDTOList the categoryDTOList.
	 * @throws Exception the Exception
	 */
	@GetMapping("/{id}/categories")
	public ResponseEntity<?> getCategoriesForCatalogue(Pageable pageable, @PathVariable("id") @NotNull Long catalogueId,
			Boolean active) throws Exception {
		log.info("API getCategoriesForCatalogue started at " + LocalTime.now());
		Page<CategoryDTO> categoriesDTOPage = new PageImpl<>(new ArrayList<>());
		String errorMessage = "Category.getCategoriesForCatalogue.";
		Optional<Catalogue> optionalCatalogue = catalogueRepository.findById(catalogueId);
		if (optionalCatalogue.isEmpty()) {
			throw new RecordNotFoundException(messageSourceUtility.getMessage(errorMessage + "invalidCatalogueId"),
					ENTITY_NAME);
		}
		if (Optional.ofNullable(active).isEmpty()) {
			categoriesDTOPage = categoryService.getCategoriesForCatalogue(pageable, optionalCatalogue.get(), null);
		} else {
			categoriesDTOPage = categoryService.getCategoriesForCatalogue(pageable, optionalCatalogue.get(), true);
		}
		if (categoriesDTOPage.isEmpty()) {
			throw new RecordNotFoundException(messageSourceUtility.getMessage(errorMessage + "noCategoriesFound"),
					ENTITY_NAME);
		}
		categoriesDTOPage = categoriesDTOPage.map(categoryDTO -> {
			categoryDTO.setCatalogueId(optionalCatalogue.get().getId());
			categoryDTO.setCatalogueName(optionalCatalogue.get().getCatalogueName());
			return categoryDTO;
		});
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), categoriesDTOPage);
		log.info("API getCategoriesForCatalogue ended at " + LocalTime.now());
		return ResponseEntity.ok().headers(headers).body(categoriesDTOPage.getContent());
	}

	/**
	 * Gets the categories by catalogue code.
	 *
	 * @param pageable      the pageable
	 * @param catalogueCode the catalogue code
	 * @return the categories by catalogue code
	 * @throws Exception the exception
	 */
	@GetMapping("/code/{catalogueCode}/categories")
	public ResponseEntity<?> getCategoriesByCatalogueCode(Pageable pageable,
			@PathVariable("catalogueCode") String catalogueCode) throws Exception {
		log.info("API getCategoriesByCatalogueCode started at " + LocalTime.now());
		String errorMessage = "Category.getCategoriesByCatalogueCode.";
		Catalogue catalogue = catalogueRepository.findByCatalogueCode(catalogueCode);
		if (Optional.ofNullable(catalogue).isEmpty()) {
			throw new RecordNotFoundException(messageSourceUtility.getMessage(errorMessage + "catalogueNotFound"),
					ENTITY_NAME);
		}
		Page<CategoryDTO> categoriesDTOPage = categoryService.getCategoriesForCatalogue(pageable, catalogue, null);
		if (categoriesDTOPage.isEmpty()) {
			throw new RecordNotFoundException(messageSourceUtility.getMessage(errorMessage + "noCategoriesFound"),
					ENTITY_NAME);
		}
		categoriesDTOPage = categoriesDTOPage.map(categoryDTO -> {
			categoryDTO.setCatalogueId(catalogue.getId());
			categoryDTO.setCatalogueName(catalogue.getCatalogueName());
			return categoryDTO;
		});
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), categoriesDTOPage);
		log.info("API getCategoriesByCatalogueCode ended at " + LocalTime.now());
		return ResponseEntity.ok().headers(headers).body(categoriesDTOPage.getContent());
	}

	/**
	 * System takes categorySearchDTO as parameter and perform the following: Checks
	 * if both category code and name is empty and throws Exception Else lists all
	 * Categories based on the parameter.
	 *
	 * @param pageable          the pageable
	 * @param catalogueId       the catalogue id
	 * @param categorySearchDTO the categorySearchDTO
	 * @return categoryDTOList the list
	 * @throws Exception the Exception
	 */
	@PostMapping("/{id}/category/search")
	public ResponseEntity<?> searchCategories(Pageable pageable, @PathVariable("id") @NotNull Long catalogueId,
			@Valid @RequestBody CategorySearchDTO categorySearchDTO) throws Exception {
		log.info("API searchCategories started at " + LocalTime.now());
		String errorMessage = "Category.searchCategory.";
		if (StringUtils.isBlank(categorySearchDTO.getCategoryCode())
				&& StringUtils.isBlank(categorySearchDTO.getCategoryName())) {
			throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "noValues"), ENTITY_NAME,
					"noValues");
		}
		Optional<Catalogue> optionalCatalogue = catalogueRepository.findById(catalogueId);
		if (optionalCatalogue.isEmpty()) {
			throw new RecordNotFoundException(messageSourceUtility.getMessage(errorMessage + "invalidCatalogueId"),
					ENTITY_NAME);
		}
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(QCategory.category.catalogue.eq(optionalCatalogue.get()));
		if (!StringUtils.isBlank(categorySearchDTO.getCategoryCode())) {
			builder.and(QCategory.category.categoryCode.containsIgnoreCase(categorySearchDTO.getCategoryCode()));
		}
		if (!StringUtils.isBlank(categorySearchDTO.getCategoryName())) {
			builder.and(QCategory.category.categoryName.containsIgnoreCase(categorySearchDTO.getCategoryName()));
		}
		builder.and(QCategory.category.deptId.eq(categorySearchDTO.getDeptId()));
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
				Sort.by(Sort.Direction.DESC, "lastModifiedTs"));
		Page<CategoryDTO> categoryDTOList = categoryService.searchCategories(builder, pageRequest);
		if (categoryDTOList.isEmpty()) {
			throw new RecordNotFoundException(messageSourceUtility.getMessage(errorMessage + "noRecords"), ENTITY_NAME);
		}
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), categoryDTOList);
		log.info("API searchCategories ended at " + LocalTime.now());
		return ResponseEntity.ok().headers(headers).body(categoryDTOList.getContent());
	}

	/**
	 * System takes catalogueId and categoryId as parameters and performs the
	 * following: Checks if any catalogue is present with the catalogueId, if not
	 * throws exception Checks if any category is present with the categoryId
	 * referencing the catalogue, if not throws exception Returns a categoryDTO
	 * object.
	 *
	 * @param catalogueId the catalogueId
	 * @param categoryId  the categoryId
	 * @return categoryDTO the categoryDTO
	 * @throws Exception the Exception
	 */
	@GetMapping("/{id}/category/{categoryId}")
	public ResponseEntity<?> getCategory(@PathVariable("id") @NotNull Long catalogueId,
			@PathVariable("categoryId") @NotNull Long categoryId) throws Exception {
		log.info("API getCategory started at " + LocalTime.now());
		String errorMessage = "Category.getCategory.";
		Optional<Catalogue> optionalCatalogue = catalogueRepository.findById(catalogueId);
		if (optionalCatalogue.isEmpty()) {
			throw new RecordNotFoundException(messageSourceUtility.getMessage(errorMessage + "invalidCatalogueId"),
					ENTITY_NAME);
		}
		CategoryDTO categoryDTO = categoryService.getCategory(optionalCatalogue.get(), categoryId);
		if (Optional.ofNullable(categoryDTO).isEmpty()) {
			throw new RecordNotFoundException(messageSourceUtility.getMessage(errorMessage + "noCategoryFound"),
					ENTITY_NAME);
		}
		categoryDTO.setCatalogueId(optionalCatalogue.get().getId());
		categoryDTO.setCatalogueName(optionalCatalogue.get().getCatalogueName());
		log.info("API getCategory ended at " + LocalTime.now());
		return ResponseEntity.ok(categoryDTO);
	}

	/**
	 * Sets activeYn to true or false based on parameter.
	 *
	 * @param catalogueId the catalogueId
	 * @param categoryId  the categoryId
	 * @param activeYn    the activeYn
	 * @return categoryDTO the categoryDTO
	 * @throws Exception the Exception
	 */
	@PutMapping("/{id}/category/{categoryId}/activeYn/{activeYn}")
	public ResponseEntity<?> setCategoryActiveYn(@PathVariable("id") @NotNull Long catalogueId,
			@PathVariable("categoryId") @NotNull Long categoryId, @PathVariable("activeYn") Boolean activeYn)
			throws Exception {
		log.info("setCategoryActiveYn started at " + LocalTime.now());
		String errorMessage = "Category.setCategoryActiveYn.";
		Catalogue catalogue = catalogueRepository.findById(catalogueId).orElseThrow(() -> new RecordNotFoundException(
				messageSourceUtility.getMessage(errorMessage + "invalidCatalogueId"), ENTITY_NAME));
		Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RecordNotFoundException(
				messageSourceUtility.getMessage(errorMessage + "invalidCategoryId"), ENTITY_NAME));
		category.setActiveYn(activeYn);
		CategoryDTO categoryDTO = categoryService.saveOrUpdateCategory(category);
		categoryDTO.setCatalogueId(catalogue.getId());
		categoryDTO.setCatalogueName(catalogue.getCatalogueName());
		log.info("setCategoryActiveYn ended at " + LocalTime.now());
		return ResponseEntity.ok(categoryDTO);
	}

	/**
	 * System will delete based on categoryId.
	 *
	 * @param catalogueId the catalogueId
	 * @param categoryId  the categoryId
	 * @return categoryDTO the categoryDTO
	 * @throws Exception the Exception
	 */
	@DeleteMapping("/{id}/category/{categoryId}")
	public ResponseEntity<?> deleteCategory(@PathVariable("id") @NotNull Long catalogueId,
			@PathVariable("categoryId") @NotNull Long categoryId) throws Exception {
		log.info("API deleteCategory started at " + LocalTime.now());
		String errorMessage = "Category.deleteCategory.";
		Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RecordNotFoundException(
				messageSourceUtility.getMessage(errorMessage + "invalidCategoryId"), ENTITY_NAME));
		Catalogue catalogue = catalogueRepository.findById(catalogueId).orElseThrow(() -> new RecordNotFoundException(
				messageSourceUtility.getMessage(errorMessage + "invalidCatalogueId"), ENTITY_NAME));
		if (categoryItemLinked(category, catalogue.getCatalogueCode())) {
			throw new BadRequestAlertException(messageSourceUtility.getMessage(errorMessage + "itemLinked"),
					ENTITY_NAME, "itemLinked");
		}
		categoryService.deleteCategory(categoryId);
		CategoryDTO categoryDTO = categoryMapper.toDto(category);
		categoryDTO.setCatalogueId(catalogueId);
		categoryDTO.setCatalogueName(catalogue.getCatalogueName());
		log.info("API deleteCategory ended at " + LocalTime.now());
		return ResponseEntity.ok(categoryDTO);
	}
}
