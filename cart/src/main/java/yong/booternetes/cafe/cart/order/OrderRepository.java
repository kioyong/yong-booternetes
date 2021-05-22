package yong.booternetes.cafe.cart.order;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface OrderRepository extends ReactiveMongoRepository<Order, Integer> {
}
