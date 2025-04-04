package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.UserEntry;
import com.example.demo.service.UserEntryService;

@RestController
@RequestMapping("/Public")

public class PublicController {
	@Autowired
	private UserEntryService userEntryService;

	@GetMapping("/health-check")
	public String healthCheck() {
		return "OK";

	}

	@PostMapping("/Create-User")
	public void createUsers(@RequestBody UserEntry userEntry) {
		userEntryService.saveNewUserEnrty(userEntry);
	}
}
