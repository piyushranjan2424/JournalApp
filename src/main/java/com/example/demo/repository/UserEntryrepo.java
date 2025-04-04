package com.example.demo.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entity.UserEntry;

public interface UserEntryrepo extends MongoRepository<UserEntry, ObjectId> {
	UserEntry findByUserName(String username);

	void deleteByUserName(String userName);
}
