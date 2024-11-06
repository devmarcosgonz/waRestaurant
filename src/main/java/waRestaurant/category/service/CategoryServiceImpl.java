package waRestaurant.category.service;

import static waRestaurant.category.mapper.CategoryMapper.mapCategoryEntityToCategoryDto;
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
import waRestaurant.category.controller.CategoryInput;
import waRestaurant.category.domain.CategoryDto;
import waRestaurant.category.repository.CategoryEntity;
import waRestaurant.category.repository.CategoryRepository;
import waRestaurant.commons.CustomException;
import waRestaurant.commons.ErrorsEnum;

@Service
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  @Override
  public List<CategoryDto> getAllCategories() {
    return categoryRepository.findAll().stream()
        .map(mapCategoryEntityToCategoryDto())
        .toList();
  }

  @Override
  public CategoryDto getCategoryById(Long id) {
    return categoryRepository.findById(id)
        .map(mapCategoryEntityToCategoryDto())
        .orElseThrow(errorSupplier(CATEGORY_NOT_FOUND));
  }

  @Override
  @Transactional
  public CategoryDto createCategory(CategoryInput category) {
    return buildCategoryEntity(category)
        .map(categoryRepository::save)
        .map(mapCategoryEntityToCategoryDto())
        .orElseThrow(errorSupplier(CATEGORY_SAVE_ERROR));
  }

  @Override
  @Transactional
  public CategoryDto updateCategory(Long id, CategoryInput category) {
    return categoryRepository.findById(id)
        .map(updateCategoryValues(category))
        .map(categoryRepository::save)
        .map(mapCategoryEntityToCategoryDto())
        .orElseThrow(errorSupplier(ErrorsEnum.CATEGORY_UPDATE_ERROR));
  }

  @Override
  @Transactional
  public void deleteCategory(Long id) {
    try {
      categoryRepository.findById(id)
          .ifPresentOrElse(
              categoryRepository::delete,
              () -> { throw new CustomException(CATEGORY_NOT_FOUND); });
    } catch (Exception e) {
      throw throwError(ErrorsEnum.CATEGORY_DELETE_ERROR, "Error while deleting category", e);
    }
  }

  private Optional<CategoryEntity> buildCategoryEntity(CategoryInput category) {
    return Optional.of(CategoryEntity.builder()
         .name(category.getName())
        .build());
  }

  private Function<CategoryEntity, CategoryEntity> updateCategoryValues(CategoryInput category) {
    return updateCategory -> updateCategory.toBuilder()
        .name(category.getName())
        .build();
  }
}
