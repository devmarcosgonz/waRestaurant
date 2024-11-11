package waRestaurant.mesa.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mesas")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class MesaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_mesa")
  private Long id;
  @Column(name = "number_mesa")
  private String number;
  @Column(name = "state")
  private String state;
}
