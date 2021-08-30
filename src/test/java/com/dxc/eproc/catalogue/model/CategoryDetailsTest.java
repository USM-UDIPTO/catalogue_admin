package com.dxc.eproc.catalogue.model;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import com.dxc.eproc.utils.RestUtilTest;

// TODO: Auto-generated Javadoc
//TODO: Auto-generated Javadoc
/**
 * The Class CategoryDetailsTest.
 */
public class CategoryDetailsTest {

	/**
	 * Entity equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void dtoEqualsVerifier() throws Exception {

		RestUtilTest.equalsVerifier(CategoryDetails.class);

		CategoryDetails categoryDetails1 = new CategoryDetails();
		categoryDetails1.setCategoryName("CATNAME");
		CategoryDetails categoryDetails2 = new CategoryDetails();
		Assertions.assertThat(categoryDetails1).isNotEqualTo(categoryDetails2);
		categoryDetails2.setCategoryName(categoryDetails1.getCategoryName());
		Assertions.assertThat(categoryDetails1.getCategoryName()).isEqualTo(categoryDetails2.getCategoryName());
		categoryDetails2.setCategoryCode("CAT001");
		Assertions.assertThat(categoryDetails1.getCategoryCode()).isNotEqualTo(categoryDetails2.getCategoryCode());
		categoryDetails2.setCategoryName(null);
		Assertions.assertThat(categoryDetails1).isNotEqualTo(categoryDetails2);

	}
}
