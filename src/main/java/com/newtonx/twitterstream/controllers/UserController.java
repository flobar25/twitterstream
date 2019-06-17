package com.newtonx.twitterstream.controllers;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newtonx.twitterstream.services.UserService;

@RestController
public class UserController {
	@Resource
	private UserService userService;

	@RequestMapping("/signup")
	public String signup(@RequestParam final String username, @RequestParam final String password) {
		// TODO error handling with http codes
		return userService.signup(username, password);
	}

	@RequestMapping("/signin")
	public String signin(@RequestParam final String username, @RequestParam final String password) {
		// TODO error handling with http codes
		return userService.signin(username, password);
	}
}
