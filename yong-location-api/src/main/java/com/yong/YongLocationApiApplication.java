package com.yong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication
public class YongLocationApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(YongLocationApiApplication.class, args);
	}


	@GetMapping("/hello")
	public String hello(){

		return "hello1";
	}
}
