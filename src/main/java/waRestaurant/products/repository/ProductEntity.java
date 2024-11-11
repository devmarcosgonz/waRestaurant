package waRestaurant.products.repository;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import waRestaurant.category.repository.CategoryEntity;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Long id;
    
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false)
    private CategoryEntity category;

    @Column(name = "price", length = 20)
    private Double price;
}
