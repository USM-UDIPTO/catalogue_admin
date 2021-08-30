package com.dxc.eproc.catalogue.dto.request;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import com.dxc.eproc.utils.RestUtilTest;

// TODO: Auto-generated Javadoc
//TODO: Auto-generated Javadoc
/**
 * The Class CatalogueDTOTest.
 */
public class CatalogueUploadFileDetailsDTOTest {

	/**
	 * Dto equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void dtoEqualsVerifier() throws Exception {

		RestUtilTest.equalsVerifier(CatalogueUploadFileDetailsDTO.class);

		CatalogueUploadFileDetailsDTO catalogueUploadFileDetailsDTO1 = new CatalogueUploadFileDetailsDTO();
		catalogueUploadFileDetailsDTO1.setId(1L);
		CatalogueUploadFileDetailsDTO catalogueUploadFileDetailsDTO2 = new CatalogueUploadFileDetailsDTO();
		Assertions.assertThat(catalogueUploadFileDetailsDTO1).isNotEqualTo(catalogueUploadFileDetailsDTO2);
		catalogueUploadFileDetailsDTO2.setId(catalogueUploadFileDetailsDTO1.getId());
		Assertions.assertThat(catalogueUploadFileDetailsDTO1.getId()).isEqualTo(catalogueUploadFileDetailsDTO2.getId());
		catalogueUploadFileDetailsDTO1.setId(2L);
		Assertions.assertThat(catalogueUploadFileDetailsDTO1).isNotEqualTo(catalogueUploadFileDetailsDTO2);
		catalogueUploadFileDetailsDTO1.setId(null);
		Assertions.assertThat(catalogueUploadFileDetailsDTO1).isNotEqualTo(catalogueUploadFileDetailsDTO2);

		catalogueUploadFileDetailsDTO1.setCatalogueCode("CAT1");
		catalogueUploadFileDetailsDTO2.setCatalogueCode("CAT1");
		Assertions.assertThat(catalogueUploadFileDetailsDTO1.getCatalogueCode())
				.isEqualTo(catalogueUploadFileDetailsDTO2.getCatalogueCode());

		catalogueUploadFileDetailsDTO1.setCatalogueFileId(1L);
		catalogueUploadFileDetailsDTO2.setCatalogueFileId(2L);
		Assertions.assertThat(catalogueUploadFileDetailsDTO1.getCatalogueFileId())
				.isNotEqualTo(catalogueUploadFileDetailsDTO2.getCatalogueFileId());

		catalogueUploadFileDetailsDTO1.setCategoryCode("CATEGORY1");
		catalogueUploadFileDetailsDTO2.setCategoryCode("CATEGORY1");
		Assertions.assertThat(catalogueUploadFileDetailsDTO1.getCategoryCode())
				.isEqualTo(catalogueUploadFileDetailsDTO2.getCategoryCode());

		catalogueUploadFileDetailsDTO1.setItemCode("CATITEM1");
		catalogueUploadFileDetailsDTO2.setItemCode("CATITEM2");
		Assertions.assertThat(catalogueUploadFileDetailsDTO1.getItemCode())
				.isNotEqualTo(catalogueUploadFileDetailsDTO2.getItemCode());

		catalogueUploadFileDetailsDTO1.setItemName("CATITEMNAME");
		catalogueUploadFileDetailsDTO2.setItemName("CATITEMNAME");
		Assertions.assertThat(catalogueUploadFileDetailsDTO1.getItemName())
				.isEqualTo(catalogueUploadFileDetailsDTO2.getItemName());

		catalogueUploadFileDetailsDTO1.setStatus("SUCCESS");
		catalogueUploadFileDetailsDTO2.setItemName("FAILED");
		Assertions.assertThat(catalogueUploadFileDetailsDTO1.getStatus())
				.isNotEqualTo(catalogueUploadFileDetailsDTO2.getStatus());

		catalogueUploadFileDetailsDTO1.setUom("10");
		catalogueUploadFileDetailsDTO2.setUom("10");
		Assertions.assertThat(catalogueUploadFileDetailsDTO1.getUom())
				.isEqualTo(catalogueUploadFileDetailsDTO2.getUom());

		catalogueUploadFileDetailsDTO1.setVersion(0);
		catalogueUploadFileDetailsDTO2.setVersion(1);
		Assertions.assertThat(catalogueUploadFileDetailsDTO1.getVersion())
				.isNotEqualTo(catalogueUploadFileDetailsDTO2.getVersion());

		catalogueUploadFileDetailsDTO1.setErrorReason("Item name already exists");
		catalogueUploadFileDetailsDTO2.setErrorReason("Item name already exists");
		Assertions.assertThat(catalogueUploadFileDetailsDTO1.getErrorReason())
				.isEqualTo(catalogueUploadFileDetailsDTO2.getErrorReason());

	}
}
