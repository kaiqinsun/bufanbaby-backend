package com.bufanbaby.backend.rest.services.auth.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bufanbaby.backend.rest.domain.auth.User;
import com.bufanbaby.backend.rest.repositories.auth.UserRepository;
import com.bufanbaby.backend.rest.resources.auth.SignUpRequest;
import com.bufanbaby.backend.rest.services.auth.UserService;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(final UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		logger.info("password encoder: " + passwordEncoder.getClass().getCanonicalName());
	}

	@Override
	public User create(SignUpRequest signUpRequest) {

		GrantedAuthority authority = new SimpleGrantedAuthority("USER");

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(authority);

		return new User("username", "password", authorities);
	}
}
