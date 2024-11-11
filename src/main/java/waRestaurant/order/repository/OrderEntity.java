package waRestaurant.order.repository;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import waRestaurant.client.repository.ClientEntity;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class OrderEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_order")
  private Long id;

  @Column(name = "id_mesa")
  private Long mesaId;

  @ManyToOne
  @JoinColumn(name = "id_client", nullable = false)
  private ClientEntity client;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "close_at")
  private LocalDateTime closeAt;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<OrderDetailsEntity> orderDetails;
}