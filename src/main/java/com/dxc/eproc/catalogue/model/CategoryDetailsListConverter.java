package com.dxc.eproc.catalogue.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

// TODO: Auto-generated Javadoc
/**
 * The Class StringListConverter.
 */
@Converter
public class CategoryDetailsListConverter implements AttributeConverter<List<CategoryDetails>, String> {

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
	public String convertToDatabaseColumn(List<CategoryDetails> object) {
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
	 * @return the list
	 */
	@Override
	public List<CategoryDetails> convertToEntityAttribute(String s) {
		if (null == s) {
			return new ArrayList<CategoryDetails>();
		}

		try {
			return MAPPER.readValue(s, new TypeReference<List<CategoryDetails>>() {
			});

		} catch (IOException e) {
			throw new IllegalArgumentException("Error converting JSON to map", e);
		}
	}

}
