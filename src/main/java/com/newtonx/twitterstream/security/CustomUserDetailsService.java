package com.newtonx.twitterstream.security;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.newtonx.twitterstream.dao.UserRepository;
import com.newtonx.twitterstream.entities.User;

@Component
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
	@Resource
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
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

		return userDetails;
	}

}
