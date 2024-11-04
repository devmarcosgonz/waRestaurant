package waRestaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("waRestaurant.domain")
public class WaRestaurantApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaRestaurantApplication.class, args);
	}

}
