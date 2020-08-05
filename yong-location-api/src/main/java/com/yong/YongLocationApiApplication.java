package com.yong;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication
@Slf4j
public class YongLocationApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(YongLocationApiApplication.class, args);
	}


	@GetMapping("/hello")
	public String hello(){
		log.info("calling hello");
		return "hello1";
	}
}
