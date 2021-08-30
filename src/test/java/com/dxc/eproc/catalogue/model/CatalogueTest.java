package com.dxc.eproc.catalogue.model;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import com.dxc.eproc.utils.RestUtilTest;

// TODO: Auto-generated Javadoc
//TODO: Auto-generated Javadoc
/**
 * The Class CatalogueTest.
 */
public class CatalogueTest {

	/**
	 * Entity equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void dtoEqualsVerifier() throws Exception {

		RestUtilTest.equalsVerifier(Catalogue.class);

		Catalogue catalogue1 = new Catalogue();
		catalogue1.setId(1L);
		Catalogue catalogue2 = new Catalogue();
		Assertions.assertThat(catalogue1).isNotEqualTo(catalogue2);
		catalogue2.setId(catalogue1.getId());
		Assertions.assertThat(catalogue1.getId()).isEqualTo(catalogue2.getId());
		catalogue2.setId(2L);
		Assertions.assertThat(catalogue1).isNotEqualTo(catalogue2);
		catalogue2.setId(null);
		Assertions.assertThat(catalogue1).isNotEqualTo(catalogue2);

		List<Category> category1 = new ArrayList<>();
		List<Category> category2 = new ArrayList<>();

		catalogue1.setCategories(category1);
		catalogue2.setCategories(category2);
		Assertions.assertThat(catalogue1.getCategories()).isEqualTo(catalogue2.getCategories());

		catalogue1.setDeptName("DEPT");
		catalogue2.setDeptName("DEPT");
		Assertions.assertThat(catalogue1.getDeptName()).isEqualTo(catalogue2.getDeptName());

		catalogue1.setDeptId(1);
		catalogue2.setDeptId(1);
		Assertions.assertThat(catalogue1.getDeptId()).isEqualTo(catalogue2.getDeptId());

		catalogue1.setCatalogueCode("CATE1");
		catalogue2.setCatalogueCode("CATE2");
		Assertions.assertThat(catalogue1.getCatalogueCode()).isNotEqualTo(catalogue2.getCatalogueCode());

		catalogue1.setCatalogueName("CATE1");
		catalogue2.setCatalogueName("CATE2");
		Assertions.assertThat(catalogue1.getCatalogueName()).isNotEqualTo(catalogue2.getCatalogueName());

		catalogue1.setCreatedBy("user");
		catalogue2.setCreatedBy("user");
		Assertions.assertThat(catalogue1.getCreatedBy()).isEqualTo(catalogue2.getCreatedBy());

		catalogue1.setLastModifiedBy("user");
		catalogue2.setLastModifiedBy("user");
		Assertions.assertThat(catalogue1.getLastModifiedBy()).isEqualTo(catalogue2.getLastModifiedBy());

		catalogue1.setActiveYn(true);
		catalogue2.setActiveYn(true);
		Assertions.assertThat(catalogue1.getDeptId()).isEqualTo(catalogue2.getDeptId());

		catalogue1.getCreatedTs();
		catalogue1.getLastModifiedTs();
	}
}
