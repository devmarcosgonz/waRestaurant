package waRestaurant.report.domain;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class EmployeeReportDto {
  private String name;
  private BigDecimal sales;
}
