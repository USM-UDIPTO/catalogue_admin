package com.dxc.eproc.catalogue.mapper;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

// TODO: Auto-generated Javadoc
//TODO: Auto-generated Javadoc
/**
 * The Class SpecificationMapperTest.
 */
public class SpecificationMapperTest {

	/** The SpecificationMapper. */
	private SpecificationMapper specificationMapper;

	/**
	 * Sets the up.
	 */
	@BeforeMethod
	public void setUp() {
		specificationMapper = new SpecificationMapperImpl();
	}

	/**
	 * Test entity from id.
	 */
	@Test
	public void testEntityFromId() {
		Long id = 1L;
		Assertions.assertThat(specificationMapper.fromId(id).getId()).isEqualTo(id);
		Assertions.assertThat(specificationMapper.fromId(null)).isNull();
	}
}
