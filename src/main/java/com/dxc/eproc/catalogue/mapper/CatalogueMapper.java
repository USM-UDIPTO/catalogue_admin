package com.dxc.eproc.catalogue.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dxc.eproc.catalogue.dto.request.CatalogueDTO;
import com.dxc.eproc.catalogue.model.Catalogue;

// TODO: Auto-generated Javadoc
/**
 * Mapper for the entity {@link Catalogue} and its DTO {@link CatalogueDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CatalogueMapper extends EntityMapper<CatalogueDTO, Catalogue> {

	/**
	 * To entity.
	 *
	 * @param catalogueDTO the catalogue DTO
	 * @return the Catalogue
	 */
	@Mapping(source = "createdTs", target = "createdTs", dateFormat = "yyyy-MM-dd")
	Catalogue toEntity(CatalogueDTO catalogueDTO);

	/**
	 * To dto.
	 *
	 * @param catalogue the catalogue
	 * @return the CatalogueDTO
	 */
	@Mapping(target = "categories", ignore = true)
	CatalogueDTO toDto(Catalogue catalogue);

	/**
	 * From id.
	 *
	 * @param id the id
	 * @return the catalogue
	 */
	default Catalogue fromId(Long id) {
		if (id == null) {
			return null;
		}
		Catalogue catalogue = new Catalogue();
		catalogue.setId(id);
		return catalogue;
	}
}
