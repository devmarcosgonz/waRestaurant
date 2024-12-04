package waRestaurant.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    @Query(value = """
      SELECT r.* 
      FROM
        reservations r 
		JOIN mesas m ON m.id_mesa = r.id_mesa 
		WHERE m.number_mesa = :mesaNumber AND r.reservation_date BETWEEN :start AND :end;
      """, nativeQuery = true)
    List<ReservationEntity> findAllByMesaAndReservationDateBetween( @Param("mesaNumber") String mesaNumber,
                                                                    @Param("start") LocalDateTime start,
                                                                    @Param("end") LocalDateTime end
    );
}
