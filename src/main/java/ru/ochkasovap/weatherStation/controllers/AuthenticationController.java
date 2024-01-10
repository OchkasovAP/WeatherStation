package ru.ochkasovap.weatherStation.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import ru.ochkasovap.weatherStation.security.JWTUtil;

@RestController
public class AuthenticationController {
	private final JWTUtil jwtUtil;
	private final AuthenticationManager authenticationManager;
	
	@Autowired
	public AuthenticationController(JWTUtil jwtUtil, AuthenticationManager authenticationManager) {
		super();
		this.jwtUtil = jwtUtil;
		this.authenticationManager = authenticationManager;
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> performLogin(@RequestBody JsonNode jsonNode) {
		String username = jsonNode.get("username").asText();
		String password = jsonNode.get("password").asText();
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		try {
			authenticationManager.authenticate(authenticationToken);
		} catch (AuthenticationException e) {
			return new ResponseEntity<Map<String,String>>(Map.of("error", e.toString()), HttpStatus.BAD_REQUEST);
		}
		String jwtToken = jwtUtil.generateToken();
		return new ResponseEntity<Map<String,String>>(Map.of("jwt_token",jwtToken), HttpStatus.OK);
	}
}
