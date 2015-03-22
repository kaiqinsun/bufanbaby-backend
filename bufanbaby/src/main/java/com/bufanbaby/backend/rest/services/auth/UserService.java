package com.bufanbaby.backend.rest.services.auth;

import com.bufanbaby.backend.rest.domain.auth.User;
import com.bufanbaby.backend.rest.resources.auth.SignUpRequest;

public interface UserService {

	User create(SignUpRequest signUpRequest);

}
