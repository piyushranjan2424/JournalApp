package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.JournalEntry;
import com.example.demo.entity.UserEntry;
import com.example.demo.service.JournalEntryService;
import com.example.demo.service.UserEntryService;

@RestController
@RequestMapping("/Journal")
public class JournalEntryController {

	@Autowired
	private JournalEntryService journalEntryService;
	@Autowired
	private UserEntryService userEntryService;

	@GetMapping
	public ResponseEntity<?> getAllJournalEntryOfUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		UserEntry userEntry = userEntryService.findByUserName(userName);
		List<JournalEntry> all = userEntry.getJournalEntries();
		if (all != null && !all.isEmpty()) {
			return new ResponseEntity<>(all, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@PostMapping
	public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myentry) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userName = authentication.getName();
			journalEntryService.saveJournalEnrty(myentry, userName);
			return new ResponseEntity<>(myentry, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("id/{myId}")
	public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId) {
		// ObjectId objectId = new ObjectId(myId);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		UserEntry userEntry = userEntryService.findByUserName(userName);
		List<JournalEntry> collect = userEntry.getJournalEntries().stream().filter(x -> x.getId().equals(myId))
				.collect(Collectors.toList());
		if (!collect.isEmpty()) {
			Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
			if (journalEntry.isPresent()) {
				return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("id/{myId}")
	public ResponseEntity<JournalEntry> getDeleteId(@PathVariable ObjectId myId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		boolean removed = journalEntryService.deleteById(myId, userName);
		if (removed) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	 @PutMapping("id/{myId}")
	    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String userName = authentication.getName();
	        UserEntry userEntry = userEntryService.findByUserName(userName);
	        List<JournalEntry> collect = userEntry.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
	        if (!collect.isEmpty()) {
	            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
	            if (journalEntry.isPresent()) {
	                JournalEntry old = journalEntry.get();
	                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
	                old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
	                journalEntryService.saveJournalEnrty(old);
	                return new ResponseEntity<>(old, HttpStatus.OK);
	            }
	        }
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }

}