package com.dxc.eproc.catalogue.mapper;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

// TODO: Auto-generated Javadoc
//TODO: Auto-generated Javadoc
/**
 * The Class CategoryMapperTest.
 */
public class CategoryMapperTest {

	/** The CategoryMapper. */
	private CategoryMapper categoryMapper;

	/**
	 * Sets the up.
	 */
	@BeforeMethod
	public void setUp() {
		categoryMapper = new CategoryMapperImpl();
	}

	/**
	 * Test entity from id.
	 */
	@Test
	public void testEntityFromId() {
		Long id = 1L;
		Assertions.assertThat(categoryMapper.fromId(id).getId()).isEqualTo(id);
		Assertions.assertThat(categoryMapper.fromId(null)).isNull();
	}
}
