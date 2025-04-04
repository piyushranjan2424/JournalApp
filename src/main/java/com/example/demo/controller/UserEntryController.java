package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.UserEntry;
import com.example.demo.repository.UserEntryrepo;
import com.example.demo.service.UserEntryService;

@RestController
@RequestMapping("/Users")
public class UserEntryController {

	@Autowired
	private UserEntryService userEntryService;

	@Autowired
	private UserEntryrepo userEntryrepo;

	@PutMapping
	public ResponseEntity<?> updateUser(@RequestBody UserEntry userEntry) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		UserEntry userInDb = userEntryService.findByUserName(userName);
		userInDb.setUserName(userEntry.getUserName());
		userInDb.setPassword(userEntry.getPassword());
		userEntryService.saveNewUserEnrty(userInDb);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping
	public ResponseEntity<?> DeleteUser(@RequestBody UserEntry userEntry) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		userEntryrepo.deleteByUserName(authentication.getName());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}
}
