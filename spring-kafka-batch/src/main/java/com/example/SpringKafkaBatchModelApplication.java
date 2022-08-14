package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;

@SpringBootApplication
public class SpringKafkaBatchModelApplication {

    @Autowired
    StreamBridge streamBridge;

    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaBatchModelApplication.class, args);
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void run() {
//        for (int i = 0; i < 2; i++) {
//            streamBridge.send("greeting-out-0", new GreetingMessage("hello"));
//        }
//    }
}
