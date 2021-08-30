package com.dxc.eproc.catalogue.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dxc.eproc.catalogue.dto.request.SegmentMasterDTO;
import com.dxc.eproc.catalogue.model.SegmentMaster;

// TODO: Auto-generated Javadoc
/**
 * The Interface SegmentMasterMapper.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SegmentMasterMapper extends EntityMapper<SegmentMasterDTO, SegmentMaster> {

	/**
	 * To entity.
	 *
	 * @param segmentMasterDTO the segment master DTO
	 * @return the segment master
	 */
	SegmentMaster toEntity(SegmentMasterDTO segmentMasterDTO);

	/**
	 * To dto.
	 *
	 * @param segmentmaster the segmentmaster
	 * @return the segment master DTO
	 */
	@Mapping(target = "families", ignore = true)
	SegmentMasterDTO toDto(SegmentMaster segmentmaster);

	/**
	 * From id.
	 *
	 * @param id the id
	 * @return the segment master
	 */
	default SegmentMaster fromId(Long id) {
		if (id == null) {
			return null;
		}
		SegmentMaster segmentmaster = new SegmentMaster();
		segmentmaster.setId(id);
		return segmentmaster;
	}

}
