package com.eurotec.backend.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
	
	@Autowired
	JwtEncoder encoder;

	public String genererToken(Authentication authentication) 
	{
		
		String scope = authentication.getAuthorities().stream()
									 .map(GrantedAuthority::getAuthority)
									 .collect(Collectors.joining(","));
		
		JwtClaimsSet claims = JwtClaimsSet.builder()
								.issuer("self")
								.issuedAt(Instant.now())
								.expiresAt(Instant.now().plus(1, ChronoUnit.DAYS))
								.subject(authentication.getName())
								.claim("scope", scope)
								.build();
		
		return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
		
	}
}
