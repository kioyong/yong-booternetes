package com.example;

import java.util.List;
import java.util.function.Consumer;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GreetingConsumer {


    @Bean
    Consumer<Message<GreetingMessage>> singleMessage() {
        return msg -> {
            GreetingMessage payload = msg.getPayload();
            log.info("receive single msg: {}", payload);
        };
    }


    @Bean
    Consumer<Message<List<GreetingMessage>>> batchMessage() {
        return msg -> {
            List<GreetingMessage> payload = msg.getPayload();
            log.info("receive batch msg: {}", payload);
        };
    }
}
