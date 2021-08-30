package com.dxc.eproc.catalogue.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dxc.eproc.catalogue.dto.request.CatalogueItemDTO;
import com.dxc.eproc.catalogue.model.CatalogueItem;

// TODO: Auto-generated Javadoc
/**
 * Mapper for the entity {@link CatalogueItem} and its DTO
 * {@link CatalogueItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CatalogueItemMapper extends EntityMapper<CatalogueItemDTO, CatalogueItem> {

	/**
	 * To entity.
	 *
	 * @param catalogueItemDTO the dto
	 * @return the catalogueItem
	 */
	@Mapping(source = "createdTs", target = "createdTs", dateFormat = "yyyy-MM-dd")
	CatalogueItem toEntity(CatalogueItemDTO catalogueItemDTO);

	/**
	 * From id.
	 *
	 * @param id the id
	 * @return the catalogueItem
	 */
	default CatalogueItem fromId(Long id) {
		if (id == null) {
			return null;
		}
		CatalogueItem catalogueItem = new CatalogueItem();
		catalogueItem.setId(id);
		return catalogueItem;
	}
}
