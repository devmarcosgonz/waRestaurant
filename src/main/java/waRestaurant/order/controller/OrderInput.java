package waRestaurant.order.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderInput {

  private Long mesaId;
  private Long productId;
  private Integer quantity;
  private String observation;
}
