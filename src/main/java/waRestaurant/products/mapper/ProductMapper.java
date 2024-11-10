package waRestaurant.products.mapper;

import java.util.function.Function;
import waRestaurant.products.domain.ProductDto;
import waRestaurant.products.repository.ProductEntity;

public class ProductMapper {

  public static Function<ProductEntity, ProductDto> mapProductEntityToProductDto() {
    return productEntity ->  ProductDto.builder()
        .prodcutId(productEntity.getId())
        .nameProducto(productEntity.getNameProduct())
        .category(CategoryDto.builder()
            .categoryId(productEntity.getCategory().getId())
            .name(productEntity.getCategory().getName())
            .build())
        .price(productEntity.getPrice())
        .build();
  }
}
