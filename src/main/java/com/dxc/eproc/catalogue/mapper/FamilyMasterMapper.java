package com.dxc.eproc.catalogue.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dxc.eproc.catalogue.dto.request.FamilyMasterDTO;
import com.dxc.eproc.catalogue.model.FamilyMaster;

// TODO: Auto-generated Javadoc
/**
 * The Interface FamilyMasterMapper.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FamilyMasterMapper extends EntityMapper<FamilyMasterDTO, FamilyMaster> {

	/**
	 * To entity.
	 *
	 * @param familyMasterDTO the family master DTO
	 * @return the family master
	 */
	FamilyMaster toEntity(FamilyMasterDTO familyMasterDTO);

	/**
	 * To dto.
	 *
	 * @param familyMaster the family master
	 * @return the family master DTO
	 */
	@Mapping(target = "segmentMaster", ignore = true)
	@Mapping(target = "segmentId", ignore = true)
	FamilyMasterDTO toDto(FamilyMaster familyMaster);

	/**
	 * From id.
	 *
	 * @param id the id
	 * @return the family master
	 */
	default FamilyMaster fromId(Long id) {
		if (id == null) {
			return null;
		}
		FamilyMaster familyMaster = new FamilyMaster();
		familyMaster.setId(id);
		return familyMaster;
	}

}
