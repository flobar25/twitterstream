package com.newtonx.twitterstream.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newtonx.twitterstream.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByUsername(String username);

	User findByUsername(String username);
}
