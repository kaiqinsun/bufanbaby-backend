package com.bufanbaby.backend.rest.repositories.auth;

import com.bufanbaby.backend.rest.domain.auth.User;

public interface UserRepository {
	User findByEmail(String email);

}
