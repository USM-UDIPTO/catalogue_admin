package com.dxc.eproc.catalogue.mapper;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface EntityMapper.
 *
 * @param <D> the generic type
 * @param <E> the element type
 */
public interface EntityMapper<D, E> {

	/**
	 * To entity.
	 *
	 * @param dto the dto
	 * @return the e
	 */
	E toEntity(D dto);

	/**
	 * To dto.
	 *
	 * @param entity the entity
	 * @return the d
	 */
	D toDto(E entity);

	/**
	 * To entity.
	 *
	 * @param dtoList the dto list
	 * @return the list
	 */
	List<E> toEntity(List<D> dtoList);

	/**
	 * To dto.
	 *
	 * @param entityList the entity list
	 * @return the list
	 */
	List<D> toDto(List<E> entityList);
}
