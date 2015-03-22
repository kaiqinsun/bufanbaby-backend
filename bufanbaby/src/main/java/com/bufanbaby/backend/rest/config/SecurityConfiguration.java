package com.bufanbaby.backend.rest.config;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
// @EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	// @Override
	// protected void configure(HttpSecurity http) throws Exception {
	// http.authorizeRequests().anyRequest().permitAll().and().sessionManagement()
	// .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable();
	// }
	//
	// @Bean
	// public PasswordEncoder passwordEncoder() {
	// return new BCryptPasswordEncoder();
	// }
}
