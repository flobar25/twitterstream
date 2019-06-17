package com.newtonx.twitterstream.security;

import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.newtonx.twitterstream.dao.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
	@Value("${security.jwt.token.secret-key:secret-key}")
	private String secretKey;

	@Value("${security.jwt.token.expire-length:3600000}")
	private final long validityInMilliseconds = 3600000; // 1h

	@Resource
	private UserRepository userRepository;

	@Resource
	private UserDetailsService userDetailsService;

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public String createToken(final String username) {
		final Date now = new Date();
		final Date validity = new Date(now.getTime() + validityInMilliseconds);
		final Claims claims = Jwts.claims().setSubject(username);
		claims.put("auth", Arrays.asList(new SimpleGrantedAuthority("USER")));
		return Jwts.builder()//
				.setClaims(claims)//
				.setIssuedAt(now)//
				.setExpiration(validity)//
				.signWith(SignatureAlgorithm.HS256, secretKey)//
				.compact();
	}

	/**
	 * Return a spring authentication model based on the parsing of the JWT token.
	 * If no token is present, if the token is not valid or if the user does not
	 * exist, an empty optional will be returned
	 *
	 * @param token
	 * @return spring Authentication or empty (if invalid token)
	 */
	public Optional<Authentication> getAuthentication(final String token) {
		if (token == null) {
			return Optional.empty();
		}
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
		} catch (JwtException | IllegalArgumentException e) {
			return Optional.empty();
		}

		final String username = getUsername(token);
		final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		return Optional.of(new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()));
	}

	public String getUsername(final String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

}
