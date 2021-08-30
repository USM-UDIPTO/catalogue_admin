package com.dxc.eproc.catalogue.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dxc.eproc.catalogue.dto.request.CatalogueUploadFileDetailsDTO;
import com.dxc.eproc.catalogue.model.CatalogueUploadFileDetails;

// TODO: Auto-generated Javadoc
/**
 * Mapper for the entity {@link CatalogueUploadFileDetails} and its DTO
 * {@link CatalogueUploadFileDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CatalogueUploadFileDetailsMapper
		extends EntityMapper<CatalogueUploadFileDetailsDTO, CatalogueUploadFileDetails> {

	/**
	 * To entity.
	 *
	 * @param catalogueUploadFileDetailsDTO the catalogue upload file details DTO
	 * @return the category
	 */
	@Mapping(source = "createdTs", target = "createdTs", dateFormat = "yyyy-MM-dd")
	CatalogueUploadFileDetails toEntity(CatalogueUploadFileDetailsDTO catalogueUploadFileDetailsDTO);

	/**
	 * From id.
	 *
	 * @param id the id
	 * @return the category
	 */
	default CatalogueUploadFileDetails fromId(Long id) {
		if (id == null) {
			return null;
		}
		CatalogueUploadFileDetails catalogueUploadFileDetails = new CatalogueUploadFileDetails();
		catalogueUploadFileDetails.setId(id);
		return catalogueUploadFileDetails;
	}

}
