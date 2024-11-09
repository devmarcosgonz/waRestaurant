package waRestaurant.products.repository;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import waRestaurant.category.domain.CategoryDto;

@Entity
@Table(name = "category")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Long id;

    @Column(name = "name_product", length = 20)
    private String nameProduct;

    @Column(name = "category", length = 20)
    private CategoryDto category;

    @Column(name = "price", length = 20)
    private Integer price;
}
