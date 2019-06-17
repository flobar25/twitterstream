package com.newtonx.twitterstream.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

// We should use OncePerRequestFilter since we are doing a database call, there is no point in doing this more than once
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

		final Authentication auth = jwtTokenProvider.getAuthentication(bearerToken);
		if (auth != null) {
			SecurityContextHolder.getContext().setAuthentication(auth);
		} else {
			SecurityContextHolder.clearContext();
		}

		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

}