package waRestaurant.report.domain;

import java.math.BigDecimal;
import java.sql.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class SaleReportDto {
  private Date date;
  private BigDecimal sales;
}
