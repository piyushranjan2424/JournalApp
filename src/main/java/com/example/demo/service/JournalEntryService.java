package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.JournalEntry;
import com.example.demo.entity.UserEntry;
import com.example.demo.repository.JournalEntryRepo;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JournalEntryService {
	@Autowired
	private JournalEntryRepo journalEntryRepo;
	@Autowired
	private UserEntryService userEntryService;

	@Transactional
	public void saveJournalEnrty(JournalEntry journalEntry, String userName) {
		try {
			UserEntry userEntry = userEntryService.findByUserName(userName);
			journalEntry.setDate(LocalDateTime.now());
			JournalEntry saved = journalEntryRepo.save(journalEntry);
			userEntry.getJournalEntries().add(saved);
			userEntryService.saveUserEnrty(userEntry);
		} catch (Exception e) {
			throw new RuntimeException("An error occurred while saving the entry.", e);
		}
	}

	public void saveJournalEnrty(JournalEntry journalEntry) {
		journalEntryRepo.save(journalEntry);
	}

	public List<JournalEntry> getAllEntry() {
		return journalEntryRepo.findAll();

	}

	public Optional<JournalEntry> findById(ObjectId id) {
		return journalEntryRepo.findById(id);

	}

	@Transactional
	public boolean deleteById(ObjectId id, String userName) {
		boolean removed=false;
		try {
			UserEntry userEntry = userEntryService.findByUserName(userName);
			 removed = userEntry.getJournalEntries().removeIf(x -> x.getId().equals(id));
			if (removed) {
				userEntryService.saveUserEnrty(userEntry);
				journalEntryRepo.deleteById(id);
			}
		} catch (Exception e) {
			System.out.print(e);
			throw new RuntimeException("An error occured while deleteing the entry.", e);
		}
		return removed;
	}

	public List<JournalEntry> findByUserName(String userName) {
		return null;

	}
}
