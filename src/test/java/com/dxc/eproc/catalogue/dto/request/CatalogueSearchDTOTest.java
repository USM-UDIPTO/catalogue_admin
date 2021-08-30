package com.dxc.eproc.catalogue.dto.request;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import com.dxc.eproc.utils.RestUtilTest;

// TODO: Auto-generated Javadoc
//TODO: Auto-generated Javadoc
/**
 * The Class CatalogueDTOTest.
 */
public class CatalogueSearchDTOTest {

	/**
	 * Dto equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void dtoEqualsVerifier() throws Exception {

		RestUtilTest.equalsVerifier(CatalogueSearchDTO.class);

		CatalogueSearchDTO catalogueSearchDTO1 = new CatalogueSearchDTO();
		catalogueSearchDTO1.setCatalogueCode("TEST");
		CatalogueSearchDTO catalogueSearchDTO2 = new CatalogueSearchDTO();
		Assertions.assertThat(catalogueSearchDTO1).isNotEqualTo(catalogueSearchDTO2);
		catalogueSearchDTO2.setCatalogueCode(catalogueSearchDTO1.getCatalogueCode());
		Assertions.assertThat(catalogueSearchDTO1.getCatalogueCode()).isEqualTo(catalogueSearchDTO2.getCatalogueCode());
		catalogueSearchDTO1.setCatalogueName("TESTNAME");
		Assertions.assertThat(catalogueSearchDTO1.getCatalogueName())
				.isNotEqualTo(catalogueSearchDTO2.getCatalogueName());
		catalogueSearchDTO1.setCatalogueName(null);
		Assertions.assertThat(catalogueSearchDTO1).isNotEqualTo(catalogueSearchDTO2);

		catalogueSearchDTO1.setDeptId(1);
		catalogueSearchDTO2.setDeptId(2);
		Assertions.assertThat(catalogueSearchDTO1.getDeptId()).isNotEqualTo(catalogueSearchDTO2.getDeptId());

	}
}
