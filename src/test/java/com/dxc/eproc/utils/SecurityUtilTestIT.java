package com.dxc.eproc.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.testng.annotations.Test;

// TODO: Auto-generated Javadoc
/**
 * Test class for the {@link SecurityUtils} utility class.
 */
public class SecurityUtilTestIT {

	/** The Constant ANONYMOUS. */
	public static final String ANONYMOUS = "ROLE_ANONYMOUS";

	/**
	 * Test get current user login.
	 */
	@Test
	public void testGetCurrentUserLogin() {
		SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
		securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "admin"));
		SecurityContextHolder.setContext(securityContext);
		Optional<String> login = SecurityUtils.getCurrentUserLogin();
		Assertions.assertThat(login).contains("admin");
	}

	/**
	 * Testget current user JWT.
	 */
	@Test
	public void testgetCurrentUserJWT() {
		SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
		securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "token"));
		SecurityContextHolder.setContext(securityContext);
		Optional<String> jwt = SecurityUtils.getCurrentUserJWT();
		Assertions.assertThat(jwt).contains("token");
	}

	/**
	 * Test is authenticated.
	 */
	@Test
	public void testIsAuthenticated() {
		SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
		securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "admin"));
		SecurityContextHolder.setContext(securityContext);
		boolean isAuthenticated = SecurityUtils.isAuthenticated();
		Assertions.assertThat(isAuthenticated).isTrue();
	}

	/**
	 * Test anonymous is not authenticated.
	 */
	@Test
	public void testAnonymousIsNotAuthenticated() {
		SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(ANONYMOUS));
		securityContext
				.setAuthentication(new UsernamePasswordAuthenticationToken("anonymous", "anonymous", authorities));
		SecurityContextHolder.setContext(securityContext);
		boolean isAuthenticated = SecurityUtils.isAuthenticated();
		Assertions.assertThat(isAuthenticated).isFalse();
	}

	/**
	 * Test is current user in role.
	 */
	@Test
	public void testIsCurrentUserInRole() {
		SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(ANONYMOUS));
		securityContext
				.setAuthentication(new UsernamePasswordAuthenticationToken("anonymous", "anonymous", authorities));
		SecurityContextHolder.setContext(securityContext);

		Assertions.assertThat(SecurityUtils.isCurrentUserInRole(ANONYMOUS)).isTrue();
		List<String> authority = new ArrayList<>();
		authority.add(ANONYMOUS);
		Assertions.assertThat(SecurityUtils.isCurrentUserInRoles(authority)).isTrue();
	}

	/**
	 * Test extract authority from claims.
	 */
	@Test
	public void testExtractAuthorityFromClaims() {
		SecurityUtils.extractAuthorityFromClaims(new HashMap<>());
	}

	/**
	 * Test map custom roles to granted authorities.
	 */
	@Test
	public void testMapCustomRolesToGrantedAuthorities() {
		SecurityUtils.mapCustomRolesToGrantedAuthorities(new ArrayList<String>());
	}
}