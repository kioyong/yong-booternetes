package yong.booternetes.cafe.cart.coffee;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CoffeeRepository extends ReactiveMongoRepository<CoffeeEntity, String> {
}
