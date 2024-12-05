package waRestaurant.products.service;

import java.util.List;
import waRestaurant.products.controller.ProductInput;
import waRestaurant.products.domain.ProductDto;

public interface ProductService {
  List<ProductDto> getAllProducts();

  ProductDto getProductById(Long id);

  ProductDto createProduct(ProductInput category);

  ProductDto updateProduct(Long id, ProductInput category);
  
  void deleteProduct(Long id);
  
  void updateStock(Long productId, Integer quantity);
  
  List<ProductDto> getAllProductsWithStockAlert();
}
