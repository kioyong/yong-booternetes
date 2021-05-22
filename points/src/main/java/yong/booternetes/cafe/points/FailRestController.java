package yong.booternetes.cafe.points;

import java.time.Instant;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class FailRestController {

    private final Object monitor = new Object();
    private int counter = 0;

    @PostMapping("/ok")
    ResponseEntity<?> ok(@RequestBody Map<String, String> payload) {
        log.info("> Payload: {}, time: {}", payload, Instant.now());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/timeout")
    ResponseEntity<?> timeout(@RequestBody Map<String, String> payload) throws Exception {
        log.info("> Timeout Payload: {}, time: {}", payload, Instant.now());
        Thread.sleep(8_000);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/retry")
    ResponseEntity<?> retry(@RequestBody Map<String, String> payload) {
        log.info("> Retry Payload: {}, time: {}", payload, Instant.now());

        synchronized (monitor) {
            counter++;
            if (counter < 5) {
                log.info("<< Failure {} >>", counter);
                return ResponseEntity.badRequest().build();
            }
        }
        log.info("<< Retry SuccessFul! {} >>", counter);
        counter = 0;
        return ResponseEntity.ok().build();
    }

}
