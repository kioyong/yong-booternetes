package com.example;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;

@Slf4j
@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class GreetingConsumerTests {

    @Autowired
    InputDestination process;

    @Autowired
    StreamBridge streamBridge;

    @Test
    void sentMessage() {
        log.info("start test123");
        process.send(new GenericMessage<>(List.of(new GreetingMessage("test"))), "greeting");
        log.info("end test123");
//        streamBridge.send("greeting-out-0", List.of(new GreetingMessage("hello")));
    }


}