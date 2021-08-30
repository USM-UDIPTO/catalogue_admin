package com.dxc.eproc.catalogue.model;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import com.dxc.eproc.utils.RestUtilTest;

// TODO: Auto-generated Javadoc
//TODO: Auto-generated Javadoc
/**
 * The Class SpecificationTest.
 */
public class SpecificationTest {

	/**
	 * Entity equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void dtoEqualsVerifier() throws Exception {

		RestUtilTest.equalsVerifier(Specification.class);

		Specification specification1 = new Specification();
		specification1.setId(1L);
		Specification specification2 = new Specification();
		Assertions.assertThat(specification1).isNotEqualTo(specification2);
		specification2.setId(specification1.getId());
		Assertions.assertThat(specification1.getId()).isEqualTo(specification2.getId());
		specification2.setId(2L);
		Assertions.assertThat(specification1).isNotEqualTo(specification2);
		specification2.setId(null);
		Assertions.assertThat(specification1).isNotEqualTo(specification2);

		specification1.setDeptId(1);
		specification2.setDeptId(1);
		Assertions.assertThat(specification1.getDeptId()).isEqualTo(specification2.getDeptId());

		specification1.setDeptName("SSA");
		specification2.setDeptName("SSA");
		Assertions.assertThat(specification1.getDeptName()).isEqualTo(specification2.getDeptName());

		specification1.setActiveYn(true);
		specification2.setActiveYn(false);
		Assertions.assertThat(specification1.getActiveYn()).isNotEqualTo(specification2.getActiveYn());

		specification1.setSpecName("AL");
		specification2.setSpecName("LA");
		Assertions.assertThat(specification1.getSpecName()).isNotEqualTo(specification2.getSpecName());
	}
}
