package com.bufanbaby.backend.rest.resources.auth;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import com.bufanbaby.backend.rest.domain.auth.User;
import com.bufanbaby.backend.rest.services.auth.UserService;

/**
 * This class represents User Resources which provides apis for manipulating
 * users.
 * 
 * @author lchi
 *
 */

// TODO: a Resource could be only managed by Jersey (without @Component) but
// still could inject spring bean
// https://github.com/jersey/jersey/blob/2.17/examples/helloworld-spring-webapp/src/main/resources/applicationContext.xml

// To enable JAX-RS resources to work Spring functionality that requires
// proxying, such as Spring transaction management (with @Transactional), Spring
// Security and aspect oriented programming (such as @Aspect), the resources
// must themselves be managed by Spring, by annotating with @Component,
// @Service, @Controller or @Repository

// that is if you don't need spring proxying then you don't need @Component
// annotation

// @Component
@Path("/users")
// @Scope("request")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class UserResource {

	@Context
	private UriInfo uriInfo;

	@Autowired
	private UserService userService;

	@PreAuthorize("isAnonymous()")
	@POST
	public Response signup(@Valid @NotNull SignUpRequest signUpRequest) {

		User user = userService.create(signUpRequest);

		// CreateUserResponse createUserResponse = new CreateUserResponse(user,
		// createTokenForNewUser(
		// user.getId(), request.getPassword().getPassword(),
		// sc.getUserPrincipal().getName()));

		// URI location = uriInfo.getAbsolutePathBuilder()
		// .path(createUserResponse.getApiUser().getId()).build();
		// return Response.created("/user").entity(user).build();
		return null;
	}

	// private OAuth2AccessToken createTokenForNewUser(String userId, String
	// password, String clientId) {
	// String hashedPassword = passwordEncoder.encode(password);
	// UsernamePasswordAuthenticationToken userAuthentication = new
	// UsernamePasswordAuthenticationToken(
	// userId, hashedPassword, Collections.singleton(new SimpleGrantedAuthority(
	// Role.ROLE_USER.toString())));
	// ClientDetails authenticatedClient =
	// clientDetailsService.loadClientByClientId(clientId);
	// OAuth2Request oAuth2Request = createOAuth2Request(null, clientId,
	// Collections.singleton(new
	// SimpleGrantedAuthority(Role.ROLE_USER.toString())), true,
	// authenticatedClient.getScope(), null, null, null, null);
	// OAuth2Authentication oAuth2Authentication = new
	// OAuth2Authentication(oAuth2Request,
	// userAuthentication);
	// return tokenServices.createAccessToken(oAuth2Authentication);
	// }

}
