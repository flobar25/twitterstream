package com.newtonx.twitterstream.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.newtonx.twitterstream.entities.Tweet;

public interface TweetRepository extends CrudRepository<Tweet, Long> {

	@Query(value = "SELECT * FROM tweets ORDER BY id DESC LIMIT 100", nativeQuery = true)
	List<Tweet> findLast100Tweets();

	@Query(value = "SELECT * FROM tweets WHERE username=:username ORDER BY id DESC LIMIT 100", nativeQuery = true)
	List<Tweet> findLast100TweetsByUser(@Param("username") String username);

	@Query(value = "SELECT * FROM tweets WHERE text LIKE %:text% ORDER BY id DESC LIMIT 100", nativeQuery = true)
	List<Tweet> findLast100TweetsByText(@Param("text") String text);

	@Query(value = "SELECT * FROM tweets WHERE text LIKE %:text% AND  username=:username ORDER BY id DESC LIMIT 100", nativeQuery = true)
	List<Tweet> findLast100TweetsByTextAndUser(@Param("text") String text, @Param("username") String username);
}
