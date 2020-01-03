package com.example.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@EnableAuthorizationServer
@SpringBootApplication
public class SpringOauth2ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringOauth2ServerApplication.class, args);
	}

}
