package com.dxc.eproc.catalogue.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dxc.eproc.catalogue.dto.request.CategoryDTO;
import com.dxc.eproc.catalogue.model.Category;

// TODO: Auto-generated Javadoc
/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {

	/**
	 * To entity.
	 *
	 * @param categoryDTO the dto
	 * @return the category
	 */
	@Mapping(source = "createdTs", target = "createdTs", dateFormat = "yyyy-MM-dd")
	Category toEntity(CategoryDTO categoryDTO);

	/**
	 * To entity.
	 *
	 * @param category the entity
	 * @return the category
	 */
	@Mapping(target = "catalogue", ignore = true)
	@Mapping(target = "catalogueId", ignore = true)
	@Mapping(target = "catalogueName", ignore = true)
	CategoryDTO toDto(Category category);

	/**
	 * From id.
	 *
	 * @param id the id
	 * @return the category
	 */
	default Category fromId(Long id) {
		if (id == null) {
			return null;
		}
		Category category = new Category();
		category.setId(id);
		return category;
	}

}
