package com.newtonx.twitterstream.services;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.newtonx.twitterstream.dao.TweetRepository;
import com.newtonx.twitterstream.entities.Tweet;

/**
 * SImple class to load some tweets from the configured DB.
 *
 * @author florentbariod
 *
 */
@Component
public class TweetsService {

	@Resource
	private TweetRepository tweetRepository;

	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	/**
	 * @return the last 100 tweets
	 */
	public List<Tweet> findLastTweets() {
		return tweetRepository.findLast100Tweets();
	}

	/**
	 * Save a tweet in DB
	 * 
	 * @param userName
	 * @param text
	 */
	public void saveTweet(final String userName, final String text) {
		final Tweet tweet = new Tweet();
		tweet.setUsername(userName);
		tweet.setText(text);
		tweetRepository.save(tweet);
	}

}
