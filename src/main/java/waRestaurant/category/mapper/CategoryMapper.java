package waRestaurant.category.mapper;

import java.util.function.Function;
import waRestaurant.category.domain.CategoryDto;
import waRestaurant.category.repository.CategoryEntity;

public class CategoryMapper {

  public static Function<CategoryEntity, CategoryDto> mapCategoryEntityToCategoryDto() {
    return categoryEntity ->  CategoryDto.builder()
        .categoryId(categoryEntity.getId())
        .name(categoryEntity.getName())
        .build();
  }
}
