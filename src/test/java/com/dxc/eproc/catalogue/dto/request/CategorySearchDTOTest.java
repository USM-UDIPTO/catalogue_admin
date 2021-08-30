package com.dxc.eproc.catalogue.dto.request;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import com.dxc.eproc.utils.RestUtilTest;

// TODO: Auto-generated Javadoc
/**
 * The Class CategorySearchDTOTest.
 */
public class CategorySearchDTOTest {

	/**
	 * Dto equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void dtoEqualsVerifier() throws Exception {

		RestUtilTest.equalsVerifier(CategorySearchDTO.class);

		CategorySearchDTO categorySearchDTO1 = new CategorySearchDTO();
		categorySearchDTO1.setDeptId(1);
		CategorySearchDTO categorySearchDTO2 = new CategorySearchDTO();
		Assertions.assertThat(categorySearchDTO1).isNotEqualTo(categorySearchDTO2);
		categorySearchDTO2.setDeptId(categorySearchDTO1.getDeptId());
		Assertions.assertThat(categorySearchDTO1.getDeptId()).isEqualTo(categorySearchDTO2.getDeptId());
		categorySearchDTO1.setDeptId(2);
		Assertions.assertThat(categorySearchDTO1).isNotEqualTo(categorySearchDTO2);
		categorySearchDTO1.setDeptId(null);
		Assertions.assertThat(categorySearchDTO1).isNotEqualTo(categorySearchDTO2);

		categorySearchDTO1.setCategoryCode("CAT");
		categorySearchDTO2.setCategoryCode("CAT");
		Assertions.assertThat(categorySearchDTO1.getCategoryCode()).isEqualTo(categorySearchDTO2.getCategoryCode());

		categorySearchDTO1.setCategoryName("CAT1");
		categorySearchDTO2.setCategoryName("CAT");
		Assertions.assertThat(categorySearchDTO1.getCategoryName()).isNotEqualTo(categorySearchDTO2.getCategoryName());

	}
}
