package com.dxc.eproc.catalogue.dto.request;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import com.dxc.eproc.utils.RestUtilTest;

// TODO: Auto-generated Javadoc
/**
 * The Class SpecificationSearchDTOTest.
 */
public class SpecificationSearchDTOTest {

	/**
	 * Dto equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void dtoEqualsVerifier() throws Exception {

		RestUtilTest.equalsVerifier(SpecificationSearchDTO.class);

		SpecificationSearchDTO specificationSearchDTO1 = new SpecificationSearchDTO();
		specificationSearchDTO1.setDeptId(1);
		SpecificationSearchDTO specificationSearchDTO2 = new SpecificationSearchDTO();
		Assertions.assertThat(specificationSearchDTO1).isNotEqualTo(specificationSearchDTO2);
		specificationSearchDTO2.setDeptId(specificationSearchDTO1.getDeptId());
		Assertions.assertThat(specificationSearchDTO1.getDeptId()).isEqualTo(specificationSearchDTO2.getDeptId());
		specificationSearchDTO1.setDeptId(2);
		Assertions.assertThat(specificationSearchDTO1).isNotEqualTo(specificationSearchDTO2);
		specificationSearchDTO1.setDeptId(null);
		Assertions.assertThat(specificationSearchDTO1).isNotEqualTo(specificationSearchDTO2);

		specificationSearchDTO1.setSpecName("SPEC");
		specificationSearchDTO2.setSpecName("SPEC");
		Assertions.assertThat(specificationSearchDTO1.getSpecName()).isEqualTo(specificationSearchDTO2.getSpecName());
	}
}
