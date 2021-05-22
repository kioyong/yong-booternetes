package yong.booternetes.cafe.cart.order;

import java.time.Duration;
import java.util.Map;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.reactor.ratelimiter.operator.RateLimiterOperator;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RestController
public class OrderRestController {

    private final String cartPointsSinkUrl;
    private final OrderRepository orderRepository;
    private final RateLimiter rateLimiter = RateLimiter.of("dataflow-rl", RateLimiterConfig.custom()
            .limitForPeriod(5)
            .limitRefreshPeriod(Duration.ofSeconds(1))
            .timeoutDuration(Duration.ofMillis(25))
            .build());
    private final WebClient http;

    public OrderRestController(@Value("${cart.points-sink-url}") String cartPointsSinkUrl, OrderRepository orderRepository, WebClient http) {
        this.cartPointsSinkUrl = cartPointsSinkUrl;
        this.orderRepository = orderRepository;
        this.http = http;
    }

    @PostMapping("/cart/orders")
    public Mono<Void> placeOrder(@RequestBody Order order) {
        return this.orderRepository
                .saveAll(Flux.range(0, 10).map(l -> order))
                .doOnNext(o -> log.info(o.toString()))
                .flatMap(this::send)
                .onErrorResume(err -> {
                    log.error(err.getLocalizedMessage());
                    return Mono.empty();
                })
                .then();
    }


    private Mono<String> send(Order order) {
        var payload = Map.of(
                "username", order.getUsername(),
                "amount", order.getQuantity());
        return this.http
                .post()
                .uri(cartPointsSinkUrl)
                .body(Mono.just(payload), Map.class)
                .retrieve()
                .bodyToMono(String.class)
                .transformDeferred(RateLimiterOperator.of(rateLimiter));

//                .doFinally(signal -> log.info("Signal: {}", signal.name()))
//                .doOnCancel(() -> log.error("Operation cancelled"))


//                .retryWhen(Retry.backoff(5, Duration.ofSeconds(1)));


        //                .timeout(Duration.ofSeconds(5))
//                .onErrorResume(err -> {
//                    log.error("Error: {}", err.getLocalizedMessage());
//                    return Mono.empty();
//                });

    }

}
