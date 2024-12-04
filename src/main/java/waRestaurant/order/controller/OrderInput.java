package waRestaurant.order.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderInput {

  private Long mesaId;
  private Long productId;
  private Integer quantity;
  private String observation;
}
