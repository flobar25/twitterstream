package com.newtonx.twitterstream.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.newtonx.twitterstream.entities.Tweet;

public interface TweetRepository extends CrudRepository<Tweet, Long> {

	@Query(value = "SELECT * FROM tweets ORDER BY id DESC LIMIT 100", nativeQuery = true)
	List<Tweet> findLast100Tweets();
}
