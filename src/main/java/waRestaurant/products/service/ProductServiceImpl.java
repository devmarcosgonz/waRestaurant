package waRestaurant.products.service;

import static waRestaurant.commons.CustomException.errorSupplier;
import static waRestaurant.commons.CustomException.throwError;
import static waRestaurant.commons.ErrorsEnum.CATEGORY_NOT_FOUND;
import static waRestaurant.commons.ErrorsEnum.CATEGORY_SAVE_ERROR;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import waRestaurant.commons.CustomException;
import waRestaurant.commons.ErrorsEnum;
import waRestaurant.products.controller.ProductInput;
import waRestaurant.products.domain.ProductDto;
import static waRestaurant.products.mapper.ProductMapper.mapProductEntityToProductDto;
import waRestaurant.products.repository.ProductEntity;
import waRestaurant.products.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Override
  public List<ProductDto> getAllProducts() {
    return productRepository.findAll().stream()
        .map(mapProductEntityToProductDto())
        .toList();
  }

  @Override
  public ProductDto getProductById(Long id) {
    return productRepository.findById(id)
        .map(mapProductEntityToProductDto())
        .orElseThrow(errorSupplier(CATEGORY_NOT_FOUND));
  }

  @Override
  @Transactional
  public ProductDto createProduct(ProductInput product) {
    return buildProductEntity(product)
        .map(productRepository::save)
        .map(mapProductEntityToProductDto())
        .orElseThrow(errorSupplier(CATEGORY_SAVE_ERROR));
  }

  @Override
  @Transactional
  public ProductDto updateProduct(Long id, ProductInput product) {
    return productRepository.findById(id)
        .map(updateProductValues(product))
        .map(productRepository::save)
        .map(mapProductEntityToProductDto())
        .orElseThrow(errorSupplier(ErrorsEnum.CATEGORY_UPDATE_ERROR));
  }

  @Override
  @Transactional
  public void deleteProduct(Long id) {
    try {
      productRepository.findById(id)
          .ifPresentOrElse(
              productRepository::delete,
              () -> { throw new CustomException(CATEGORY_NOT_FOUND); });
    } catch (Exception e) {
      throw throwError(ErrorsEnum.CATEGORY_DELETE_ERROR, "Error while deleting product", e);
    }
  }

  private Optional<ProductEntity> buildProductEntity(ProductInput product) {
    return Optional.of(ProductEntity.builder()
         .nameProduct(product.getName())
        .build());
  }

  private Function<ProductEntity, ProductEntity> updateProductValues(ProductInput product) {
    return updateProduct -> updateProduct.toBuilder()
        .nameProduct(product.getName())
        .build();
  }
}
