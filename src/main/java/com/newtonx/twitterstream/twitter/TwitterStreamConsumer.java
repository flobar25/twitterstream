package com.newtonx.twitterstream.twitter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.newtonx.twitterstream.dao.TweetsDao;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

@Component
public class TwitterStreamConsumer {
	private static final Logger LOG = LoggerFactory.getLogger(TwitterStreamConsumer.class);

	@Resource
	private TweetsDao tweetsDao;

	@PostConstruct
	public void getStream() {
		StatusListener listener = new StatusListener() {

			@Override
			public void onException(Exception ex) {
				LOG.error("Exception happened during stream", ex);
			}

			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
			}

			@Override
			public void onStatus(Status status) {
				tweetsDao.saveTweet(status.getUser().getName(), status.getText());
			}

			@Override
			public void onStallWarning(StallWarning warning) {
				LOG.warn("stalled");
			}

			@Override
			public void onScrubGeo(long userId, long upToStatusId) {

			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
			}
		};

		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.addListener(listener);
		twitterStream.filter("#photography", "#tech", "#funny");
	}

}
