package com.dxc.eproc.catalogue.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dxc.eproc.catalogue.dto.request.ClassMasterDTO;
import com.dxc.eproc.catalogue.model.ClassMaster;

// TODO: Auto-generated Javadoc
/**
 * The Interface ClassMasterMapper.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClassMasterMapper extends EntityMapper<ClassMasterDTO, ClassMaster> {

	/**
	 * To entity.
	 *
	 * @param classMasterDTO the classMasterDTO
	 * @return the ClassMaster
	 */
	ClassMaster toEntity(ClassMasterDTO classMasterDTO);

	/**
	 * To dto.
	 *
	 * @param classMaster the classMaster
	 * @return the ClassMasterDTO
	 */
	@Mapping(target = "commodities", ignore = true)
	@Mapping(target = "familyId", ignore = true)
	@Mapping(target = "familyMaster", ignore = true)
	ClassMasterDTO toDto(ClassMaster classMaster);

	/**
	 * From id.
	 *
	 * @param id the id
	 * @return the classMaster
	 */
	default ClassMaster fromId(Long id) {
		if (id == null) {
			return null;
		}
		ClassMaster classMaster = new ClassMaster();
		classMaster.setId(id);
		return classMaster;
	}
}
