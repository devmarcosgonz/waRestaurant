package waRestaurant.report.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import waRestaurant.order.repository.OrderRepository;
import waRestaurant.report.domain.CategoryReportDto;
import waRestaurant.report.domain.EmployeeReportDto;
import waRestaurant.report.domain.InvoicingDto;
import waRestaurant.report.domain.MesaRankingDto;
import waRestaurant.report.domain.MesaReportDto;
import waRestaurant.report.domain.RankingProductDto;
import waRestaurant.report.domain.SaleReportDto;

@Service
public class ReportServiceImpl implements ReportService {

  @Autowired
  private OrderRepository orderRepository;

  @Override
  public List<SaleReportDto> getSalesReport(LocalDate init, LocalDate finish) {
    LocalDateTime startDate = LocalDateTime.of(init, LocalTime.MIN);
    LocalDateTime endDate = LocalDateTime.of(finish, LocalTime.MAX);

    return orderRepository.findAllByCreatedAtBetween(startDate, endDate).stream()
        .map(tuple -> SaleReportDto.builder()
            .date(tuple.get(0, Date.class))
            .sales(tuple.get(1, BigDecimal.class))
            .build())
        .toList();
  }

  @Override
  public List<CategoryReportDto> getCategoryReport() {

    return orderRepository.findAllByCategory().stream()
        .map(tuple -> CategoryReportDto.builder()
            .category(tuple.get(0, String.class))
            .sales(tuple.get(1, BigDecimal.class))
            .build())
        .toList();

  }

  @Override
  public List<EmployeeReportDto> getEmployeeReport() {
    return orderRepository.findAllByEmployee().stream()
        .map(tuple -> EmployeeReportDto.builder()
            .name(tuple.get(0, String.class))
            .sales(tuple.get(1, BigDecimal.class))
            .build())
        .toList();
  }

  @Override
  public List<MesaReportDto> getMesaReport() {
    return orderRepository.findAllByMesa().stream()
        .map(tuple -> MesaReportDto.builder()
            .mesaNumber(tuple.get(0, String.class))
            .sales(tuple.get(1, BigDecimal.class))
            .build())
        .toList();
  }

  @Override
  public List<RankingProductDto> getRankingProductsReport(LocalDate init, LocalDate finish) {
    LocalDateTime startDate = null;
    LocalDateTime endDate = null;
    boolean filterDate = true;
    if(init != null && finish != null) {
      startDate = LocalDateTime.of(init, LocalTime.MIN);
      endDate = LocalDateTime.of(finish, LocalTime.MAX);
      filterDate = false;
    }
    return orderRepository.findRankingProducts(startDate, endDate, filterDate).stream()
        .map(tuple -> RankingProductDto.builder()
            .name(tuple.get(0, String.class))
            .quantity(tuple.get(2, BigDecimal.class))
            .sales(tuple.get(2, BigDecimal.class))
            .build())
        .toList();
  }

  @Override
  public InvoicingDto getDailyInvoicingReport() {
    LocalDate now = LocalDate.now();
    LocalDateTime startDate = LocalDateTime.of(now, LocalTime.MIN);
    LocalDateTime endDate = LocalDateTime.of(now, LocalTime.MAX);
    return orderRepository.findDailyInvoicing(startDate, endDate)
        .map(invoicing -> InvoicingDto.builder()
            .invoiceAmount(invoicing)
            .date(now)
            .build())
        .orElse(InvoicingDto.builder()
            .invoiceAmount(BigDecimal.ZERO)
            .date(now)
            .build());
  }

  @Override
  public List<MesaRankingDto> getMesasOccupation(LocalDate init, LocalDate finish) {
    LocalDateTime startDate = LocalDateTime.of(init, LocalTime.MIN);
    LocalDateTime endDate = LocalDateTime.of(finish, LocalTime.MAX);

    return orderRepository.findMesaRanking(startDate, endDate).stream()
        .map(tuple -> MesaRankingDto.builder()
            .mesaId(tuple.get(0, Integer.class))
            .count(tuple.get(1, Long.class))
            .build())
        .toList();
  }

}
