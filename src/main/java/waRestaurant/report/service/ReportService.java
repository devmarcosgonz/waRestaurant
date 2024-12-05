package waRestaurant.report.service;

import java.time.LocalDate;
import java.util.List;
import waRestaurant.report.domain.CategoryReportDto;
import waRestaurant.report.domain.EmployeeReportDto;
import waRestaurant.report.domain.InvoicingDto;
import waRestaurant.report.domain.MesaRankingDto;
import waRestaurant.report.domain.MesaReportDto;
import waRestaurant.report.domain.RankingProductDto;
import waRestaurant.report.domain.SaleReportDto;

public interface ReportService {

  List<SaleReportDto> getSalesReport(LocalDate init, LocalDate finish);

  List<CategoryReportDto> getCategoryReport();

  List<EmployeeReportDto> getEmployeeReport();

  List<MesaReportDto> getMesaReport();

  List<RankingProductDto> getRankingProductsReport(LocalDate init, LocalDate finish);

  InvoicingDto getDailyInvoicingReport();

  List<MesaRankingDto> getMesasOccupation(LocalDate init, LocalDate finish);
}
