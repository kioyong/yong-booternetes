package yong.booternetes.cafe.cart.order;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
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
    private final WebClient http;

    public OrderRestController(@Value("${cart.points-sink-url}") String cartPointsSinkUrl, OrderRepository orderRepository, WebClient http) {
        this.cartPointsSinkUrl = cartPointsSinkUrl;
        this.orderRepository = orderRepository;
        this.http = http;
    }

    @PostMapping("/cart/orders")
    public Mono<Void> placeOrder(@RequestBody Order order) {
        return this.orderRepository
                .save(order)
                .doOnNext(o -> log.info(o.toString()))
                .flatMap(this::send)
                .then();
    }


    private Mono<String> send(Order order) {
        var payload = Map.of(
                "username", order.getUsername(),
                "amount", order.getQuantity());
        return this.http.
                post()
                .uri(cartPointsSinkUrl)
                .body(Mono.just(payload), Map.class)
                .retrieve()
                .bodyToMono(String.class);
    }

}
