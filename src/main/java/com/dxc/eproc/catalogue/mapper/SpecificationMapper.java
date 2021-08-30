package com.dxc.eproc.catalogue.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dxc.eproc.catalogue.dto.request.SpecificationDTO;
import com.dxc.eproc.catalogue.model.Specification;

// TODO: Auto-generated Javadoc
/**
 * Mapper for the entity {@link Specification} and its DTO
 * {@link SpecificationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SpecificationMapper extends EntityMapper<SpecificationDTO, Specification> {

	/**
	 * To entity.
	 *
	 * @param specificationDTO the dto
	 * @return the specification
	 */
	@Mapping(source = "createdTs", target = "createdTs", dateFormat = "yyyy-MM-dd")
	Specification toEntity(SpecificationDTO specificationDTO);

	/**
	 * From id.
	 *
	 * @param id the id
	 * @return the specification
	 */
	default Specification fromId(Long id) {
		if (id == null) {
			return null;
		}
		Specification specification = new Specification();
		specification.setId(id);
		return specification;
	}
}
