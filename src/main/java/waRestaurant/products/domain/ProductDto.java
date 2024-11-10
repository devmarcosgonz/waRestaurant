package waRestaurant.products.domain;

import lombok.Builder;
import lombok.Data;
import waRestaurant.category.domain.CategoryDto;

@Data
@Builder(toBuilder = true)
public class ProductDto {

    private Long prodcutId;
    
    private String nameProducto;
    
    private CategoryDto category;
    
    private Double price;
}
