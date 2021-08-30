package com.dxc.eproc.catalogue.documentstore;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.dxc.eproc.document.space.AbstractDocumentStore;
import com.dxc.eproc.document.space.DocumentMetaData;
import com.dxc.eproc.document.space.DocumentStoreException;
import com.dxc.eproc.utils.DateUtil;
import com.dxc.eproc.utils.Utility;

// TODO: Auto-generated Javadoc
/**
 * The Class CatalogueDocumentStore.
 */
@Component
public class CatalogueDocumentStore extends AbstractDocumentStore {

	/** The Constant SPACE. */
	private static final String SPACE = "catalogue-space";

	/** The Constant BUCKET_NAME. */
	private static final String BUCKET_NAME = "goods-catalogue-document-store";

	/** The Constant SPACE_PREFIX. */
	private static final String SPACE_PREFIX = "catalogue-admin-service";

	/** The Constant ENTITY_NAME. */
	private static final String ENTITY_NAME = "catalogue-upload";

	/**
	 * Instantiates a new catalogue document store.
	 */
	public CatalogueDocumentStore() {
		setBucketName(BUCKET_NAME);
	}

	/**
	 * Generate key.
	 *
	 * @param referenceType the reference type
	 * @param referenceId   the reference id
	 * @return the string
	 */
	private String generateKey(ReferenceTypes referenceType, Long referenceId) {
		String key = null;
		key = SPACE_PREFIX + "/" + referenceType + "/" + ENTITY_NAME + "/" + referenceId;
		return key;
	}

	/**
	 * Save catalogue document.
	 *
	 * @param documentMetaData the document meta data
	 * @throws DocumentStoreException the document store exception
	 */
	public void saveCatalogueDocument(DocumentMetaData documentMetaData) throws DocumentStoreException {

		Map<String, String> fileMetadata = new HashMap<>();
		DocumentMetaData objectMetaData = new DocumentMetaData();
		fileMetadata.put(SPACE, SPACE);
		fileMetadata.put(FILE_NAME, documentMetaData.getFileName());
		fileMetadata.put(CONTENT_TYPE, documentMetaData.getMimeType());
		fileMetadata.put(CREATED_DATE, DateUtil.getCurrentDateTime());
		fileMetadata.put(LAST_MODIFIED_DATE, DateUtil.getCurrentDateTime());
		fileMetadata.put(REFERENCE_ID, String.valueOf(documentMetaData.getReferenceId()));
		fileMetadata.put(REFERENCE_TYPE, documentMetaData.getReferenceType().toString());
		if (Utility.isValidString(documentMetaData.getDocumentType())) {
			fileMetadata.put(DOCUMENT_TYPE, documentMetaData.getDocumentType());
		}

		if (Utility.isValidString(documentMetaData.getDocumentName())) {
			fileMetadata.put(DOCUMENT_NAME, documentMetaData.getDocumentName());
		}

		if (Utility.isValidString(documentMetaData.getFileDesc())) {
			fileMetadata.put(DOCUMENT_NAME, documentMetaData.getFileDesc());
		}

		String key = generateKey(ReferenceTypes.valueOf(documentMetaData.getReferenceType()),
				documentMetaData.getReferenceId());
		fileMetadata.put(OBJECT_KEY, key);

		objectMetaData.setFileMetadata(fileMetadata);
		objectMetaData.setFileData(documentMetaData.getFileData());
		objectMetaData.setBucketName(BUCKET_NAME);
		objectMetaData.setObjectKey(key);
		try {
			saveDocument(objectMetaData);
		} catch (DocumentStoreException e) {
			throw new DocumentStoreException(e.getMessage());
		}

	}

	/**
	 * Download object.
	 *
	 * @param referenceType the reference type
	 * @param referenceId   the reference id
	 * @return the response entity
	 * @throws DocumentStoreException the document store exception
	 */
	public ResponseEntity<ByteArrayResource> downloadObject(ReferenceTypes referenceType, Long referenceId)
			throws DocumentStoreException {
		String objectKey = generateKey(referenceType, referenceId);
		Map<String, String> objectMetaData = getDocumentMetaData(objectKey);
		byte[] object = getDocument(objectKey);
		String fileName = objectMetaData.get(FILE_NAME);
		if (!Utility.isValidString(fileName)) {
			fileName = objectMetaData.get("x-amz-meta-filename");
		}

		ByteArrayResource resource = new ByteArrayResource(object);
		ResponseEntity<ByteArrayResource> downloadFile = ResponseEntity.ok().contentLength(object.length)
				.header("Content-type", "application/octet-stream")
				.header("Content-disposition", "attachment; filename=\"" + fileName + "\"").body(resource);

		return downloadFile;
	}

	/**
	 * Gets the object.
	 *
	 * @param referenceType the reference type
	 * @param referenceId   the reference id
	 * @return the object
	 * @throws DocumentStoreException the document store exception
	 */
	public byte[] getObject(ReferenceTypes referenceType, Long referenceId) throws DocumentStoreException {
		String objectKey = generateKey(referenceType, referenceId);
		return getDocument(objectKey);
	}
//
//	/**
//	 * Removes the document.
//	 *
//	 * @param objectMetaData the object meta data
//	 * @throws DocumentStoreException the document store exception
//	 */
//	@Override
//	public void removeDocument(DocumentMetaData objectMetaData) throws DocumentStoreException {
//
//	}

	/**
	 * Gets the document.
	 *
	 * @param bucketName the bucket name
	 * @param objectKey  the object key
	 * @return the document
	 * @throws DocumentStoreException the document store exception
	 */
	@Override
	public byte[] getDocument(String bucketName, String objectKey) throws DocumentStoreException {
		return null;
	}
}
