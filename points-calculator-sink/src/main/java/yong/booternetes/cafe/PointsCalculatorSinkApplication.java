package yong.booternetes.cafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j2
@SpringBootApplication
@Import(HttpRequestFunctionConfiguration.class)
public class PointsCalculatorSinkApplication {

	private static final int POINTS_MULTIPLIER = 100;

	public static void main(String[] args) {
		SpringApplication.run(PointsCalculatorSinkApplication.class, args);
	}

	@Bean
	Function<Purchase, Points> calculatePoints(){
		return purchase -> new Points(purchase.getUsername(), purchase.getAmount() * POINTS_MULTIPLIER);
	}

	@Bean
	Consumer<Points> postPoints(HttpRequestFunction httpRequestFunction){
		return points -> httpRequestFunction
				.apply(Flux.just(new GenericMessage<>(points)))
				.doOnError(throwable -> log.error(throwable.getMessage(), throwable))
				.blockFirst();
	}

}
