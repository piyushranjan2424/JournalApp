package com.example.demo.service;

import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.entity.UserEntry;
import com.example.demo.repository.UserEntryrepo;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserEntryService {

	@Autowired
	private UserEntryrepo userEntryRepo;
	private static final PasswordEncoder passwordencoder = new BCryptPasswordEncoder();
	private static final Logger logger = LoggerFactory.getLogger(UserEntryService.class);

	public boolean saveNewUserEnrty(UserEntry userEntry) {
		try {
			userEntry.setPassword(passwordencoder.encode(userEntry.getPassword()));
			userEntry.setRoles(Arrays.asList("ADMIN"));
			userEntryRepo.save(userEntry);
			return true;
		} catch (Exception e) {
			log.error("error {}", userEntry.getUserName(), e);
			log.debug("erroertyuiuytrefghjgfdsfghygtrferfghgfghgf");
			return false;
		}
	}

	public void saveUserEnrty(UserEntry userEntry) {
		userEntryRepo.save(userEntry);
	}

	public void saveAdmin(UserEntry userEntry) {
		userEntry.setPassword(passwordencoder.encode(userEntry.getPassword()));
		userEntry.setRoles(Arrays.asList("USER", "ADMIN"));
		userEntryRepo.save(userEntry);
	}

	public List<UserEntry> getAllEntry() {
		return userEntryRepo.findAll();

	}

	public void deleteById(ObjectId id) {
		userEntryRepo.deleteById(id);

	}

	public UserEntry findByUserName(String username) {
		return userEntryRepo.findByUserName(username);

	}

}
