package com.dxc.eproc.catalogue.dto.request;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import com.dxc.eproc.utils.RestUtilTest;

// TODO: Auto-generated Javadoc
/**
 * The Class CategoryDTOTest.
 */
public class CategoryDTOTest {

	/**
	 * Dto equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void dtoEqualsVerifier() throws Exception {

		RestUtilTest.equalsVerifier(CategoryDTO.class);

		CategoryDTO categoryDTO1 = new CategoryDTO();
		categoryDTO1.setId(1L);
		CategoryDTO categoryDTO2 = new CategoryDTO();
		Assertions.assertThat(categoryDTO1).isNotEqualTo(categoryDTO2);
		categoryDTO2.setId(categoryDTO1.getId());
		Assertions.assertThat(categoryDTO1.getId()).isEqualTo(categoryDTO2.getId());
		categoryDTO1.setId(2L);
		Assertions.assertThat(categoryDTO1).isNotEqualTo(categoryDTO2);
		categoryDTO1.setId(null);
		Assertions.assertThat(categoryDTO1).isNotEqualTo(categoryDTO2);

		categoryDTO1.setActiveYn(true);
		categoryDTO2.setActiveYn(true);
		Assertions.assertThat(categoryDTO1.getActiveYn()).isEqualTo(categoryDTO2.getActiveYn());

		CatalogueDTO catalogueDTO1 = new CatalogueDTO();
		catalogueDTO1.setCatalogueCode("CaaR");
		CatalogueDTO catalogueDTO2 = new CatalogueDTO();
		catalogueDTO2.setCatalogueCode("CaaR1");

		categoryDTO1.setCatalogue(catalogueDTO1);
		categoryDTO2.setCatalogue(catalogueDTO2);
		Assertions.assertThat(categoryDTO1.getCatalogue().getCatalogueCode())
				.isNotEqualTo(categoryDTO2.getCatalogue().getCatalogueCode());

		categoryDTO1.setCatalogueId(1L);
		categoryDTO2.setCatalogueId(1L);
		Assertions.assertThat(categoryDTO1.getCatalogueId()).isEqualTo(categoryDTO2.getCatalogueId());

		categoryDTO1.setCatalogueName("DDD");
		categoryDTO2.setCatalogueName("DDD222");
		Assertions.assertThat(categoryDTO1.getCatalogueName()).isNotEqualTo(categoryDTO2.getCatalogueName());

		categoryDTO1.setCategoryCode("CATSDT");
		categoryDTO2.setCategoryCode("CATSDT");
		Assertions.assertThat(categoryDTO1.getCategoryCode()).isEqualTo(categoryDTO2.getCategoryCode());

		categoryDTO1.setCategoryName("CATNAME");
		categoryDTO2.setCategoryName("CATNAME1");
		Assertions.assertThat(categoryDTO1.getCategoryName()).isNotEqualTo(categoryDTO2.getCategoryName());

		categoryDTO1.setDeptId(1);
		categoryDTO2.setDeptId(1);
		Assertions.assertThat(categoryDTO1.getDeptId()).isEqualTo(categoryDTO2.getDeptId());

		categoryDTO1.setDeptName("DNAME");
		categoryDTO2.setCategoryName("DNAME1");
		Assertions.assertThat(categoryDTO1.getDeptName()).isNotEqualTo(categoryDTO2.getDeptName());

		categoryDTO1.setVersion(0);
		categoryDTO2.setVersion(0);
		Assertions.assertThat(categoryDTO1.getVersion()).isEqualTo(categoryDTO2.getVersion());

	}
}
