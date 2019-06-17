package com.newtonx.twitterstream.controllers;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newtonx.twitterstream.dao.TweetsDao;
import com.newtonx.twitterstream.models.Tweet;

@RestController
public class TwitterController {

	@Resource
	private TweetsDao tweetsDao;

	@RequestMapping("/tweets")
	public List<Tweet> index() {
		// TODO error handling with http codes
		return tweetsDao.findLastTweets();
	}
}
