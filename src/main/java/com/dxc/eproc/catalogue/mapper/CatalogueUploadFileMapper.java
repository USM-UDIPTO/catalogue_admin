package com.dxc.eproc.catalogue.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dxc.eproc.catalogue.dto.request.CatalogueUploadFileDTO;
import com.dxc.eproc.catalogue.model.CatalogueUploadFile;

// TODO: Auto-generated Javadoc
/**
 * Mapper for the entity {@link CatalogueUploadFile} and its DTO
 * {@link CatalogueUploadFileDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CatalogueUploadFileMapper extends EntityMapper<CatalogueUploadFileDTO, CatalogueUploadFile> {

	/**
	 * To entity.
	 *
	 * @param catalogueUploadFileDTO the catalogue upload file DTO
	 * @return the category
	 */
	@Mapping(source = "createdTs", target = "createdTs", dateFormat = "yyyy-MM-dd")
	CatalogueUploadFile toEntity(CatalogueUploadFileDTO catalogueUploadFileDTO);

	/**
	 * From id.
	 *
	 * @param id the id
	 * @return the category
	 */
	default CatalogueUploadFile fromId(Long id) {
		if (id == null) {
			return null;
		}
		CatalogueUploadFile catalogueUploadFile = new CatalogueUploadFile();
		catalogueUploadFile.setId(id);
		return catalogueUploadFile;
	}

}
