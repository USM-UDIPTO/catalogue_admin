package com.dxc.eproc.catalogue.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.catalogue.dto.request.SegmentMasterDTO;

// TODO: Auto-generated Javadoc
/**
 * The Interface SegmentService.
 */
public interface SegmentService {

	/**
	 * Gets the segment master.
	 *
	 * @param segmentId the segment id
	 * @return the segment master
	 * @throws Exception the exception
	 */
	public Optional<SegmentMasterDTO> getSegmentMaster(Long segmentId) throws Exception;

	/**
	 * Gets the segment master by code.
	 *
	 * @param code the code
	 * @return the segment master by code
	 * @throws Exception the exception
	 */
	public Optional<SegmentMasterDTO> getSegmentMasterByCode(String code) throws Exception;

	/**
	 * Gets the all segments.
	 *
	 * @param pageable the pageable
	 * @return the all segments
	 * @throws Exception the exception
	 */
	public Page<SegmentMasterDTO> getAllSegments(Pageable pageable) throws Exception;

}
