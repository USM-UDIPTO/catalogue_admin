package com.dxc.eproc.catalogue.dto.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import com.dxc.eproc.utils.RestUtilTest;

// TODO: Auto-generated Javadoc
//TODO: Auto-generated Javadoc
/**
 * The Class CatalogueItemDTOTest.
 */
public class CatalogueItemDTOTest {

	/**
	 * Dto equals verifier.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void dtoEqualsVerifier() throws Exception {

		RestUtilTest.equalsVerifier(CatalogueItemDTO.class);

		CatalogueItemDTO catalogueItemDTO1 = new CatalogueItemDTO();
		catalogueItemDTO1.setId(1L);
		CatalogueItemDTO catalogueItemDTO2 = new CatalogueItemDTO();
		Assertions.assertThat(catalogueItemDTO1).isNotEqualTo(catalogueItemDTO2);
		catalogueItemDTO2.setId(catalogueItemDTO1.getId());
		Assertions.assertThat(catalogueItemDTO1.getId()).isEqualTo(catalogueItemDTO2.getId());
		catalogueItemDTO2.setId(2L);
		Assertions.assertThat(catalogueItemDTO1).isNotEqualTo(catalogueItemDTO2);
		catalogueItemDTO2.setId(null);
		Assertions.assertThat(catalogueItemDTO1).isNotEqualTo(catalogueItemDTO2);

		catalogueItemDTO1.setActiveYn(true);
		catalogueItemDTO2.setActiveYn(false);
		Assertions.assertThat(catalogueItemDTO1.getActiveYn()).isNotEqualTo(catalogueItemDTO2.getActiveYn());

		catalogueItemDTO1.setCatalogueCode("CAT01");
		catalogueItemDTO2.setCatalogueCode("CAT01");
		Assertions.assertThat(catalogueItemDTO1.getCatalogueCode()).isEqualTo(catalogueItemDTO2.getCatalogueCode());

		catalogueItemDTO1.setCatalogueName("CATNAME");
		catalogueItemDTO2.setCatalogueName("CATNAME");
		Assertions.assertThat(catalogueItemDTO1.getCatalogueName()).isEqualTo(catalogueItemDTO2.getCatalogueName());

		catalogueItemDTO1.setDeptId(1);
		catalogueItemDTO2.setDeptId(2);
		Assertions.assertThat(catalogueItemDTO1.getDeptId()).isNotEqualTo(catalogueItemDTO2.getDeptId());

		catalogueItemDTO1.setDeptName("DDA");
		catalogueItemDTO2.setDeptName("SSA");
		Assertions.assertThat(catalogueItemDTO1.getDeptName()).isNotEqualTo(catalogueItemDTO2.getDeptName());

		catalogueItemDTO1.setPartNumber("1");
		catalogueItemDTO2.setPartNumber("SSA");
		Assertions.assertThat(catalogueItemDTO1.getPartNumber()).isNotEqualTo(catalogueItemDTO2.getPartNumber());

		catalogueItemDTO1.setUomName("1");
		catalogueItemDTO2.setUomName("2");
		Assertions.assertThat(catalogueItemDTO1.getUomName()).isNotEqualTo(catalogueItemDTO2.getUomName());

		List<CategoryDetailsDTO> categoryDetailsList1 = new ArrayList<>();
		List<CategoryDetailsDTO> categoryDetailsList2 = new ArrayList<>();
		CategoryDetailsDTO categoryDetailsDTO1 = new CategoryDetailsDTO();
		categoryDetailsDTO1.setCategoryCode("AA");
		categoryDetailsList1.add(categoryDetailsDTO1);
		CategoryDetailsDTO categoryDetailsDTO2 = new CategoryDetailsDTO();
		categoryDetailsDTO2.setCategoryCode("AA");
		categoryDetailsList2.add(categoryDetailsDTO2);
		catalogueItemDTO1.setCategories(categoryDetailsList1);
		catalogueItemDTO2.setCategories(categoryDetailsList2);
		Assertions.assertThat(catalogueItemDTO1.getCategories()).isNotEqualTo(catalogueItemDTO2.getCategories());

		Map<String, String> map1 = new HashMap<>();
		map1.put("SPEC", "SPECNAM");
		map1.put("SPEC1", "SPECNAM1");
		catalogueItemDTO1.setSpecifications(map1);

		Map<String, String> map2 = new HashMap<>();
		map2.put("SPEC", "SPECNAM");
		map2.put("SPEC1", "SPECNAM1");
		catalogueItemDTO2.setSpecifications(map2);
		Assertions.assertThat(catalogueItemDTO1.getSpecifications()).isEqualTo(catalogueItemDTO2.getSpecifications());

		catalogueItemDTO1.setCreatedBy("user");
		catalogueItemDTO2.setCreatedBy("user");
		Assertions.assertThat(catalogueItemDTO1.getCreatedBy()).isEqualTo(catalogueItemDTO2.getCreatedBy());

		catalogueItemDTO1.setLastModifiedBy("user");
		catalogueItemDTO2.setLastModifiedBy("user");
		Assertions.assertThat(catalogueItemDTO1.getLastModifiedBy()).isEqualTo(catalogueItemDTO2.getLastModifiedBy());

		catalogueItemDTO1.getCreatedTs();
		catalogueItemDTO2.getLastModifiedTs();
	}
}
