package com.dxc.eproc.catalogue.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

// TODO: Auto-generated Javadoc
/**
 * The Class EprocMessageSourceComponent.
 */
@Component
public class EprocMessageSourceComponent {

	/** The message source. */
	@Autowired
	MessageSource messageSource;

	/** The request. */
	@Autowired
	HttpServletRequest request;

	/** The env. */
	@Autowired
	private Environment env;

	/**
	 * returns header for Accept-Language.
	 *
	 * @return request
	 */
	public String getAcceptLanguage() {
		return request.getHeader("Accept-Language");
	}

	/**
	 * gets Active Profiles.
	 *
	 * @return arrayList
	 */
	public Collection<String> getActiveProfiles() {
		return Arrays.asList(env.getActiveProfiles());
	}

	/**
	 * gets messageSource.
	 *
	 * @param code   the code
	 * @param args   the args
	 * @param locale the locale
	 * @return messageSource the messageSource
	 */
	private String getMessage(String code, Object[] args, Locale locale) {
		return messageSource.getMessage(code, args, locale);
	}

	/**
	 * gets message.
	 *
	 * @param code the code
	 * @return message the message
	 */
	public String getMessage(String code) {
		String acceptLanguage = getAcceptLanguage();
		if (acceptLanguage != null) {
			Locale locale = new Locale(acceptLanguage);
			return getMessage(code, null, locale);
		} else {
			return getMessage(code, null, Locale.ENGLISH);
		}
	}

	/**
	 * gets message.
	 *
	 * @param code the code
	 * @param args the args
	 * @return message the message
	 */
	public String getMessage(String code, Object[] args) {
		String acceptLanguage = getAcceptLanguage();
		if (acceptLanguage != null) {
			Locale locale = new Locale(acceptLanguage);
			return getMessage(code, args, locale);
		} else {
			return getMessage(code, args, Locale.ENGLISH);
		}
	}

}
