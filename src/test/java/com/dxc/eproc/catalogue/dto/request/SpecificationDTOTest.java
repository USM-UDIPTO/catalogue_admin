package com.dxc.eproc.catalogue.dto.request;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import com.dxc.eproc.utils.RestUtilTest;

// TODO: Auto-generated Javadoc
/**
 * The Class SpecificationDTOTest.
 */
public class SpecificationDTOTest {

	/**
	 * Dto equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void dtoEqualsVerifier() throws Exception {

		RestUtilTest.equalsVerifier(SpecificationDTO.class);

		SpecificationDTO specificationDTO1 = new SpecificationDTO();
		specificationDTO1.setId(1L);
		SpecificationDTO specificationDTO2 = new SpecificationDTO();
		Assertions.assertThat(specificationDTO1).isNotEqualTo(specificationDTO2);
		specificationDTO2.setId(specificationDTO1.getId());
		Assertions.assertThat(specificationDTO1.getId()).isEqualTo(specificationDTO2.getId());
		specificationDTO1.setId(2L);
		Assertions.assertThat(specificationDTO1).isNotEqualTo(specificationDTO2);
		specificationDTO1.setId(null);
		Assertions.assertThat(specificationDTO1).isNotEqualTo(specificationDTO2);

		specificationDTO1.setActiveYn(true);
		specificationDTO2.setActiveYn(true);
		Assertions.assertThat(specificationDTO1.getActiveYn()).isEqualTo(specificationDTO2.getActiveYn());

		specificationDTO1.setDeptId(1);
		specificationDTO2.setDeptId(2);
		Assertions.assertThat(specificationDTO1.getDeptId()).isNotEqualTo(specificationDTO2.getDeptId());

		specificationDTO1.setDeptName("A");
		specificationDTO2.setDeptName("A");
		Assertions.assertThat(specificationDTO1.getDeptName()).isEqualTo(specificationDTO2.getDeptName());

		specificationDTO1.setSpecName("SPEC");
		specificationDTO2.setDeptName("SPEC1");
		Assertions.assertThat(specificationDTO1.getSpecName()).isNotEqualTo(specificationDTO2.getSpecName());

		specificationDTO1.setVersion(0);
		specificationDTO2.setVersion(0);
		Assertions.assertThat(specificationDTO1.getVersion()).isEqualTo(specificationDTO2.getVersion());
	}
}
