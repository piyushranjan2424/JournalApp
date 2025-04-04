package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.entity.UserEntry;
import com.example.demo.repository.UserEntryrepo;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserEntryrepo userEntryrepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntry userentry = userEntryrepo.findByUserName(username);
		if (userentry != null) {
			return org.springframework.security.core.userdetails.User.builder().username(userentry.getUserName())
					.password(userentry.getPassword()).roles(userentry.getRoles().toArray(new String[0])).build();

		}

		throw new UsernameNotFoundException("User not found with username: " + username);
	}

}
