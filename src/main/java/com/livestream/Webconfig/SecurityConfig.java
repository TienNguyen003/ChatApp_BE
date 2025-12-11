package com.livestream.Webconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	@Value("${api.prefix}")
	private String api;

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

	private final String[] PUBLIC_ENDPOINTS = {
			"api/users", "api/auth/token",
			"api/auth/introspect", "api/auth/logout",
			"api/auth/refresh",
			"api/menu", "api/submenu", "api/chat-message", "api/chat-meta"
	};

	@Autowired
	private CustomJwtDecoder jwtDecoder;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.authorizeHttpRequests(request -> request
				.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()
				.requestMatchers(HttpMethod.GET, PUBLIC_ENDPOINTS).permitAll()
				.requestMatchers("/ws/**").permitAll()
				.anyRequest().permitAll()
				);

		httpSecurity.oauth2ResourceServer(oauth2 -> oauth2
				.jwt(jwtConfigurer -> jwtConfigurer
						.decoder(jwtDecoder)
						.jwtAuthenticationConverter(jwtAuthenticationConverter())
				)
				.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
		);
		httpSecurity.csrf(AbstractHttpConfigurer::disable);

		return httpSecurity.build();
	}

	@Bean
	JwtAuthenticationConverter jwtAuthenticationConverter(){
		JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

		return jwtAuthenticationConverter;
	}
}
