package waRestaurant.mesa.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class HeaderDto {
  private Long mesaId;
  private Long clientId;
  private String state;
  private Double total;
  private LocalDateTime initAt;
  private LocalDateTime closeAt;
}
