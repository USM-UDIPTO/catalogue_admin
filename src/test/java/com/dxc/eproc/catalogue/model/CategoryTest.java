package com.dxc.eproc.catalogue.model;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import com.dxc.eproc.utils.RestUtilTest;

// TODO: Auto-generated Javadoc
//TODO: Auto-generated Javadoc
/**
 * The Class CategoryTest.
 */
public class CategoryTest {

	/**
	 * Entity equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void dtoEqualsVerifier() throws Exception {

		RestUtilTest.equalsVerifier(Category.class);

		Category category1 = new Category();
		category1.setId(1L);
		Category category2 = new Category();
		Assertions.assertThat(category1).isNotEqualTo(category2);
		category2.setId(category1.getId());
		Assertions.assertThat(category1.getId()).isEqualTo(category2.getId());
		category2.setId(2L);
		Assertions.assertThat(category1).isNotEqualTo(category2);
		category2.setId(null);
		Assertions.assertThat(category1).isNotEqualTo(category2);

		category1.setActiveYn(true);
		category2.setActiveYn(true);
		Assertions.assertThat(category1.getActiveYn()).isEqualTo(category2.getActiveYn());

		Catalogue catalogue1 = new Catalogue();
		catalogue1.setCatalogueCode("CAT-1");
		catalogue1.setCatalogueName("CAT-2");
		category1.setCatalogue(catalogue1);

		Catalogue catalogue2 = new Catalogue();
		catalogue2.setCatalogueCode("CAT-1");
		catalogue2.setCatalogueName("CAT-2");
		category2.setCatalogue(catalogue2);

		Assertions.assertThat(category1.getCatalogue()).isNotEqualTo(category2.getCatalogue());

		category1.setCategoryCode("CCC");
		category1.setCategoryCode("DDD");
		Assertions.assertThat(category1.getCatalogue()).isNotEqualTo(category2.getCatalogue());

		category1.setCategoryName("AA");
		category2.setCategoryName("BB");
		Assertions.assertThat(category1.getCategoryName()).isNotEqualTo(category2.getCategoryName());

		category1.setDeptId(1);
		category2.setDeptId(2);
		Assertions.assertThat(category1.getDeptId()).isNotEqualTo(category2.getDeptId());

		category1.setDeptName("SAA");
		category2.setDeptName("SAA");
		Assertions.assertThat(category1.getDeptName()).isEqualTo(category2.getDeptName());
	}
}
