package com.dxc.eproc.catalogue.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dxc.eproc.catalogue.dto.request.SegmentMasterDTO;
import com.dxc.eproc.catalogue.mapper.SegmentMasterMapper;
import com.dxc.eproc.catalogue.model.SegmentMaster;
import com.dxc.eproc.catalogue.repository.SegmentRepository;
import com.dxc.eproc.catalogue.services.SegmentService;

// TODO: Auto-generated Javadoc
/**
 * The Class SegmentServiceImpl.
 */
@Service
@Transactional
public class SegmentServiceImpl implements SegmentService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(SegmentServiceImpl.class);

	/** The segment repository. */
	@Autowired
	private SegmentRepository segmentRepository;

	/** The segment master mapper. */
	@Autowired
	private SegmentMasterMapper segmentMasterMapper;

	/**
	 * Gets the segment master.
	 *
	 * @param segmentId the segment id
	 * @return the segment master
	 * @throws Exception the exception
	 */
	@Override
	public Optional<SegmentMasterDTO> getSegmentMaster(Long segmentId) throws Exception {
		return segmentRepository.findById(segmentId).map(segmentMasterMapper::toDto);
	}

	/**
	 * Gets the segment master by code.
	 *
	 * @param code the code
	 * @return the segment master by code
	 * @throws Exception the exception
	 */
	@Override
	public Optional<SegmentMasterDTO> getSegmentMasterByCode(String code) throws Exception {
		return segmentRepository.findByCode(code).map(segmentMasterMapper::toDto);
	}

	/**
	 * Gets the all segments.
	 *
	 * @param pageable the pageable
	 * @return the all segments
	 * @throws Exception the exception
	 */
	@Override
	public Page<SegmentMasterDTO> getAllSegments(Pageable pageable) throws Exception {
		Page<SegmentMaster> segmentList = segmentRepository.findAll(pageable);
		long totalRows = segmentList.getTotalElements();
		List<SegmentMasterDTO> segmentDTOList = segmentList.stream().map(segment -> {
			return segmentMasterMapper.toDto(segment);
		}).collect(Collectors.toList());
		return new PageImpl<SegmentMasterDTO>(segmentDTOList, pageable, totalRows);
	}
}