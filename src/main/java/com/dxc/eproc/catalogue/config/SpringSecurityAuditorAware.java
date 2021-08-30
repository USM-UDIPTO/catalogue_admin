package com.dxc.eproc.catalogue.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.dxc.eproc.utils.SecurityUtils;

// TODO: Auto-generated Javadoc
/**
 * Implementation of AuditorAware based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {
	/** The Constant SYSTEM_ACCOUNT. */
	public static final String SYSTEM_ACCOUNT = "system";

	/**
	 * Gets the current auditor.
	 *
	 * @return the current auditor
	 */
	@Override
	public Optional<String> getCurrentAuditor() {
		// TODO -
		return Optional.of(SecurityUtils.getCurrentUserLogin().orElse(SYSTEM_ACCOUNT));

	}
}
