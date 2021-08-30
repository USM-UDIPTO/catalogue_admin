package com.dxc.eproc.catalogue.model;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import com.dxc.eproc.utils.RestUtilTest;

// TODO: Auto-generated Javadoc
//TODO: Auto-generated Javadoc
/**
 * The Class CatalogueUploadFileExcelTemplateTest.
 */
public class CatalogueUploadFileExcelTemplateTest {

	/**
	 * Entity equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void dtoEqualsVerifier() throws Exception {

		RestUtilTest.equalsVerifier(CatalogueUploadFileExcelTemplate.class);

		CatalogueUploadFileExcelTemplate catalogueUploadFileDetailsTemplate1 = new CatalogueUploadFileExcelTemplate();
		catalogueUploadFileDetailsTemplate1.setsNo(1L);
		CatalogueUploadFileExcelTemplate catalogueUploadFileDetailsTemplate2 = new CatalogueUploadFileExcelTemplate();
		Assertions.assertThat(catalogueUploadFileDetailsTemplate1).isNotEqualTo(catalogueUploadFileDetailsTemplate2);
		catalogueUploadFileDetailsTemplate2.setsNo(catalogueUploadFileDetailsTemplate1.getsNo());
		Assertions.assertThat(catalogueUploadFileDetailsTemplate1.getsNo())
				.isEqualTo(catalogueUploadFileDetailsTemplate2.getsNo());
		catalogueUploadFileDetailsTemplate2.setsNo(2L);
		Assertions.assertThat(catalogueUploadFileDetailsTemplate1).isNotEqualTo(catalogueUploadFileDetailsTemplate2);
		catalogueUploadFileDetailsTemplate2.setsNo(null);
		Assertions.assertThat(catalogueUploadFileDetailsTemplate1).isNotEqualTo(catalogueUploadFileDetailsTemplate2);

		catalogueUploadFileDetailsTemplate1.setCategoryCode("CATEGORY1");
		catalogueUploadFileDetailsTemplate2.setCategoryCode("CATEGORY1");
		Assertions.assertThat(catalogueUploadFileDetailsTemplate1.getCategoryCode())
				.isEqualTo(catalogueUploadFileDetailsTemplate2.getCategoryCode());

		catalogueUploadFileDetailsTemplate1.setItemCode("CATITEM1");
		catalogueUploadFileDetailsTemplate2.setItemCode("CATITEM2");
		Assertions.assertThat(catalogueUploadFileDetailsTemplate1.getItemCode())
				.isNotEqualTo(catalogueUploadFileDetailsTemplate2.getItemCode());

		catalogueUploadFileDetailsTemplate1.setItemName("CATITEMNAME");
		catalogueUploadFileDetailsTemplate2.setItemName("CATITEMNAME");
		Assertions.assertThat(catalogueUploadFileDetailsTemplate1.getItemName())
				.isEqualTo(catalogueUploadFileDetailsTemplate2.getItemName());

		catalogueUploadFileDetailsTemplate1.setStatus("SUCCESS");
		catalogueUploadFileDetailsTemplate2.setStatus("FAILED");
		Assertions.assertThat(catalogueUploadFileDetailsTemplate1.getStatus())
				.isNotEqualTo(catalogueUploadFileDetailsTemplate2.getStatus());

		catalogueUploadFileDetailsTemplate1.setUnit("10");
		catalogueUploadFileDetailsTemplate2.setUnit("10");
		Assertions.assertThat(catalogueUploadFileDetailsTemplate1.getUnit())
				.isEqualTo(catalogueUploadFileDetailsTemplate2.getUnit());

		catalogueUploadFileDetailsTemplate1.setErrorReason("Item name already exists");
		catalogueUploadFileDetailsTemplate2.setErrorReason("Item name already exists");
		Assertions.assertThat(catalogueUploadFileDetailsTemplate1.getErrorReason())
				.isEqualTo(catalogueUploadFileDetailsTemplate2.getErrorReason());
	}
}
