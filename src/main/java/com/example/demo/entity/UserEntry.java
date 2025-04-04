package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "users_entries")
@Getter
@Setter
@Data
public class UserEntry {
	@Id
	private ObjectId id;
	@Indexed(unique=true)
	@NonNull
	private String userName;
	@NonNull
	private String password;
	@DBRef
	private List<JournalEntry> journalEntries=new ArrayList<>();
	private List<String> roles;
}
