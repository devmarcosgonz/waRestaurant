package waRestaurant.mesa.domain;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class MesaDto {

  private HeaderDto header;
  private List<DetailsDto> details;
}
