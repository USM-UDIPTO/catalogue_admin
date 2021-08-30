package com.dxc.eproc.catalogue.model;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import com.dxc.eproc.utils.RestUtilTest;

// TODO: Auto-generated Javadoc
//TODO: Auto-generated Javadoc
/**
 * The Class CatalogueUploadFileDetailsTest.
 */
public class CatalogueUploadFileDetailsTest {

	/**
	 * Entity equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void dtoEqualsVerifier() throws Exception {

		RestUtilTest.equalsVerifier(CatalogueUploadFileDetails.class);

		CatalogueUploadFileDetails catalogueUploadFileDetails1 = new CatalogueUploadFileDetails();
		catalogueUploadFileDetails1.setId(1L);
		CatalogueUploadFileDetails catalogueUploadFileDetails2 = new CatalogueUploadFileDetails();
		Assertions.assertThat(catalogueUploadFileDetails1).isNotEqualTo(catalogueUploadFileDetails2);
		catalogueUploadFileDetails2.setId(catalogueUploadFileDetails1.getId());
		Assertions.assertThat(catalogueUploadFileDetails1.getId()).isEqualTo(catalogueUploadFileDetails2.getId());
		catalogueUploadFileDetails2.setId(2L);
		Assertions.assertThat(catalogueUploadFileDetails1).isNotEqualTo(catalogueUploadFileDetails2);
		catalogueUploadFileDetails2.setId(null);
		Assertions.assertThat(catalogueUploadFileDetails1).isNotEqualTo(catalogueUploadFileDetails2);

		catalogueUploadFileDetails1.setCatalogueCode("CAT01");
		catalogueUploadFileDetails2.setCatalogueCode("CAT");
		Assertions.assertThat(catalogueUploadFileDetails1.getCatalogueCode())
				.isNotEqualTo(catalogueUploadFileDetails2.getCatalogueCode());

		catalogueUploadFileDetails1.setCatalogueFileId(1L);
		catalogueUploadFileDetails2.setCatalogueFileId(2L);
		Assertions.assertThat(catalogueUploadFileDetails1.getCatalogueFileId())
				.isNotEqualTo(catalogueUploadFileDetails2.getCatalogueFileId());

		catalogueUploadFileDetails1.setCategoryCode("CATEGORY1");
		catalogueUploadFileDetails2.setCategoryCode("CATEGORY1");
		Assertions.assertThat(catalogueUploadFileDetails1.getCategoryCode())
				.isEqualTo(catalogueUploadFileDetails2.getCategoryCode());

		catalogueUploadFileDetails1.setItemCode("CATITEM1");
		catalogueUploadFileDetails2.setItemCode("CATITEM2");
		Assertions.assertThat(catalogueUploadFileDetails1.getItemCode())
				.isNotEqualTo(catalogueUploadFileDetails2.getItemCode());

		catalogueUploadFileDetails1.setItemName("CATITEMNAME");
		catalogueUploadFileDetails2.setItemName("CATITEMNAME");
		Assertions.assertThat(catalogueUploadFileDetails1.getItemName())
				.isEqualTo(catalogueUploadFileDetails2.getItemName());

		catalogueUploadFileDetails1.setStatus("SUCCESS");
		catalogueUploadFileDetails2.setStatus("FAILED");
		Assertions.assertThat(catalogueUploadFileDetails1.getStatus())
				.isNotEqualTo(catalogueUploadFileDetails2.getStatus());

		catalogueUploadFileDetails1.setUom("10");
		catalogueUploadFileDetails2.setUom("10");
		Assertions.assertThat(catalogueUploadFileDetails1.getUom()).isEqualTo(catalogueUploadFileDetails2.getUom());

		catalogueUploadFileDetails1.setVersion(0);
		catalogueUploadFileDetails2.setVersion(1);
		Assertions.assertThat(catalogueUploadFileDetails1.getVersion())
				.isNotEqualTo(catalogueUploadFileDetails2.getVersion());

		catalogueUploadFileDetails1.setErrorReason("Item name already exists");
		catalogueUploadFileDetails2.setErrorReason("Item name already exists");
		Assertions.assertThat(catalogueUploadFileDetails1.getErrorReason())
				.isEqualTo(catalogueUploadFileDetails2.getErrorReason());
	}
}
