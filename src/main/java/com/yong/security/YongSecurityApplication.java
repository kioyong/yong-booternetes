package com.yong.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.reactive.config.EnableWebFlux;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableWebFlux
@EnableAutoConfiguration
public class YongSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(YongSecurityApplication.class, args);
	}
}
