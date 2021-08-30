package com.dxc.eproc.catalogue.mapper;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

// TODO: Auto-generated Javadoc
//TODO: Auto-generated Javadoc
/**
 * The Class CatalogueUploadFileMapperTest.
 */
public class CatalogueUploadFileMapperTest {

	/** The CatalogueUploadFileMapper. */
	private CatalogueUploadFileMapper catalogueUploadFileMapper;

	/**
	 * Sets the up.
	 */
	@BeforeMethod
	public void setUp() {
		catalogueUploadFileMapper = new CatalogueUploadFileMapperImpl();
	}

	/**
	 * Test entity from id.
	 */
	@Test
	public void testEntityFromId() {
		Long id = 1L;
		Assertions.assertThat(catalogueUploadFileMapper.fromId(id).getId()).isEqualTo(id);
		Assertions.assertThat(catalogueUploadFileMapper.fromId(null)).isNull();
	}
}
