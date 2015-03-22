package com.bufanbaby.backend.rest.config;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.validation.ParameterNameProvider;
import javax.validation.Validation;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.glassfish.jersey.server.validation.ValidationConfig;
import org.glassfish.jersey.server.validation.ValidationFeature;
import org.glassfish.jersey.server.validation.internal.InjectingConstraintValidatorFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * This is the bootstrap class for Jersey application. Spring component
 * annotation is used to hook into Spring Boot auto-configuration feature.
 * 
 * See: spring-boot-starter-jersey
 * http://docs.spring.io/spring-boot/docs/1.2.2.RELEASE/reference/htmlsingle
 * /#boot-features-jersey
 * 
 * By default, Jersey servlet will be registered and mapped to /* by default.
 * You can change the mapping by adding <code>ApplicationPath</code> annotation
 * to your <code>ResourceConfig/code>.
 * 
 * @author lchi
 *
 */
@Component
@ApplicationPath("/v1.0")
// Accept/Content-type header: application/json; version=1
public class ResourcesConfiguration extends ResourceConfig {
	private final static Logger logger = Logger.getLogger(ResourcesConfiguration.class.getName());

	public ResourcesConfiguration() {

		packages("com.bufanbaby.backend.rest.resources");

		// This filter is used to invoked before the resource method call and
		// Spring will pass the request onto this fliter
		register(RequestContextFilter.class);

		// Log request and entity info in the server side log
		register(new LoggingFilter(logger, true));

		// register(RolesAllowedDynamicFeature.class);

		// See Jersey Bean Validation example:
		// https://github.com/jersey/jersey/tree/2.17/examples/bean-validation-webapp
		register(ValidationFeature.class);

		register(JacksonFeature.class);

		register(JacksonObjectMapperProvider.class);

		register(ValidationConfigurationContextResolver.class);

		// Put Jersey processing info into HTTP response header which could be
		// inspected in Developer Tool
		// TODO: should be disabled in production environment
		// property(ServerProperties.TRACING, "ALL");

		// Jersey Bean Validation Feature is Auto Discovered once
		// jersey-bean-validation on classpath

		// Now you can expect validation errors to be sent to the client.
		property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

		// property(CommonProperties.FEATURE_AUTO_DISCOVERY_DISABLE, true);

		// @ValidateOnExecution annotations on subclasses won't cause errors.
		// property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK,
		// true);

		// register filters
		// register(RequestContextFilter.class);

		// NOTE: for details:
		// https://github.com/Codingpedia/demo-rest-jersey-spring

		// register(LoggingResponseFilter.class);
		// register(CORSResponseFilter.class);
		//
		// // register exception mappers
		// register(GenericExceptionMapper.class);
		// register(AppExceptionMapper.class);
		// register(NotFoundExceptionMapper.class);
		//
		// // register features
		// register(JacksonFeature.class);
		// register(MultiPartFeature.class);

		// NOTE:
		// http://www.codingpedia.org/ama/tutorial-rest-api-design-and-implementation-in-java-with-jersey-and-spring/
		// There may be cases when your REST api provides responses that are
		// very long, and we all know how important transfer speed and bandwidth
		// still are on mobile devices/networks. I think this is the first
		// performance optimization point one needs to address, when developing
		// REST apis that support mobile apps. Guess what? Because responses are
		// text, we can compress them. And with today’s power of smartphones and
		// tablets uncompressing them on the client side should not be a big
		// deal…
		//
		// On the server side, you can easily compress every response with
		// Jersey’s EncodingFilter, which is a container filter that supports
		// enconding-based content configuration. The filter examines what
		// content encodings are supported by the container and decides what
		// encoding should be chosen based on the encodings listed in the
		// Accept-Encoding request header and their associated quality values.
		// To use particular content en/decoding, you need to register one or
		// more content encoding providers that provide specific encoding
		// support; currently there are providers for GZIP and DEFLATE coding.

		// register(EntityFilteringFeature.class);
		// EncodingFilter.enableFor(this, GZipEncoder.class);

	}

	/**
	 * Custom configuration of validation. This configuration defines custom:
	 * <ul>
	 * <li>ConstraintValidationFactory - so that validators are able to inject
	 * Jersey providers/resources.</li>
	 * <li>ParameterNameProvider - if method input parameters are invalid, this
	 * class returns actual parameter names instead of the default ones (
	 * {@code arg0, arg1, ..})</li>
	 * </ul>
	 */
	public static class ValidationConfigurationContextResolver implements
			ContextResolver<ValidationConfig> {

		@Context
		private ResourceContext resourceContext;

		@Override
		public ValidationConfig getContext(final Class<?> type) {
			return new ValidationConfig().constraintValidatorFactory(
					resourceContext.getResource(InjectingConstraintValidatorFactory.class))
					.parameterNameProvider(new CustomParameterNameProvider());
		}

		/**
		 * See ContactCardTest#testAddInvalidContact.
		 */
		private class CustomParameterNameProvider implements ParameterNameProvider {

			private final ParameterNameProvider nameProvider;

			public CustomParameterNameProvider() {
				nameProvider = Validation.byDefaultProvider().configure()
						.getDefaultParameterNameProvider();
			}

			@Override
			public List<String> getParameterNames(final Constructor<?> constructor) {
				return nameProvider.getParameterNames(constructor);
			}

			@Override
			public List<String> getParameterNames(final Method method) {
				if ("signup".equals(method.getName())) {
					return Arrays.asList("user");
				}
				return nameProvider.getParameterNames(method);
			}
		}
	}

	// see Jackson 2 example:
	// https://github.com/jersey/jersey/tree/2.17/examples/json-jackson
	@Provider
	public static class JacksonObjectMapperProvider implements ContextResolver<ObjectMapper> {

		final ObjectMapper defaultObjectMapper;

		public JacksonObjectMapperProvider() {
			defaultObjectMapper = createDefaultMapper();
		}

		@Override
		public ObjectMapper getContext(Class<?> type) {
			return defaultObjectMapper;
		}

		private static ObjectMapper createDefaultMapper() {
			final ObjectMapper result = new ObjectMapper();
			result.configure(SerializationFeature.INDENT_OUTPUT, true);

			return result;
		}
	}
}
