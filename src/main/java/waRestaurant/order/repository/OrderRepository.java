package waRestaurant.order.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

  Optional<OrderEntity> findByMesaIdAndCloseAtIsNull(Long number);

  boolean existsByMesaId(Long mesaId);
}
