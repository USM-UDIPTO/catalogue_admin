package com.dxc.eproc.catalogue.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.catalogue.dto.request.CategoryDTO;
import com.dxc.eproc.catalogue.model.Catalogue;
import com.dxc.eproc.catalogue.model.Category;
import com.querydsl.core.BooleanBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Interface CategoryService.
 */
public interface CategoryService {

	/**
	 * Saves or updates Category.
	 *
	 * @param category the category
	 * @return categoryDTO the categoryDTO
	 * @throws Exception the Exception
	 */
	public CategoryDTO saveOrUpdateCategory(Category category) throws Exception;

	/**
	 * Returns list of categories.
	 *
	 * @param pageable  the pageable
	 * @param catalogue the catalogue
	 * @param active    the active
	 * @return the list.
	 * @throws Exception the Exception
	 */
	public Page<CategoryDTO> getCategoriesForCatalogue(Pageable pageable, Catalogue catalogue, Boolean active)
			throws Exception;

	/**
	 * Returns Category object.
	 *
	 * @param catalogue  the catalogue
	 * @param categoryId the categoryId
	 * @return categoryDTO the categoryDTO
	 * @throws Exception the Exception
	 */
	public CategoryDTO getCategory(Catalogue catalogue, Long categoryId) throws Exception;

	/**
	 * Checks duplicate Category name or code.
	 *
	 * @param categoryDTO the categoryDTO
	 * @param codeOrName  the codeOrName
	 * @return boolean value
	 * @throws Exception the Exception
	 */
	public boolean categoryNameCodeCheck(CategoryDTO categoryDTO, String codeOrName) throws Exception;

	/**
	 * Search Categories.
	 *
	 * @param builder  the builder
	 * @param pageable the pageable
	 * @return CategoriesDTOList
	 * @throws Exception the Exception
	 */
	public Page<CategoryDTO> searchCategories(BooleanBuilder builder, Pageable pageable) throws Exception;

	/**
	 * Delete Category.
	 *
	 * @param categoryId the categoryId
	 * @throws Exception the Exception
	 */
	public void deleteCategory(Long categoryId) throws Exception;

}