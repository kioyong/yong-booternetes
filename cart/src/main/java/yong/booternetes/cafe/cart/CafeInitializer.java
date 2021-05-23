package yong.booternetes.cafe.cart;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import yong.booternetes.cafe.cart.coffee.CoffeeEntity;
import yong.booternetes.cafe.cart.coffee.CoffeeRepository;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
@RequiredArgsConstructor
@Log4j2
public class CafeInitializer {

    private final Environment env;
    private final CoffeeRepository repo;

    @EventListener({ApplicationReadyEvent.class})
    public void run() {
        var coffees = env.getProperty("cart.coffees", "");

        var coffeeStream = Flux.fromArray(coffees.split(";"))
                .filter(str -> !ObjectUtils.isEmpty(str))
                .map(coffee -> new CoffeeEntity(null, coffee.trim()));

        repo.deleteAll()
                .thenMany(repo.saveAll(coffeeStream))
                .subscribe(coffeeEntity -> log.info(" ...adding "));
    }
}
