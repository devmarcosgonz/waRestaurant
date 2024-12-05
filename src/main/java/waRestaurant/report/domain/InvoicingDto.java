package waRestaurant.report.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class InvoicingDto {
  private LocalDate date;
  private BigDecimal invoiceAmount;
}
