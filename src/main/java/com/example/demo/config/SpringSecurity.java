package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http
				.authorizeHttpRequests(request -> request.requestMatchers("/Public/**").permitAll()
						.requestMatchers("/Journal/**", "/Users/**").authenticated().requestMatchers("/admin/**")
						.hasAnyRole("ADMIN","USER").anyRequest().permitAll())
				.httpBasic(Customizer.withDefaults()).csrf(AbstractHttpConfigurer::disable).build();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
