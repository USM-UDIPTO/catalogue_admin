package com.dxc.eproc.catalogue.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.catalogue.dto.request.CategoryDTO;
import com.dxc.eproc.catalogue.mapper.CategoryMapper;
import com.dxc.eproc.catalogue.model.Catalogue;
import com.dxc.eproc.catalogue.model.Category;
import com.dxc.eproc.catalogue.repository.CatalogueRepository;
import com.dxc.eproc.catalogue.repository.CategoryRepository;
import com.dxc.eproc.catalogue.services.CategoryService;
import com.querydsl.core.BooleanBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Class CategoryServiceImpl.
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	/** The category repository. */
	@Autowired
	private CategoryRepository categoryRepository;

	/** The catalogue repository. */
	@Autowired
	private CatalogueRepository catalogueRepository;

	/** The category mapper. */
	@Autowired
	private CategoryMapper categoryMapper;

	/**
	 * Save or update category.
	 *
	 * @param category the category
	 * @return the category DTO
	 * @throws Exception the exception
	 */
	@Override
	public CategoryDTO saveOrUpdateCategory(Category category) throws Exception {
		category = categoryRepository.save(category);
		return categoryMapper.toDto(category);
	}

	/**
	 * Gets the categories for catalogue.
	 *
	 * @param pageable  the pageable
	 * @param catalogue the catalogue
	 * @param active    the active
	 * @return the categories for catalogue
	 * @throws Exception the exception
	 */
	@Override
	public Page<CategoryDTO> getCategoriesForCatalogue(Pageable pageable, Catalogue catalogue, Boolean active)
			throws Exception {
		Page<Category> categoriesList = new PageImpl<>(new ArrayList<>());

		if (active == null) {
			categoriesList = categoryRepository.findByCatalogueOrderByLastModifiedTsDesc(pageable, catalogue);
		} else {
			categoriesList = categoryRepository.findByCatalogueAndActiveYnOrderByLastModifiedTsDesc(pageable, catalogue,
					active);
		}
		long totalRows = categoriesList.getTotalElements();
		List<CategoryDTO> categoryDTOList = categoriesList.stream().map(category -> {
			return categoryMapper.toDto(category);
		}).collect(Collectors.toList());
		return new PageImpl<CategoryDTO>(categoryDTOList, pageable, totalRows);
	}

	/**
	 * Search categories.
	 *
	 * @param builder  the builder
	 * @param pageable the pageable
	 * @return the page
	 * @throws Exception the exception
	 */
	@Override
	public Page<CategoryDTO> searchCategories(BooleanBuilder builder, Pageable pageable) throws Exception {
		Page<Category> categoriesList = categoryRepository.findAll(builder, pageable);
		long totalRows = categoriesList.getTotalElements();
		List<CategoryDTO> categoryDTOList = categoriesList.stream().map(category -> {
			Catalogue catalogue = catalogueRepository.findByDeptIdAndCatalogueCode(category.getDeptId(),
					category.getCatalogue().getCatalogueCode());
			CategoryDTO categoryDTO = categoryMapper.toDto(category);
			categoryDTO.setCatalogueId(catalogue.getId());
			categoryDTO.setCatalogueName(catalogue.getCatalogueName());
			return categoryDTO;
		}).collect(Collectors.toList());
		return new PageImpl<CategoryDTO>(categoryDTOList, pageable, totalRows);
	}

	/**
	 * Gets the category.
	 *
	 * @param catalogue  the catalogue
	 * @param categoryId the category id
	 * @return the category
	 * @throws Exception the exception
	 */
	@Override
	public CategoryDTO getCategory(Catalogue catalogue, Long categoryId) throws Exception {
		Category category = categoryRepository.findByCatalogueAndId(catalogue, categoryId);
		return categoryMapper.toDto(category);
	}

	/**
	 * Checks for duplicate Category Name or Code.
	 *
	 * @param categoryDTO the categoryDTO
	 * @param field       the field
	 * @return boolean value
	 * @throws Exception the Exception
	 */
	public boolean categoryNameCodeCheck(CategoryDTO categoryDTO, String field) throws Exception {
		Category category = null;
		if (field.equalsIgnoreCase("code")) {
			category = categoryRepository.findByDeptIdAndCategoryCode(categoryDTO.getDeptId(),
					categoryDTO.getCategoryCode());
		} else {
			category = categoryRepository.findByDeptIdAndCategoryName(categoryDTO.getDeptId(),
					categoryDTO.getCategoryName());
		}
		// save check
		if (categoryDTO.getId() == null) {
			if (Optional.ofNullable(category).isPresent()) {
				return true;
			}
		} else { // update check
			if (Optional.ofNullable(category).isPresent()) {
				if (!category.getId().equals(categoryDTO.getId())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Delete category.
	 *
	 * @param categoryId the category id
	 * @throws Exception the exception
	 */
	@Override
	public void deleteCategory(Long categoryId) throws Exception {
		categoryRepository.deleteById(categoryId);
	}
}
