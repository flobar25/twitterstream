package com.newtonx.twitterstream.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.newtonx.twitterstream.dao.TweetRepository;
import com.newtonx.twitterstream.entities.Tweet;

//TODO user JPA and proper ORM if we need more complex entities
@Component
public class TweetsService {

	@Resource
	private TweetRepository tweetRepository;

	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public List<Tweet> findLastTweets() {
		return tweetRepository.findLast100Tweets();
	}

	public void saveTweet(String userName, String text) {
		Tweet tweet = new Tweet();
		tweet.setUsername(userName);
		tweet.setText(text);
		tweetRepository.save(tweet);
	}

}
