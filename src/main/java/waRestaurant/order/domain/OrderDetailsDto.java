package waRestaurant.order.domain;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class OrderDetailsDto {

  private OrderClientDto client;
  private List<OrderProductDto> products;
  private Double totalPrice;
  private LocalDateTime initAt;

  public OrderDetailsDto calculateTotalPrice() {
    this.totalPrice = products.stream()
        .mapToDouble(products -> products.getPrice() * products.getQuantity())
        .sum();
    return this;
  }

}
