package com.eurotec.backend.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPublicKey;
import java.security.interfaces.RSAPrivateKey;


@ConfigurationProperties(prefix = "rsa")
public record RsaKeys(RSAPublicKey publicKey,RSAPrivateKey privateKey) 
{
}
