package com.newtonx.twitterstream.controllers;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newtonx.twitterstream.entities.Tweet;
import com.newtonx.twitterstream.services.TweetsService;

@RestController
public class TwitterController {

	@Resource
	private TweetsService tweetsDao;

	@RequestMapping("/tweets")
	public List<Tweet> tweets(@RequestParam(required = false) final String username,
			@RequestParam(required = false) final String text) {
		return tweetsDao.findLastTweets(username, text);
	}
}
