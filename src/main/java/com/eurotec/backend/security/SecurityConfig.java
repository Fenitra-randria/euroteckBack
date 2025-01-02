package com.eurotec.backend.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	RsaKeys rsaKeys;
	
	@Bean
	PasswordEncoder generatePasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	     return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Autowired
	UtilisateurDetailService utilisateurDetailService;
	
	@Bean
	@Order(1)                                                        
	public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception 
	{
		http
			.cors(corsCustomizer())
			.securityMatcher("/api/**")
			.authorizeHttpRequests(authorize -> {
					authorize.requestMatchers ( "/api/login" ).permitAll();
					authorize.requestMatchers ( HttpMethod.POST , "/api/utilisateurs" ).permitAll();
					
					authorize.anyRequest().authenticated();
				}
			)
			.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.csrf((csrf) -> csrf.disable());
		return http.build();
	}

	@Bean   
	@Order(2)
	public SecurityFilterChain formLoginFilterChain(HttpSecurity http) throws Exception 
	{

		http.authorizeHttpRequests(authorize -> {
					authorize.requestMatchers("/swagger-ui/**").permitAll();
					authorize.requestMatchers("/v3/api-docs/**").permitAll();
					authorize.requestMatchers("/images/**").permitAll();
					authorize.requestMatchers("/pdf/**").permitAll();
					authorize.requestMatchers("/css/**").permitAll();
					authorize.requestMatchers("/js/**").permitAll();
					authorize.anyRequest().authenticated();
				})
			.formLogin(Customizer.withDefaults())
			.userDetailsService(utilisateurDetailService)
			.csrf((csrf) -> csrf.disable());
		return http.build();
	}
	
	@Bean
	JwtDecoder jwtDecoder()
	{
		return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
	}
	
	@Bean
	JwtEncoder jwtEncoder()
	{
		JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
		JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
		return new NimbusJwtEncoder(jwks);
	}

	@Bean
    public Customizer<CorsConfigurer<HttpSecurity>> corsCustomizer() {
        return cors -> cors
            .configurationSource(request -> {
                var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                corsConfig.addAllowedOrigin("*"); // Allow specific origin
                corsConfig.addAllowedMethod("*"); // Allow all HTTP methods (GET, POST, etc.)
                corsConfig.addAllowedHeader("*"); // Allow all headers
                return corsConfig;
            });
    }
	
}
