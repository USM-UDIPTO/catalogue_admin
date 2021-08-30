package com.dxc.eproc.catalogue.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

// TODO: Auto-generated Javadoc
/**
 * The Class StringMapConverter.
 */
@Converter
public class StringMapConverter implements AttributeConverter<Map<String, String>, String> {

	/** The Constant mapper. */
	private static final ObjectMapper MAPPER;

	/** The Constant writer. */
	private static final ObjectWriter WRITER;

	static {
		MAPPER = new ObjectMapper();
		WRITER = MAPPER.writer();
	}

	/**
	 * Convert to database column.
	 *
	 * @param object the object
	 * @return the string
	 */
	@Override
	public String convertToDatabaseColumn(Map<String, String> object) {
		try {
			return object == null ? "" : WRITER.writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Convert to entity attribute.
	 *
	 * @param s the s
	 * @return the map
	 */
	@Override
	public Map<String, String> convertToEntityAttribute(String s) {
		if (null == s) {
			// You may return null if you prefer that style
			return new HashMap<>();
		}

		try {
			return MAPPER.readValue(s, new TypeReference<Map<String, String>>() {
			});

		} catch (IOException e) {
			throw new IllegalArgumentException("Error converting JSON to map", e);
		}
	}

}
