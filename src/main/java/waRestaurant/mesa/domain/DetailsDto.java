package waRestaurant.mesa.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class DetailsDto {
  private Long productId;
  private Integer quantity;
}
