package waRestaurant.category.service;

import java.util.List;
import waRestaurant.category.controller.CategoryInput;
import waRestaurant.category.domain.CategoryDto;

public interface CategoryService {
  List<CategoryDto> getAllCategories();

  CategoryDto getCategoryById(Long id);

  CategoryDto createCategory(CategoryInput category);

  CategoryDto updateCategory(Long id, CategoryInput category);

  void deleteCategory(Long id);
}
