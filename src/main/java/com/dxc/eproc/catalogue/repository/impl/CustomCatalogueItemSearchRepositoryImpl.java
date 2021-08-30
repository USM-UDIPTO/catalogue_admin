package com.dxc.eproc.catalogue.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.dxc.eproc.catalogue.dto.request.CatalogueItemDTO;
import com.dxc.eproc.catalogue.dto.request.CatalogueItemSearchDTO;
import com.dxc.eproc.catalogue.model.QCatalogueItem;
import com.dxc.eproc.catalogue.repository.CustomCatalogueItemSearchRepository;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomCatalogueItemSearchRepositoryImpl.
 */
public class CustomCatalogueItemSearchRepositoryImpl implements CustomCatalogueItemSearchRepository {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(CustomCatalogueItemSearchRepositoryImpl.class);

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Search catalogue item query DSL.
	 *
	 * @param pageable               the pageable
	 * @param catalogueItemSearchDto the catalogue item search dto
	 * @return the page
	 * @throws Exception the exception
	 */
	@Override
	public Page<CatalogueItemDTO> searchCatalogueItemQueryDSL(Pageable pageable,
			CatalogueItemSearchDTO catalogueItemSearchDto) throws Exception {

		QCatalogueItem qitem = QCatalogueItem.catalogueItem;
		JPAQuery<Tuple> query = new JPAQuery<Tuple>(entityManager);
		List<CatalogueItemDTO> catalogueItemDTOList = null;
		long total = 0;

		query.select(qitem.id, qitem.catalogueCode, qitem.catalogueName, qitem.itemCode, qitem.itemName, qitem.uomId,
				qitem.uomName, qitem.partNumber, qitem.deptId, qitem.deptName, qitem.activeYn, qitem.specifications)
				.from(qitem);

		if (StringUtils.hasText(catalogueItemSearchDto.getCatalogueCode())) {
			query.where(qitem.catalogueCode.contains(catalogueItemSearchDto.getCatalogueCode()));
		}

		if (StringUtils.hasText(catalogueItemSearchDto.getCatalogueItemCode())) {
			query.where(qitem.itemCode.contains(catalogueItemSearchDto.getCatalogueItemCode()));
		}

		if (StringUtils.hasText(catalogueItemSearchDto.getCatalogueItemName())) {
			query.where(qitem.itemName.contains(catalogueItemSearchDto.getCatalogueItemName()));
		}

		query.orderBy(qitem.lastModifiedTs.desc());
		if (pageable != null) {
			query.offset(pageable.getPageNumber() * pageable.getPageSize());
			query.limit(pageable.getPageSize());
		}

		total = query.fetchCount();
		log.debug("searchCatalogueItemQueryDSL count - " + total);
		List<Tuple> content = query.fetch();

		catalogueItemDTOList = new ArrayList<CatalogueItemDTO>();

		for (Tuple tuple : content) {
			CatalogueItemDTO dto = new CatalogueItemDTO();
			dto.setId(tuple.get(qitem.id));
			dto.setCatalogueCode(tuple.get(qitem.catalogueCode));
			dto.setCatalogueName(tuple.get(qitem.catalogueName));
			dto.setItemCode(tuple.get(qitem.itemCode));
			dto.setItemName(tuple.get(qitem.itemName));
			dto.setUomId(tuple.get(qitem.uomId));
			dto.setUomName(tuple.get(qitem.uomName));
			dto.setPartNumber(tuple.get(qitem.partNumber));
			dto.setDeptId(tuple.get(qitem.deptId));
			dto.setDeptName(tuple.get(qitem.deptName));
			dto.setActiveYn(tuple.get(qitem.activeYn));
			dto.setSpecifications(tuple.get(qitem.specifications));
			catalogueItemDTOList.add(dto);
		}

		return new PageImpl<CatalogueItemDTO>(catalogueItemDTOList, pageable, total);
	}

}
