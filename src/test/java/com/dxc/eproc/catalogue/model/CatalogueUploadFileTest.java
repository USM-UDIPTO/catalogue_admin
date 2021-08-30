package com.dxc.eproc.catalogue.model;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import com.dxc.eproc.utils.RestUtilTest;

// TODO: Auto-generated Javadoc
//TODO: Auto-generated Javadoc
/**
 * The Class CatalogueUploadFileTest.
 */
public class CatalogueUploadFileTest {

	/**
	 * Entity equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void dtoEqualsVerifier() throws Exception {

		RestUtilTest.equalsVerifier(CatalogueUploadFile.class);

		CatalogueUploadFile catalogueUploadFile1 = new CatalogueUploadFile();
		catalogueUploadFile1.setId(1L);
		CatalogueUploadFile catalogueUploadFile2 = new CatalogueUploadFile();
		Assertions.assertThat(catalogueUploadFile1).isNotEqualTo(catalogueUploadFile2);
		catalogueUploadFile2.setId(catalogueUploadFile1.getId());
		Assertions.assertThat(catalogueUploadFile1.getId()).isEqualTo(catalogueUploadFile2.getId());
		catalogueUploadFile2.setId(2L);
		Assertions.assertThat(catalogueUploadFile1).isNotEqualTo(catalogueUploadFile2);
		catalogueUploadFile2.setId(null);
		Assertions.assertThat(catalogueUploadFile1).isNotEqualTo(catalogueUploadFile2);

		catalogueUploadFile1.setStatus("SUCCESS");
		catalogueUploadFile2.setStatus("FAILED");
		Assertions.assertThat(catalogueUploadFile1.getStatus()).isNotEqualTo(catalogueUploadFile2.getStatus());

		catalogueUploadFile1.setFileName("category.xsls");
		catalogueUploadFile2.setFileName("category.xsls");
		Assertions.assertThat(catalogueUploadFile1.getFileName()).isEqualTo(catalogueUploadFile2.getFileName());
	}
}
