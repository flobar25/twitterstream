package com.newtonx.twitterstream.services;

import javax.annotation.Resource;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.newtonx.twitterstream.dao.UserRepository;
import com.newtonx.twitterstream.entities.User;
import com.newtonx.twitterstream.security.JwtTokenProvider;

@Component
public class UserService {

	@Resource
	private PasswordEncoder passwordEncoder;

	@Resource
	private UserRepository userRepository;

	@Resource
	private AuthenticationManager authenticationManager;

	@Resource
	private JwtTokenProvider jwtTokenProvider;

	public String signup(final String userName, final String password) {
		if (!userRepository.existsByUsername(userName)) {
			final User user = new User();
			user.setUsername(userName);
			user.setPassword(passwordEncoder.encode(password));
			userRepository.save(user);
			return jwtTokenProvider.createToken(user.getUsername());
		} else {
			throw new IllegalArgumentException("Username is already in use");
		}
	}

	public String signin(final String userName, final String password) {

		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(userName, password));

		if (authentication.isAuthenticated()) {
			return jwtTokenProvider.createToken(userName);
		} else {
			throw new IllegalArgumentException("Wrong username/password");
		}

	}
}
