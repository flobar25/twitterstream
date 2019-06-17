package com.newtonx.twitterstream.security;

import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.newtonx.twitterstream.dao.UserRepository;
import com.newtonx.twitterstream.entities.User;

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

	public Authentication getAuthentication(final String token) {
		if (token == null) {
			return null;
		}
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
		} catch (JwtException | IllegalArgumentException e) {
			return null;
		}

		final String username = getUsername(token);
		final User user = userRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		final UserDetails userDetails = org.springframework.security.core.userdetails.User//
				.withUsername(username)//
				.authorities(Arrays.asList(new SimpleGrantedAuthority("USER")))//
				.password(user.getPassword())//
				.accountExpired(false)//
				.accountLocked(false)//
				.credentialsExpired(false)//
				.disabled(false)//
				.build();

		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String getUsername(final String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

}
