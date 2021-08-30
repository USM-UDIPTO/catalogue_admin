package com.dxc.eproc.utils;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.assertj.core.api.Assertions;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

// TODO: Auto-generated Javadoc
/**
 * Utility class for testing REST controllers.
 */
public final class RestUtilTest {

	/** The Constant mapper. */
	private static final ObjectMapper MAPPER = createObjectMapper();

	/**
	 * Instantiates a new test util.
	 */
	private RestUtilTest() {
	}

	/**
	 * Creates a matcher that matches when the examined string represents the same
	 * instant as the reference datetime.
	 *
	 * @param date the reference datetime against which the examined string is
	 *             checked.
	 * @return the zoned date time matcher
	 */
	public static ZonedDateTimeMatcher sameInstant(ZonedDateTime date) {
		return new ZonedDateTimeMatcher(date);
	}

	/**
	 * Verifies the equals/hashcode contract on the domain object.
	 *
	 * @param <T>   the generic type
	 * @param clazz the clazz
	 * @throws Exception the exception
	 */
	public static <T> void equalsVerifier(Class<T> clazz) throws Exception {
		T domainObject1 = clazz.getConstructor().newInstance();
		Assertions.assertThat(domainObject1.toString()).isNotNull();
		Assertions.assertThat(domainObject1).isEqualTo(domainObject1);
		Assertions.assertThat(domainObject1.hashCode()).isEqualTo(domainObject1.hashCode());
		// Test with an instance of another class

		Object testOtherObject = new Object();
		Assertions.assertThat(domainObject1).isNotEqualTo(testOtherObject);
		Assertions.assertThat(domainObject1).isNotEqualTo(null);
		// Test with an instance of the same class
		T domainObject2 = clazz.getConstructor().newInstance();
		Assertions.assertThat(domainObject1).isNotEqualTo(domainObject2);
		// HashCodes are equals because the objects are not persisted yet

	}

	/**
	 * Create a {@link FormattingConversionService} which use ISO date format,
	 * instead of the localized one.
	 *
	 * @return the {@link FormattingConversionService}.
	 */
	public static FormattingConversionService createFormattingConversionService() {
		DefaultFormattingConversionService dfcs = new DefaultFormattingConversionService();
		DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
		registrar.setUseIsoFormat(true);
		registrar.registerFormatters(dfcs);
		return dfcs;
	}

	/**
	 * Makes a an executes a query to the EntityManager finding all stored objects.
	 *
	 * @param <T>  The type of objects to be searched
	 * @param em   The instance of the EntityManager
	 * @param clss The class type to be searched
	 * @return A list of all found objects
	 */
	public static <T> List<T> findAll(EntityManager em, Class<T> clss) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clss);
		Root<T> rootEntry = cq.from(clss);
		CriteriaQuery<T> all = cq.select(rootEntry);
		TypedQuery<T> allQuery = em.createQuery(all);
		return allQuery.getResultList();
	}

	/**
	 * Convert java.util.Date to java.sql.Timestamp
	 *
	 * @param date the date
	 * @return java.sql.Timestamp
	 */
	public static Timestamp convertToSQLDate(Date date) {
		return new Timestamp(date.getTime());
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

	/**
	 * Create a byte array with a specific size filled with specified data.
	 *
	 * @param size the size of the byte array.
	 * @param data the data to put in the byte array.
	 * @return the JSON byte array.
	 */
	public static byte[] createByteArray(int size, String data) {
		byte[] byteArray = new byte[size];
		for (int i = 0; i < size; i++) {
			byteArray[i] = Byte.parseByte(data, 2);
		}
		return byteArray;
	}

	/**
	 * A matcher that tests that the examined string represents the same instant as
	 * the reference datetime.
	 */
	public static class ZonedDateTimeMatcher extends TypeSafeDiagnosingMatcher<String> {

		/** The date. */
		private final ZonedDateTime date;

		/**
		 * Instantiates a new zoned date time matcher.
		 *
		 * @param date the date
		 */
		public ZonedDateTimeMatcher(ZonedDateTime date) {
			this.date = date;
		}

		/**
		 * Matches safely.
		 *
		 * @param item                the item
		 * @param mismatchDescription the mismatch description
		 * @return true, if successful
		 */
		@Override
		protected boolean matchesSafely(String item, Description mismatchDescription) {
			try {
				if (!date.isEqual(ZonedDateTime.parse(item))) {
					mismatchDescription.appendText("was ").appendValue(item);
					return false;
				}
				return true;
			} catch (DateTimeParseException e) {
				mismatchDescription.appendText("was ").appendValue(item)
						.appendText(", which could not be parsed as a ZonedDateTime");
				return false;
			}
		}

		/**
		 * Describe to.
		 *
		 * @param description the description
		 */
		@Override
		public void describeTo(Description description) {
			description.appendText("a String representing the same Instant as ").appendValue(date);
		}
	}

}