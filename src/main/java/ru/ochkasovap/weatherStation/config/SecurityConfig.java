package ru.ochkasovap.weatherStation.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ru.ochkasovap.weatherStation.security.JWTFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final JWTFilter jwtFilter;
	
	@Autowired
	public SecurityConfig(JWTFilter jwtFilter) {
		super();
		this.jwtFilter = jwtFilter;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(c -> c
						.disable())
				.authorizeHttpRequests(r -> r
						.requestMatchers("/measurements", "/measurements/add", "/measurements/rainyDaysCount",
								"/sensors/registration", "/login").permitAll()
						.anyRequest().authenticated())
				.sessionManagement(c -> c
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}


	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

}
