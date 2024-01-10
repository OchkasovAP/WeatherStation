package ru.ochkasovap.weatherStation.security;

import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Component
public class JWTUtil {
	@Value("${jwt_secret}")
	private String secret;
	@Value("${admin.login}")
	private String username;
	
	public String generateToken() {
		Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());
		return JWT.create()
				.withClaim("username", username)
				.withIssuedAt(new Date())
				.withExpiresAt(expirationDate)
				.sign(Algorithm.HMAC256(secret));
	}
	public String validateTokenAndRetrieveClaim(String token) {
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
				.build();
		DecodedJWT jwt = verifier.verify(token);
		return jwt.getClaim("username").asString();
	}
	
}
