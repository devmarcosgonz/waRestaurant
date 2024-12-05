package waRestaurant.order.repository;

import jakarta.persistence.Tuple;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

  Optional<OrderEntity> findByMesaIdAndCloseAtIsNull(Long number);

  boolean existsByMesaId(Long mesaId);

  @Query(value = """
    SELECT DATE(O.CREATED_AT), SUM(P.PRICE * OD.QUANTITY) 
      FROM ORDERS O
      JOIN ORDER_DETAILS OD ON O.ID_ORDER = OD.ID_ORDER
      JOIN PRODUCTS P ON OD.ID_PRODUCT = P.ID_PRODUCT 
      WHERE O.CREATED_AT BETWEEN :startDate AND :endDate
    GROUP BY DATE(O.CREATED_AT);
      """, nativeQuery = true)
  List<Tuple> findAllByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

  @Query(value = """
    SELECT C.NAME, SUM(P.PRICE * OD.QUANTITY) 
    FROM ORDER_DETAILS OD
    JOIN PRODUCTS P ON OD.ID_PRODUCT = P.ID_PRODUCT
    JOIN CATEGORY C ON P.ID_CATEGORY = C.ID_CATEGORY
    GROUP BY C.NAME;
      """, nativeQuery = true)
  List<Tuple> findAllByCategory();

  @Query(value = """
    SELECT E.NAME, SUM(P.PRICE * OD.QUANTITY) 
    FROM ORDERS O
    JOIN ORDER_DETAILS OD ON O.ID_ORDER = OD.ID_ORDER
    JOIN PRODUCTS P ON OD.ID_PRODUCT = P.ID_PRODUCT
    JOIN EMPLOYEES E ON O.ID_EMPLOYEE = E.ID_EMPLOYEE
    GROUP BY E.NAME
      """, nativeQuery = true)
  List<Tuple>  findAllByEmployee();

  @Query(value = """
    SELECT M.NUMBER_MESA, SUM(P.PRICE * OD.QUANTITY) 
    FROM ORDERS O
    JOIN MESAS M ON O.ID_MESA = M.ID_MESA
    JOIN ORDER_DETAILS OD ON O.ID_ORDER = OD.ID_ORDER
    JOIN PRODUCTS P ON OD.ID_PRODUCT = P.ID_PRODUCT
    GROUP BY M.NUMBER_MESA
      """, nativeQuery = true)
  List<Tuple> findAllByMesa();

  @Query(value = """
    SELECT P.NAME, SUM(OD.QUANTITY), SUM(P.PRICE * OD.QUANTITY)
    FROM ORDERS O
    JOIN ORDER_DETAILS OD ON O.ID_ORDER = OD.ID_ORDER
    JOIN PRODUCTS P ON OD.ID_PRODUCT = P.ID_PRODUCT
    WHERE :filterDate OR O.CREATED_AT BETWEEN :start AND :end 
    GROUP BY P.NAME
      """, nativeQuery = true)
  List<Tuple> findRankingProducts(LocalDateTime start, LocalDateTime end, boolean filterDate);

  @Query(value = """
    SELECT SUM(P.PRICE * OD.QUANTITY) 
    FROM ORDERS O
    JOIN ORDER_DETAILS OD ON O.ID_ORDER = OD.ID_ORDER
    JOIN PRODUCTS P ON OD.ID_PRODUCT = P.ID_PRODUCT
    WHERE O.CREATED_AT BETWEEN :startDate AND :endDate
      """, nativeQuery = true)
  Optional<BigDecimal> findDailyInvoicing(LocalDateTime startDate, LocalDateTime endDate);

  @Query(value = """
    SELECT O.ID_MESA, COUNT(O.ID_MESA)
    FROM ORDERS O
    WHERE O.CREATED_AT BETWEEN :startDate AND :endDate
    GROUP BY O.ID_MESA
      """, nativeQuery = true)
  List<Tuple> findMesaRanking(LocalDateTime startDate, LocalDateTime endDate);
}
