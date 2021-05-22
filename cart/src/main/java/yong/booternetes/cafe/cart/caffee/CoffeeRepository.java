package yong.booternetes.cafe.cart.caffee;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CoffeeRepository extends ReactiveMongoRepository<Coffee, Integer> {
}
