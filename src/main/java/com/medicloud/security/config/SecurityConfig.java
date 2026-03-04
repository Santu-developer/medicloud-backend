package com.medicloud.security.config;

import com.medicloud.security.jwt.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	private final JwtAuthFilter jwtAuthFilter;
	private final CorsConfigurationSource corsConfigurationSource;

	public SecurityConfig(JwtAuthFilter jwtAuthFilter, CorsConfigurationSource corsConfigurationSource) {
		this.jwtAuthFilter = jwtAuthFilter;
		this.corsConfigurationSource = corsConfigurationSource;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
				.cors(cors -> cors.configurationSource(corsConfigurationSource))
				.csrf(csrf -> csrf.disable())
				.headers(headers -> headers
						.frameOptions(frame -> frame.deny())
						.contentTypeOptions(ct -> {})
				)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth

						// ===== PUBLIC ENDPOINTS =====
						.requestMatchers("/auth/**").permitAll()
						.requestMatchers("/public/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/shops").permitAll()
						.requestMatchers("/api/subscriptions/health").permitAll()

						// ===== SWAGGER / OPENAPI =====
						.requestMatchers(
								"/swagger-ui/**",
								"/swagger-ui.html",
								"/v3/api-docs/**",
								"/v3/api-docs.yaml"
						).permitAll()

						// ===== SUPERADMIN — ONLY SUPER_ADMIN ROLE =====
						.requestMatchers("/superadmin/login").permitAll()
						.requestMatchers("/superadmin/**").hasAuthority("SUPER_ADMIN")

						// ===== USER MANAGEMENT — OWNER/ADMIN ONLY =====
						.requestMatchers("/users/**").hasAnyAuthority("OWNER", "ADMIN")

						// ===== DELETE OPERATIONS — OWNER ONLY =====
						.requestMatchers(HttpMethod.DELETE, "/suppliers/**").hasAuthority("OWNER")

						// ===== ALL OTHER ENDPOINTS — AUTHENTICATED =====
						.anyRequest().authenticated()
				)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}