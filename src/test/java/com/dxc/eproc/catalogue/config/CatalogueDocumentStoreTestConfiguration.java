package com.dxc.eproc.catalogue.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.dxc.eproc.catalogue.documentstore.CatalogueDocumentStore;

//TODO: Auto-generated Javadoc
/**
* The Class CatalogueDocumentStoreTestConfiguration.
*/
@Profile("test")
@Configuration
public class CatalogueDocumentStoreTestConfiguration {

	/**
	 * Save supplier document.
	 *
	 * @return the SupplierSpace
	 */
	@Bean
	@Primary
	public CatalogueDocumentStore saveCatalogueDocument() {
		return Mockito.mock(CatalogueDocumentStore.class);
	}
}
