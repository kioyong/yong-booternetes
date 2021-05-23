package yong.booternetes.cafe.cart.coffee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "coffee")
@AllArgsConstructor
@NoArgsConstructor
public class CoffeeEntity {

    @Id
    private String id;
    private String name;
}
