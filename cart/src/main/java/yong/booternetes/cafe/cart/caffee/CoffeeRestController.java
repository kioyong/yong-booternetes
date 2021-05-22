package yong.booternetes.cafe.cart.caffee;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CoffeeRestController {

    private final CoffeeRepository cafe;

    @GetMapping("/cart/coffees")
    public Flux<Coffee> get() {
        return cafe.findAll();
    }
}
