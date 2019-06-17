package com.newtonx.twitterstream.twitter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.newtonx.twitterstream.services.TweetsService;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

/**
 * Simple consumer of a tweet stream containing the hastags "#photography",
 * "#tech", "#funny" This saves the stream in the DB
 *
 * @author florentbariod
 *
 */
@Component
public class TwitterStreamConsumer {
	private static final Logger LOG = LoggerFactory.getLogger(TwitterStreamConsumer.class);

	@Resource
	private TweetsService tweetsDao;

	@PostConstruct
	public void getStream() {
		final StatusListener listener = new StatusListener() {

			@Override
			public void onException(final Exception ex) {
				LOG.error("Exception happened during stream", ex);
			}

			@Override
			public void onTrackLimitationNotice(final int numberOfLimitedStatuses) {
			}

			@Override
			public void onStatus(final Status status) {
				tweetsDao.saveTweet(status.getUser().getName(), status.getText());
			}

			@Override
			public void onStallWarning(final StallWarning warning) {
				LOG.warn("stalled");
			}

			@Override
			public void onScrubGeo(final long userId, final long upToStatusId) {

			}

			@Override
			public void onDeletionNotice(final StatusDeletionNotice statusDeletionNotice) {
			}
		};

		final TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.addListener(listener);
		twitterStream.filter("#photography", "#tech", "#funny");
	}

}
