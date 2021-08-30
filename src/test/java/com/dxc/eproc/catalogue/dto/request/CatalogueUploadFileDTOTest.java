package com.dxc.eproc.catalogue.dto.request;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import com.dxc.eproc.utils.RestUtilTest;

// TODO: Auto-generated Javadoc
/**
 * The Class CatalogueUploadFileDTOTest.
 */
public class CatalogueUploadFileDTOTest {

	/**
	 * Dto equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void dtoEqualsVerifier() throws Exception {

		RestUtilTest.equalsVerifier(CatalogueUploadFileDTO.class);

		CatalogueUploadFileDTO catalogueUploadFileDTO1 = new CatalogueUploadFileDTO();
		catalogueUploadFileDTO1.setId(1L);
		CatalogueUploadFileDTO catalogueUploadFileDTO2 = new CatalogueUploadFileDTO();
		Assertions.assertThat(catalogueUploadFileDTO1).isNotEqualTo(catalogueUploadFileDTO2);
		catalogueUploadFileDTO2.setId(catalogueUploadFileDTO1.getId());
		Assertions.assertThat(catalogueUploadFileDTO1.getId()).isEqualTo(catalogueUploadFileDTO2.getId());
		catalogueUploadFileDTO1.setId(2L);
		Assertions.assertThat(catalogueUploadFileDTO1).isNotEqualTo(catalogueUploadFileDTO2);
		catalogueUploadFileDTO1.setId(null);
		Assertions.assertThat(catalogueUploadFileDTO1).isNotEqualTo(catalogueUploadFileDTO2);

		catalogueUploadFileDTO1.setFileName("catalogue_items.xlsx");
		catalogueUploadFileDTO2.setFileName("catalogue_items.xlsx");
		Assertions.assertThat(catalogueUploadFileDTO1.getFileName()).isEqualTo(catalogueUploadFileDTO2.getFileName());

		catalogueUploadFileDTO1.setStatus("SUCCESS");
		catalogueUploadFileDTO2.setFileName("FAILED");
		Assertions.assertThat(catalogueUploadFileDTO1.getStatus()).isNotEqualTo(catalogueUploadFileDTO2.getStatus());

		catalogueUploadFileDTO1.setVersion(0);
		catalogueUploadFileDTO2.setVersion(0);
		Assertions.assertThat(catalogueUploadFileDTO1.getVersion()).isEqualTo(catalogueUploadFileDTO2.getVersion());

	}
}
