package waRestaurant.mesa.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class MesasDto {

  private Long id;
  private String number;
  private String state;
}
