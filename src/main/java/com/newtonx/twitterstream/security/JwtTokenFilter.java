package com.newtonx.twitterstream.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filter that checks the authorization header from the request and authenticate
 * the user if the token is valid
 *
 * @author florentbariod
 *
 */
public class JwtTokenFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

	public JwtTokenFilter(final JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	protected void doFilterInternal(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse, final FilterChain filterChain)
			throws ServletException, IOException {
		String bearerToken = httpServletRequest.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			bearerToken = bearerToken.substring(7);
		}

		// set sprint security context if token is valid
		final Optional<Authentication> auth = jwtTokenProvider.getAuthentication(bearerToken);
		if (auth.isPresent()) {
			SecurityContextHolder.getContext().setAuthentication(auth.get());
		} else {
			SecurityContextHolder.clearContext();
		}

		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

}