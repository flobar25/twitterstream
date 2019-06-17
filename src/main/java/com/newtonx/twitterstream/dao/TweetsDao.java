package com.newtonx.twitterstream.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.newtonx.twitterstream.models.Tweet;

//TODO user JPA and proper ORM if we need more complex entities
@Component
public class TweetsDao {
	private static final String FIND_TWEETS_QUERY = "SELECT * FROM tweets ORDER BY id DESC LIMIT 100";
	private static final String SAVE_TWEET_QUERY = "INSERT INTO tweets (username, text) VALUES (:username, :text)";

	@Resource
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public List<Tweet> findLastTweets() {
		Map<String, Object> params = new HashMap<>();
		var result = namedParameterJdbcTemplate.query(FIND_TWEETS_QUERY, params, new TweetMapper());
		return result;
	}

	public void saveTweet(String userName, String text) {
		Map<String, Object> params = new HashMap<>();
		params.put("username", userName);
		params.put("text", text);
		namedParameterJdbcTemplate.update(SAVE_TWEET_QUERY, params);
	}

	private static final class TweetMapper implements RowMapper<Tweet> {

		@Override
		public Tweet mapRow(ResultSet rs, int rowNum) throws SQLException {
			Tweet tweet = new Tweet();
			tweet.setId(rs.getInt("id"));
			tweet.setUserName(rs.getString("username"));
			tweet.setText(rs.getString("text"));
			return tweet;
		}
	}
}
