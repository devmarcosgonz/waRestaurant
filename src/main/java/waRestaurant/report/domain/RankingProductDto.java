package waRestaurant.report.domain;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class RankingProductDto {
  private String name;
  private BigDecimal quantity;
  private BigDecimal sales;
}
