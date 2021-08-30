package com.dxc.eproc.catalogue.dto.request;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import com.dxc.eproc.utils.RestUtilTest;

// TODO: Auto-generated Javadoc
//TODO: Auto-generated Javadoc
/**
 * The Class CatalogueDTOTest.
 */
public class CatalogueItemSearchDTOTest {

	/**
	 * Dto equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void dtoEqualsVerifier() throws Exception {

		RestUtilTest.equalsVerifier(CatalogueItemSearchDTO.class);

		CatalogueItemSearchDTO catalogueItemSearchDTO1 = new CatalogueItemSearchDTO();
		catalogueItemSearchDTO1.setCatalogueCode("TEST");
		CatalogueItemSearchDTO catalogueItemSearchDTO2 = new CatalogueItemSearchDTO();
		Assertions.assertThat(catalogueItemSearchDTO1).isNotEqualTo(catalogueItemSearchDTO2);
		catalogueItemSearchDTO2.setCatalogueCode(catalogueItemSearchDTO1.getCatalogueCode());
		Assertions.assertThat(catalogueItemSearchDTO1.getCatalogueCode())
				.isEqualTo(catalogueItemSearchDTO2.getCatalogueCode());
		catalogueItemSearchDTO1.setCatalogueItemCode("TESTItEM");
		Assertions.assertThat(catalogueItemSearchDTO1).isNotEqualTo(catalogueItemSearchDTO2);
		catalogueItemSearchDTO1.setCatalogueItemCode(null);
		Assertions.assertThat(catalogueItemSearchDTO1).isNotEqualTo(catalogueItemSearchDTO2);

		catalogueItemSearchDTO1.setCatalogueItemName("CITEM");
		catalogueItemSearchDTO2.setCatalogueItemName("CITEM");
		Assertions.assertThat(catalogueItemSearchDTO1.getCatalogueItemName())
				.isEqualTo(catalogueItemSearchDTO2.getCatalogueItemName());

		catalogueItemSearchDTO1.setCatalogueItemCode("C");
		catalogueItemSearchDTO2.setCatalogueItemCode("CIT");
		Assertions.assertThat(catalogueItemSearchDTO1.getCatalogueItemCode())
				.isNotEqualTo(catalogueItemSearchDTO2.getCatalogueItemCode());

		List<String> categoryCodeList1 = new ArrayList<>();
		categoryCodeList1.add("CAT1");
		categoryCodeList1.add("CAT2");
		List<String> categoryCodeList2 = new ArrayList<>();
		categoryCodeList2.add("CAT1");
		categoryCodeList2.add("CAT2");

		catalogueItemSearchDTO1.setCategoryCodeList(categoryCodeList1);
		catalogueItemSearchDTO2.setCategoryCodeList(categoryCodeList2);
		Assertions.assertThat(catalogueItemSearchDTO1.getCategoryCodeList())
				.isEqualTo(catalogueItemSearchDTO2.getCategoryCodeList());

	}
}
