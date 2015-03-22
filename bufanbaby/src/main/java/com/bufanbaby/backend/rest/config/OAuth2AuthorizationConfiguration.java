package com.bufanbaby.backend.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationConfiguration extends AuthorizationServerConfigurerAdapter {

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient("my-trusted-client")
				.authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
				.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT").scopes("read", "write", "trust")
				.resourceIds("sparklr").accessTokenValiditySeconds(60).and()
				.withClient("my-client-with-registered-redirect")
				.authorizedGrantTypes("authorization_code").authorities("ROLE_CLIENT")
				.scopes("read", "trust").resourceIds("sparklr")
				.redirectUris("http://anywhere?key=value").and()
				.withClient("my-client-with-secret")
				.authorizedGrantTypes("client_credentials", "password").authorities("ROLE_CLIENT")
				.scopes("read").resourceIds("sparklr").secret("secret");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setSupportRefreshToken(true);
		tokenServices.setTokenStore(new InMemoryTokenStore());
		return tokenServices;
	}
}
