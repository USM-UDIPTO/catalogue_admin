/**
 * 
 */
package com.dxc.eproc.catalogue.mapper;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

// TODO: Auto-generated Javadoc
//TODO: Auto-generated Javadoc
/**
 * The Class CatalogueItemMapperTest.
 */
public class CatalogueItemMapperTest {

	/** The catalogueItem mapper. */
	private CatalogueItemMapper catalogueItemMapper;

	/**
	 * Sets the up.
	 */
	@BeforeMethod
	public void setUp() {
		catalogueItemMapper = new CatalogueItemMapperImpl();
	}

	/**
	 * Test entity from id.
	 */
	@Test
	public void testEntityFromId() {
		Long id = 1L;
		Assertions.assertThat(catalogueItemMapper.fromId(id).getId()).isEqualTo(id);
		Assertions.assertThat(catalogueItemMapper.fromId(null)).isNull();
	}
}
