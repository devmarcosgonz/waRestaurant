package waRestaurant.order.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import waRestaurant.products.repository.ProductEntity;

@Entity
@Table(name = "order_details")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class OrderDetailsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_detail")
  private Long id;

  @Column(name = "id_order")
  private Long order;

  @ManyToOne
  @JoinColumn(name = "id_product", nullable = false)
  private ProductEntity product;

  private Integer quantity;

}