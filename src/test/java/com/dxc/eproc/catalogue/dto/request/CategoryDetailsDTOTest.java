package com.dxc.eproc.catalogue.dto.request;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import com.dxc.eproc.utils.RestUtilTest;

// TODO: Auto-generated Javadoc
/**
 * The Class CategoryDetailsDTOTest.
 */
public class CategoryDetailsDTOTest {

	/**
	 * Dto equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void dtoEqualsVerifier() throws Exception {

		RestUtilTest.equalsVerifier(CategoryDetailsDTO.class);

		CategoryDetailsDTO categoryDetailsDTO1 = new CategoryDetailsDTO();
		categoryDetailsDTO1.setCategoryCode("TESTCAT");
		CategoryDetailsDTO categoryDetailsDTO2 = new CategoryDetailsDTO();
		Assertions.assertThat(categoryDetailsDTO1).isNotEqualTo(categoryDetailsDTO2);
		categoryDetailsDTO2.setCategoryCode(categoryDetailsDTO1.getCategoryCode());
		Assertions.assertThat(categoryDetailsDTO1.getCategoryCode()).isEqualTo(categoryDetailsDTO2.getCategoryCode());
		categoryDetailsDTO1.setCategoryName("TESTNAME");
		categoryDetailsDTO1.setCategoryName("TESTNE");
		Assertions.assertThat(categoryDetailsDTO1.getCategoryName())
				.isNotEqualTo(categoryDetailsDTO2.getCategoryName());
		categoryDetailsDTO1.setCategoryCode(null);
		Assertions.assertThat(categoryDetailsDTO1).isNotEqualTo(categoryDetailsDTO2);
	}
}
