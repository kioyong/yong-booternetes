package yong.booternetes.cafe.cart.caffee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "coffee")
@AllArgsConstructor
@NoArgsConstructor
public class Coffee {

    @Id
    private Integer id;
    private String name;
}
