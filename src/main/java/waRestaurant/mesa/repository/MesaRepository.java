package waRestaurant.mesa.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MesaRepository extends JpaRepository<MesaEntity, Long> {

  Optional<MesaEntity> findByNumber(String number);

  boolean existsByNumber(String mesaNumber);

  Optional<MesaEntity> findByNumberAndState(String number, String name);
}
