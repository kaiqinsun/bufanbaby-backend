package com.bufanbaby.backend.rest.resources.auth;

import javax.validation.constraints.NotNull;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

public class SignUpResponse {

	@NotNull
	private String username;

	@NotNull
	private String id;

	private OAuth2AccessToken oAuth2AccessToken;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public OAuth2AccessToken getoAuth2AccessToken() {
		return oAuth2AccessToken;
	}

	public void setoAuth2AccessToken(OAuth2AccessToken oAuth2AccessToken) {
		this.oAuth2AccessToken = oAuth2AccessToken;
	}

}
