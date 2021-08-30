package com.dxc.eproc.catalogue.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dxc.eproc.catalogue.dto.request.CommodityMasterDTO;
import com.dxc.eproc.catalogue.model.ClassMaster;

// TODO: Auto-generated Javadoc
/**
 * The Interface CommodityService.
 */
public interface CommodityService {

	/**
	 * Gets the commodity.
	 *
	 * @param commodityId the commodity id
	 * @return the commodity
	 * @throws Exception the exception
	 */
	public Optional<CommodityMasterDTO> getCommodityMaster(Long commodityId) throws Exception;

	/**
	 * Gets the commodity master by code.
	 *
	 * @param code the code
	 * @return the commodity master by code
	 * @throws Exception the exception
	 */
	public Optional<CommodityMasterDTO> getCommodityMasterByCode(String code) throws Exception;

	/**
	 * Gets the all commodities.
	 *
	 * @param pageable    the pageable
	 * @param classMaster the class master
	 * @return the all commodities
	 * @throws Exception the exception
	 */
	public Page<CommodityMasterDTO> getAllCommodities(Pageable pageable, ClassMaster classMaster) throws Exception;

}
