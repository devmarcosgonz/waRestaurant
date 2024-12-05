package waRestaurant.report.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class MesaRankingDto {
  private Integer mesaId;
  private Long count;
}
