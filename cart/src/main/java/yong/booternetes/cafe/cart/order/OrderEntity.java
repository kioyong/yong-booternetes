package yong.booternetes.cafe.cart.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "cafe_order")
public class OrderEntity {

    @Id
    private String id;
    private String coffee;
    private String username;
    private int quantity;
}
