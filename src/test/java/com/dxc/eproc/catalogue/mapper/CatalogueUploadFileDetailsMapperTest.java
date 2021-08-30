package com.dxc.eproc.catalogue.mapper;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

// TODO: Auto-generated Javadoc
//TODO: Auto-generated Javadoc
/**
 * The Class CatalogueUploadFileDetailsMapperTest.
 */
public class CatalogueUploadFileDetailsMapperTest {

	/** The catalogueUploadFileDetailsMapper mapper. */
	private CatalogueUploadFileDetailsMapper catalogueUploadFileDetailsMapper;

	/**
	 * Sets the up.
	 */
	@BeforeMethod
	public void setUp() {
		catalogueUploadFileDetailsMapper = new CatalogueUploadFileDetailsMapperImpl();
	}

	/**
	 * Test entity from id.
	 */
	@Test
	public void testEntityFromId() {
		Long id = 1L;
		Assertions.assertThat(catalogueUploadFileDetailsMapper.fromId(id).getId()).isEqualTo(id);
		Assertions.assertThat(catalogueUploadFileDetailsMapper.fromId(null)).isNull();
	}
}
