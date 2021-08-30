package com.dxc.eproc.catalogue.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dxc.eproc.catalogue.dto.request.CommodityMasterDTO;
import com.dxc.eproc.catalogue.model.CommodityMaster;

// TODO: Auto-generated Javadoc
/**
 * Mapper for the entity {@link CommodityMaster} and its DTO
 * {@link CommodityMasterDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CommodityMasterMapper extends EntityMapper<CommodityMasterDTO, CommodityMaster> {

	/**
	 * To entity.
	 *
	 * @param commodityMasterDTO the commodityMasterDTO
	 * @return the CommodityMaster
	 */
	CommodityMaster toEntity(CommodityMasterDTO commodityMasterDTO);

	/**
	 * To dto.
	 *
	 * @param commodityMaster the commodityMaster
	 * @return the commodity master DTO
	 */
	@Mapping(target = "classMaster", ignore = true)
	@Mapping(target = "classId", ignore = true)
	CommodityMasterDTO toDto(CommodityMaster commodityMaster);

	/**
	 * From id.
	 *
	 * @param id the id
	 * @return the commodity master
	 */
	default CommodityMaster fromId(Long id) {
		if (id == null) {
			return null;
		}
		CommodityMaster commodityMaster = new CommodityMaster();
		commodityMaster.setId(id);
		return commodityMaster;
	}

}
