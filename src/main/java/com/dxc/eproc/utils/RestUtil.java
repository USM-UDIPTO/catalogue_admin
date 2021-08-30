package com.dxc.eproc.utils;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

// TODO: Auto-generated Javadoc
/**
 * Utility class for testing REST controllers.
 */
public final class RestUtil {

	/** The Constant mapper. */
	private static final ObjectMapper MAPPER = createObjectMapper();

	/**
	 * Instantiates a new test util.
	 */
	private RestUtil() {
	}

	/**
	 * Creates the object mapper.
	 *
	 * @return the object mapper
	 */
	private static ObjectMapper createObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}

	/**
	 * Convert an object to JSON byte array.
	 *
	 * @param object the object to convert.
	 * @return the JSON byte array.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		return MAPPER.writeValueAsBytes(object);
	}
}
