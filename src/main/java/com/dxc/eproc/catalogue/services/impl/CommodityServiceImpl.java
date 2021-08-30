package com.dxc.eproc.catalogue.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dxc.eproc.catalogue.dto.request.CommodityMasterDTO;
import com.dxc.eproc.catalogue.mapper.CommodityMasterMapper;
import com.dxc.eproc.catalogue.model.ClassMaster;
import com.dxc.eproc.catalogue.model.CommodityMaster;
import com.dxc.eproc.catalogue.repository.CommodityRepository;
import com.dxc.eproc.catalogue.services.CommodityService;

// TODO: Auto-generated Javadoc
/**
 * The Class CommodityServiceImpl.
 */
@Service
@Transactional
public class CommodityServiceImpl implements CommodityService {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(CommodityServiceImpl.class);

	/** The CommodityMaster repository. */
	@Autowired
	private CommodityRepository commodityRepository;

	/** The CommodityMaster mapper. */
	@Autowired
	private CommodityMasterMapper commodityMasterMapper;

	/**
	 * Gets the commodity.
	 *
	 * @param commodityMasterId the commodity master id
	 * @return the commodity
	 * @throws Exception the exception
	 */
	@Override
	public Optional<CommodityMasterDTO> getCommodityMaster(Long commodityMasterId) throws Exception {
		return commodityRepository.findById(commodityMasterId).map(commodity -> {
			CommodityMasterDTO commodityMasterDto = commodityMasterMapper.toDto(commodity);
			commodityMasterDto.setClassId(commodity.getClassMaster().getId());
			return commodityMasterDto;
		});
	}

	/**
	 * Gets the commodity master by code.
	 *
	 * @param code the code
	 * @return the commodity master by code
	 * @throws Exception the exception
	 */
	@Override
	public Optional<CommodityMasterDTO> getCommodityMasterByCode(String code) throws Exception {
		return commodityRepository.findByCode(code).map(commodity -> {
			CommodityMasterDTO commodityMasterDto = commodityMasterMapper.toDto(commodity);
			commodityMasterDto.setClassId(commodity.getClassMaster().getId());
			return commodityMasterDto;
		});
	}

	/**
	 * Save or update CommodityMaster.
	 *
	 * @param pageable    the pageable
	 * @param classMaster the class master
	 * @return the CommodityMaster DTO
	 * @throws Exception the exception
	 */
	@Override
	public Page<CommodityMasterDTO> getAllCommodities(Pageable pageable, ClassMaster classMaster) throws Exception {
		Page<CommodityMaster> commodityList = commodityRepository.findByClassMaster(pageable, classMaster);
		long totalRows = commodityList.getTotalElements();
		List<CommodityMasterDTO> commodityDTOList = commodityList.stream().map(commodity -> {
			CommodityMasterDTO commodityMasterDto = commodityMasterMapper.toDto(commodity);
			commodityMasterDto.setClassId(commodity.getClassMaster().getId());
			return commodityMasterDto;
		}).collect(Collectors.toList());
		return new PageImpl<CommodityMasterDTO>(commodityDTOList, pageable, totalRows);
	}
}
