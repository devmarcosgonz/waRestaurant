package waRestaurant.order.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class OrderClientDto {
  private Long clientId;
  private String name;
}
