package waRestaurant.products.domain;

import lombok.Builder;
import lombok.Data;
import waRestaurant.category.domain.CategoryDto;

@Data
@Builder(toBuilder = true)
public class ProductDto {

    private Long productId;
    
    private String productName;

    private CategoryDto category;
    
    private Double price;
}
