package com.dxc.eproc.catalogue.model;

import org.assertj.core.api.Assertions;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.dxc.eproc.utils.RestUtilTest;

// TODO: Auto-generated Javadoc
//TODO: Auto-generated Javadoc
/**
 * The Class CatalogueItemTest.
 */
public class CatalogueItemTest {

	/**
	 * Entity equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Transactional
	public void dtoEqualsVerifier() throws Exception {

		RestUtilTest.equalsVerifier(CatalogueItem.class);

		CatalogueItem catalogueItem1 = new CatalogueItem();
		catalogueItem1.setId(1L);
		CatalogueItem catalogueItem2 = new CatalogueItem();
		Assertions.assertThat(catalogueItem1).isNotEqualTo(catalogueItem2);
		catalogueItem2.setId(catalogueItem1.getId());
		Assertions.assertThat(catalogueItem1.getId()).isEqualTo(catalogueItem2.getId());
		catalogueItem2.setId(2L);
		Assertions.assertThat(catalogueItem1).isNotEqualTo(catalogueItem2);
		catalogueItem2.setId(null);
		Assertions.assertThat(catalogueItem1).isNotEqualTo(catalogueItem2);

		catalogueItem1.setActiveYn(true);
		catalogueItem2.setActiveYn(false);
		Assertions.assertThat(catalogueItem1.getActiveYn()).isNotEqualTo(catalogueItem2.getActiveYn());

		catalogueItem1.setCatalogueCode("CAT1");
		catalogueItem2.setCatalogueCode("CAT1");
		Assertions.assertThat(catalogueItem1.getCatalogueCode()).isEqualTo(catalogueItem2.getCatalogueCode());

		catalogueItem1.setCatalogueName("CATNAME1");
		catalogueItem2.setCatalogueName("CATNAME1");
		Assertions.assertThat(catalogueItem1.getCatalogueName()).isEqualTo(catalogueItem2.getCatalogueName());

		catalogueItem1.setDeptId(1);
		catalogueItem2.setDeptId(2);
		Assertions.assertThat(catalogueItem1.getDeptId()).isNotEqualTo(catalogueItem2.getDeptId());

		catalogueItem1.setPartNumber("1");
		catalogueItem2.setPartNumber("SSA");
		Assertions.assertThat(catalogueItem1.getPartNumber()).isNotEqualTo(catalogueItem2.getPartNumber());

		catalogueItem1.setUomName("1");
		catalogueItem2.setUomName("2");
		Assertions.assertThat(catalogueItem1.getUomName()).isNotEqualTo(catalogueItem2.getUomName());

		catalogueItem1.getCreatedTs();
		catalogueItem1.getLastModifiedTs();

	}
}
