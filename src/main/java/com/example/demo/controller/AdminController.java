package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.UserEntry;
import com.example.demo.service.UserEntryService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/admin")

public class AdminController {
	@Autowired
	private UserEntryService userEntryService;

	@GetMapping("/all-users")
	public ResponseEntity<?> getAllUsers() {
		List<UserEntry> all = userEntryService.getAllEntry();
		if (all != null && !all.isEmpty()) {
			return new ResponseEntity<>(all, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}
	@PostMapping("/create-admin-user")
	public void AddNewAdmin(@RequestBody UserEntry userEntry) {
		userEntryService.saveAdmin(userEntry);
		
		
	}

}
