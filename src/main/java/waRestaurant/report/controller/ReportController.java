package waRestaurant.report.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import waRestaurant.report.domain.CategoryReportDto;
import waRestaurant.report.domain.EmployeeReportDto;
import waRestaurant.report.domain.InvoicingDto;
import waRestaurant.report.domain.MesaRankingDto;
import waRestaurant.report.domain.MesaReportDto;
import waRestaurant.report.domain.RankingProductDto;
import waRestaurant.report.domain.SaleReportDto;
import waRestaurant.report.service.ReportService;

@RestController
@RequestMapping("/report")
public class ReportController {

  @Autowired
  private ReportService reportService;

  @GetMapping("/sales")
  public List<SaleReportDto> getSalesReport(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate init,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finish) {
    return reportService.getSalesReport(init, finish);
  }

  @GetMapping("/category")
  public List<CategoryReportDto> getCategoryReport() {
    return reportService.getCategoryReport();
  }

  @GetMapping("/employee")
  public List<EmployeeReportDto> getEmployeeReport() {
    return reportService.getEmployeeReport();
  }

  @GetMapping("/mesa")
  public List<MesaReportDto> getMesaReport() {
    return reportService.getMesaReport();
  }

  @GetMapping("/ranking-products")
  public List<RankingProductDto> getRankingProductsReport(@RequestParam(required = false) LocalDate init,
                                                          @RequestParam(required = false) LocalDate finish) {
    return reportService.getRankingProductsReport(init, finish);
  }

  @GetMapping("/daily-invoicing")
  public InvoicingDto getDailyInvoicingReport() {
    return reportService.getDailyInvoicingReport();
  }

  @GetMapping("/mesa-ranking")
  public List<MesaRankingDto> getMesasOccupation(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate init,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finish) {
    return reportService.getMesasOccupation(init, finish);
  }
}
