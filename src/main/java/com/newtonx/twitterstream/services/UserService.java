package com.newtonx.twitterstream.services;

import javax.annotation.Resource;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.newtonx.twitterstream.dao.UserRepository;
import com.newtonx.twitterstream.entities.User;
import com.newtonx.twitterstream.security.JwtTokenProvider;

@Component
public class UserService {
	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Resource
	private UserRepository userRepository;

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
		final User user = userRepository.findByUsername(userName);
		if (user == null) {
			throw new IllegalArgumentException("Username does not exist");
		}
		if (passwordEncoder.matches(password, user.getPassword())) {
			return jwtTokenProvider.createToken(userName);
		} else {
			throw new IllegalArgumentException("Wrong username/password");
		}

	}
}
