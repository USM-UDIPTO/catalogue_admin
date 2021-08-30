package com.dxc.eproc.catalogue.service;

import org.mockito.InjectMocks;
import org.powermock.modules.testng.PowerMockTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import com.dxc.eproc.catalogue.documentstore.CatalogueDocumentStore;
import com.dxc.eproc.catalogue.documentstore.ReferenceTypes;
import com.dxc.eproc.document.space.DocumentMetaData;

// TODO: Auto-generated Javadoc
/**
 * The Class CatalogueDocumentStoreTest.
 */
@Ignore
public class CatalogueDocumentStoreTest extends PowerMockTestCase {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(CatalogueDocumentStoreTest.class);

	/** The catalogue document store. */
	@InjectMocks
	private CatalogueDocumentStore catalogueDocumentStore;

	/**
	 * Save catalogue document test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void saveCatalogueDocumentTest() throws Exception {
		DocumentMetaData documentMetaData = new DocumentMetaData();
		documentMetaData.setReferenceType(ReferenceTypes.CATALOGUE_UPLOAD_FILE.toString());
		catalogueDocumentStore.saveCatalogueDocument(documentMetaData);
		log.info("saveCatalogueDocumentTest successful!!");
	}

	/**
	 * Gets the object test.
	 *
	 * @return the object test
	 * @throws Exception the exception
	 */
	@Test
	public void getObjectTest() throws Exception {
		catalogueDocumentStore.getObject(null, null);
		log.info("getObjectTest successful!!");
	}

	/**
	 * Gets the document test.
	 *
	 * @return the document test
	 * @throws Exception the exception
	 */
	@Test
	public void getDocumentTest() throws Exception {
		catalogueDocumentStore.getDocument(null, null);
		log.info("getDocumentTest successful!!");
	}

	/**
	 * Removes the document test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void removeDocumentTest() throws Exception {
		DocumentMetaData documentMetaData = new DocumentMetaData();
		catalogueDocumentStore.removeDocument(documentMetaData.getObjectKey());
		log.info("removeDocumentTest successful!!");
	}
}
