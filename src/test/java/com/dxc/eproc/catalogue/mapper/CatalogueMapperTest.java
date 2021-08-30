package com.dxc.eproc.catalogue.mapper;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

// TODO: Auto-generated Javadoc
//TODO: Auto-generated Javadoc
/**
 * The Class CatalogueMapperTest.
 */
public class CatalogueMapperTest {

	/** The catalogue mapper. */
	private CatalogueMapper catalogueMapper;

	/**
	 * Sets the up.
	 */
	@BeforeMethod
	public void setUp() {
		catalogueMapper = new CatalogueMapperImpl();
	}

	/**
	 * Test entity from id.
	 */
	@Test
	public void testEntityFromId() {
		Long id = 1L;
		Assertions.assertThat(catalogueMapper.fromId(id).getId()).isEqualTo(id);
		Assertions.assertThat(catalogueMapper.fromId(null)).isNull();
	}
}
