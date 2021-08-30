package com.dxc.eproc.catalogue.mocks;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.dxc.eproc.client.master.dto.UomDTO;
import com.dxc.eproc.utils.RestUtil;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

public class MasterServiceMock {

	public static void setUpMockGetAllActiveUomsByNameUsingGET(WireMockServer mockService, String name, List<UomDTO> uomDTOList)
			throws IOException {

		mockService.stubFor(WireMock.get(WireMock.urlEqualTo("/v1/api/master/active/uoms/" + name))
				.willReturn(WireMock.aResponse().withStatus(HttpStatus.OK.value()).withHeader("Content-Type",
						MediaType.APPLICATION_JSON_VALUE)
						.withBody(RestUtil.convertObjectToJsonBytes(uomDTOList))));
	}
}
