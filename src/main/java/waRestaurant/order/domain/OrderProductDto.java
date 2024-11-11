package waRestaurant.order.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class OrderProductDto {

  private Long productId;
  private String name;
  private Double price;
  private Integer quantity;
}
