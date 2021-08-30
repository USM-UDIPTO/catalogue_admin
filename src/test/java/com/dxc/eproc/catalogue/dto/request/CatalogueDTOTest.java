package com.dxc.eproc.catalogue.dto.request;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import com.dxc.eproc.utils.RestUtilTest;

// TODO: Auto-generated Javadoc
//TODO: Auto-generated Javadoc
/**
 * The Class CatalogueDTOTest.
 */
public class CatalogueDTOTest {

	/**
	 * Dto equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void dtoEqualsVerifier() throws Exception {

		RestUtilTest.equalsVerifier(CatalogueDTO.class);

		CatalogueDTO catalogueDTO1 = new CatalogueDTO();
		catalogueDTO1.setId(1L);
		CatalogueDTO catalogueDTO2 = new CatalogueDTO();
		Assertions.assertThat(catalogueDTO1).isNotEqualTo(catalogueDTO2);
		catalogueDTO2.setId(catalogueDTO1.getId());
		Assertions.assertThat(catalogueDTO1.getId()).isEqualTo(catalogueDTO2.getId());
		catalogueDTO2.setId(2L);
		Assertions.assertThat(catalogueDTO1).isNotEqualTo(catalogueDTO2);
		catalogueDTO1.setId(null);
		Assertions.assertThat(catalogueDTO1).isNotEqualTo(catalogueDTO2);
		catalogueDTO1.setCategories(null);
		catalogueDTO1.getActiveYn();
		catalogueDTO1.getDeptName();
		catalogueDTO1.getCategories();
	}
}
