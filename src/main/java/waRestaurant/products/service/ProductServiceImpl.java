package waRestaurant.products.service;

import static waRestaurant.commons.CustomException.errorSupplier;
import static waRestaurant.commons.CustomException.throwError;
import static waRestaurant.commons.ErrorsEnum.CATEGORY_NOT_FOUND;
import static waRestaurant.commons.ErrorsEnum.PRODUCT_NOT_FOUND;
import static waRestaurant.commons.ErrorsEnum.PRODUCT_SAVE_ERROR;
import static waRestaurant.products.mapper.ProductMapper.mapProductEntityToProductDto;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import waRestaurant.category.repository.CategoryEntity;
import waRestaurant.category.repository.CategoryRepository;
import waRestaurant.commons.CustomException;
import waRestaurant.commons.ErrorsEnum;
import waRestaurant.products.controller.ProductInput;
import waRestaurant.products.domain.ProductDto;
import waRestaurant.products.repository.ProductEntity;
import waRestaurant.products.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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
                .orElseThrow(errorSupplier(PRODUCT_NOT_FOUND));
    }

    @Override
    @Transactional
    public ProductDto createProduct(ProductInput product) {
        CategoryEntity category = categoryRepository.findById(product.getCategoryId())
                .orElseThrow(errorSupplier(CATEGORY_NOT_FOUND));
        return buildProductEntity(product, category)
                .map(productRepository::save)
                .map(mapProductEntityToProductDto())
                .orElseThrow(errorSupplier(PRODUCT_SAVE_ERROR));
    }

    @Override
    @Transactional
    public ProductDto updateProduct(Long id, ProductInput product) {
        CategoryEntity category = null;
        if (product.getCategoryId() != null) {
            category = categoryRepository.findById(product.getCategoryId())
                    .orElseThrow(errorSupplier(CATEGORY_NOT_FOUND));
        }
        return productRepository.findById(id)
                .map(updateProductValues(product, category))
                .map(productRepository::save)
                .map(mapProductEntityToProductDto())
                .orElseThrow(errorSupplier(ErrorsEnum.PRODUCT_UPDATE_ERROR));
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        try {
            productRepository.findById(id)
                    .ifPresentOrElse(
                            productRepository::delete,
              () -> { throw new CustomException(PRODUCT_NOT_FOUND); });
        } catch (Exception e) {
            throw throwError(ErrorsEnum.PRODUCT_DELETE_ERROR, "Error while deleting product", e);
        }
    }

    private Optional<ProductEntity> buildProductEntity(ProductInput product, CategoryEntity category) {
        return Optional.of(ProductEntity.builder()
                .name(product.getName())
                .price(product.getPrice())
                .category(category)
                .build());
    }

    private Function<ProductEntity, ProductEntity> updateProductValues(ProductInput product, CategoryEntity category) {
        return productEntity -> productEntity.toBuilder()
                .name(product.getName() != null ? product.getName() : productEntity.getName())
                .price(product.getPrice() != null ? product.getPrice() : productEntity.getPrice())
                .category(category != null ? category : productEntity.getCategory())
                .build();
    }

    @Override
    @Transactional
    public void updateStock(Long productId, Integer quantity) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));

        if (product.getStockActual() < quantity) {
            throw new CustomException(ErrorsEnum.PRODUCT_STOCK_ERROR);
        }

        product.setStockActual(product.getStockActual() - quantity);
        productRepository.save(product);

        if (product.getStockActual() < product.getStockMinimo()) {
            System.out.println("ALERTA: El producto " + product.getName()
                    + " está por debajo del stock mínimo.");
        }
    }

    @Override
    public List<ProductDto> getAllProductsWithStockAlert() {
        return productRepository.findAll().stream()
                .map(mapProductEntityToProductDto())
                .peek(product -> {
                    if (product.getStockActual() < product.getStockMinimo()) {
                        System.out.println("ALERTA: Producto con stock bajo: " + product.getProductName());
                    }
                })
                .toList();
    }

}
